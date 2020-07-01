package com.github.wxiaoqi.security.schedulewo.task;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.constant.MsgNoticeConstant;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.schedulewo.config.WOTimeConfig;
import com.github.wxiaoqi.security.schedulewo.config.WoConfig;
import com.github.wxiaoqi.security.schedulewo.constant.OperateConstants;
import com.github.wxiaoqi.security.schedulewo.feign.OrderEngineFegin;
import com.github.wxiaoqi.security.schedulewo.feign.SmsUtilsFegin;
import com.github.wxiaoqi.security.schedulewo.feign.SystemMsgFegin;
import com.github.wxiaoqi.security.schedulewo.mapper.BizWoMapper;
import com.github.wxiaoqi.security.schedulewo.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:37 2019/3/6
 * @Modified By:
 */
@Component
@EnableScheduling
@Slf4j
public class WoTask {
	@Autowired
	private BizWoMapper woMapper;

	@Autowired
	private SmsUtilsFegin smsUtilsFegin;

	@Autowired
	private SystemMsgFegin systemMsgFegin;

	@Autowired
	private WoConfig woConfig;

	@Autowired
	private OrderEngineFegin orderEngineFegin;

	//去除工单接单超时提醒任务
	// @Scheduled(cron = "0 0/5 * * * ? ")
	public void job1(){
		log.info("处理超过"+woConfig.getOuttime()+"个小时未接单的工单:" + new Date());
		List<String> list = new ArrayList<>();
//		list.add(BusinessConstant.getPlanWo());
		list.add(BusinessConstant.getCmplainBusId());
		list.add(BusinessConstant.getRepairBusId());
		List<NoticeWoInfoVo> woInfoVos = woMapper.needNoticeWo(list,woConfig.getOuttime());
		if(woInfoVos != null && woInfoVos.size()>0){
			for (NoticeWoInfoVo woInfoVo:woInfoVos) {
				if(StringUtils.isNotEmpty(woInfoVo.getProjectId())){
					List<UserInfoVo> userInfoVos = woMapper.getUserInfoListByProjectId(woInfoVo.getProjectId());
					if(userInfoVos != null && userInfoVos.size()>0){
						for (UserInfoVo userInfoVo:userInfoVos) {
							try {
								String userId = userInfoVo.getId();
								String mobilePhone = userInfoVo.getMobilePhone();
								Map<String,String> param = new HashMap<>();
								param.put("title",woInfoVo.getTitle());
								String msgParam = JSON.toJSONString(param);
								ObjectRestResponse restResponse = smsUtilsFegin.sendMsg(mobilePhone, null, null, null, null, userId, MsgThemeConstants.DELAY_WO_M_NOTICE, msgParam);
								log.info("发送短信消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());
								restResponse = smsUtilsFegin.sendMsg(null, null, null, null, null, userId, MsgThemeConstants.DELAY_WO_P_NOTICE, msgParam);
								log.info("发送消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());
							}catch (Exception e){
								e.printStackTrace();
							}
						}
						woMapper.insertNoticeWO(woInfoVo.getId());
					}
				}
			}
		}
	}

	/**
	 * 2.用户下单在自定义（小时）内系统自动取消
	 *
	 */
	@Scheduled(cron = "0 0/3 * * * ? ")
	public void job2(){
		Config config = ConfigService.getConfig("ace-schedule-wo");
		int missedorder = config.getIntProperty(WOTimeConfig.ApolloKey.MISSED_ORDER.toString(), 0);
		if(0 == missedorder){
			missedorder = woConfig.getMissedorder();
		}
		log.info("处理超过"+missedorder/60.00+"个小时未接单的订单:" + new Date());
		String busIds = config.getProperty(WOTimeConfig.ApolloKey.MISSED_ORDER_BUSID.toString(), "");
		if(StringUtils.isEmpty(busIds)){
			busIds = woConfig.getMissedorderbusid();
		}
		List<String> list = Arrays.asList(busIds.split(","));
		List<SubVo> subVos = woMapper.missedOrderWo(list,missedorder);
		if(subVos != null && subVos.size()>0){
			for (SubVo subVo:subVos) {
				DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
				doOperateByTypeVo.setId(subVo.getSubId());
				doOperateByTypeVo.setOperateType(OperateConstants.OperateType.CANCEL.toString());
				ObjectRestResponse operateResult = orderEngineFegin.doOperateByType(doOperateByTypeVo);
				log.info("受理工单处理结果:"+operateResult.toString());
				if(StringUtils.isNotEmpty(subVo.getUserId())){
					Map<String,String> paramMap = new HashMap<>();
					paramMap.put("title",subVo.getTitle());
					paramMap.put("time",String.valueOf(missedorder));
					SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
					smsNoticeVo.setReceiverId(subVo.getUserId());
					smsNoticeVo.setParamMap(paramMap);
					smsNoticeVo.setObjectId(subVo.getSubId());
					smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.MISSED_ORDER));
					try {
						systemMsgFegin.saveSmsNotice(smsNoticeVo);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 *
	 * 3.商品订单在配送完成自定义（小时）内，用户未确认系统自动确认完成订单
	 */
	@Scheduled(cron = "0 0/3 * * * ? ")
	public void job3(){
		Config config = ConfigService.getConfig("ace-schedule-wo");
		int unconfirmed = config.getIntProperty(WOTimeConfig.ApolloKey.UN_CONFIRMED.toString(), 0);
		if(0 == unconfirmed){
			unconfirmed = woConfig.getUnconfirmed();
		}
		log.info("处理超过"+unconfirmed/60.00+"个小时未确认的订单:" + new Date());
		String busIds = config.getProperty(WOTimeConfig.ApolloKey.UN_CONFIRMED_BUSID.toString(), "");
		if(StringUtils.isEmpty(busIds)){
			busIds = woConfig.getUnconfirmedbusid();
		}
		List<String> list = Arrays.asList(busIds.split(","));
		List<SubVo> subVos = woMapper.unconfirmedWo(list,unconfirmed);
		if(subVos != null && subVos.size()>0){
			for (SubVo subVo:subVos) {
				DoOperateByTypeVo doOperateByTypeVo = new DoOperateByTypeVo();
				doOperateByTypeVo.setId(subVo.getSubId());
				doOperateByTypeVo.setOperateType(OperateConstants.OperateType.REVIEW.toString());
				ObjectRestResponse operateResult = orderEngineFegin.doOperateByType(doOperateByTypeVo);
				log.info("受理工单处理结果:"+operateResult.toString());
				if(StringUtils.isNotEmpty(subVo.getUserId())){
					Map<String,String> paramMap = new HashMap<>();
					paramMap.put("title",subVo.getTitle());
					paramMap.put("time",String.valueOf(unconfirmed));
					SmsNoticeVo smsNoticeVo = new SmsNoticeVo();
					smsNoticeVo.setReceiverId(subVo.getUserId());
					smsNoticeVo.setParamMap(paramMap);
					smsNoticeVo.setObjectId(subVo.getSubId());
					smsNoticeVo.setSmsNotice(MsgNoticeConstant.msgNoticeMap.get(MsgNoticeConstant.UN_CONFIREMED));
					try {
						systemMsgFegin.saveSmsNotice(smsNoticeVo);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}
