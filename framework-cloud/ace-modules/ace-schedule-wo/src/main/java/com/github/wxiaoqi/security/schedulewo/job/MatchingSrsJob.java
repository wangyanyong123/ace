package com.github.wxiaoqi.security.schedulewo.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.schedulewo.common.Msg;
import com.github.wxiaoqi.security.schedulewo.constant.DBConstant;
import com.github.wxiaoqi.security.schedulewo.feign.OrderEngineFegin;
import com.github.wxiaoqi.security.schedulewo.feign.SmsUtilsFegin;
import com.github.wxiaoqi.security.schedulewo.mapper.BizWoMapper;
import com.github.wxiaoqi.security.schedulewo.vo.BusDispatchRule;
import com.github.wxiaoqi.security.schedulewo.vo.MatchingCondition;
import com.github.wxiaoqi.security.schedulewo.vo.Srs;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;


/**
* @author xufeng
* @Description: 匹配服务资源
* @date 2015-6-5 下午5:31:40
* @version V1.0
*
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class MatchingSrsJob implements Job {

	private static Logger _log = LoggerFactory.getLogger(MatchingSrsJob.class);
	public static final String MATCHING_CONDITION = "matchingCondition";//匹配条件
	public static final String DISPATCH_RULE = "BusDispatchRule";//匹配条件
	public static final String DISPATCH_TIME = "DISPATCH_TIME";//调度次数
	public static final Map<String, Set<String>> msgMap = new HashMap<String, Set<String>>();

	private static SmsUtilsFegin smsUtilsFegin;
	private static OrderEngineFegin orderEngineFegin;
	private static BizWoMapper bizWoMapper;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		init();
		_log.debug("服务资源匹配.....");
		JobDataMap data = context.getJobDetail().getJobDataMap();
		MatchingCondition condition = (MatchingCondition)data.get(MATCHING_CONDITION);
		BusDispatchRule rule = (BusDispatchRule)data.get(DISPATCH_RULE);

		int dispatchTime = data.getInt(DISPATCH_TIME);//实际调用次数
		data.put(DISPATCH_TIME, ++dispatchTime);

		/*1.-------------有人接单（删除调度）||  超过匹配次数并且没有人接单或者抢单则（删除调度+转为人工处理）--------------*/
		if( checkWoAccept( condition.getWoId() ) ){
			GlobalSchedule.deleteJob( context.getJobDetail().getKey() );
			msgMap.remove(condition.getWoId());
			return;
		}else if(rule.getDispatchTime() != -1 && rule.getDispatchTime() <= (dispatchTime - 1) ){
			GlobalSchedule.deleteJob( context.getJobDetail().getKey() );
			changeWoDealType(condition.getWoId());
			msgMap.remove(condition.getWoId());
			return;
		}

		/*1.-------------有人接单（删除调度）||  超过匹配次数并且没有人接单或者抢单则（删除调度+转为人工处理）--------------*/
		ObjectRestResponse restResponse = orderEngineFegin.getAccpetWoUserListNoToken(condition.getWoId());//匹配服务资源
		if(restResponse!=null && restResponse.getStatus()==200) {
//			List<Map<String,String>> userInfoList = restResponse.getData()==null ? null : (List<Map<String,String>>)restResponse.getData();
//			if(userInfoList!=null && userInfoList.size()>0){
//				for (Map<String,String> userMap : userInfoList) {
//					String userId = userMap.get("userId") == null ? "" : (String)userMap.get("userId");
//					String mobilePhone = userMap.get("phone") == null ? "" : (String)userMap.get("phone");
//					String msgTheme = "SRS_GRAB_WO";
//					String msgParam = "";
//					restResponse = smsUtilsFegin.sendMsg(mobilePhone, null, null, null, null, userId, msgTheme, msgParam);
//					_log.info("发送消息结果："+restResponse.getStatus()+"-"+restResponse.getMessage());
//				}
//			}
		}else{
			_log.debug("暂未匹配到服务资源!");
			if( rule.getDispatchTime()  <= dispatchTime ){//实际调度次数大于等于调度规则的次数，还没有匹配到资源则（删除调度+转为人工处理）

				GlobalSchedule.deleteJob( context.getJobDetail().getKey() );
				if(rule.getDispatchTime() != -1){
					_log.error("未匹配到服务资源,且超时!");
					changeWoDealType(condition.getWoId());
				}

			}
		}

	}

	private void changeWoDealType(String woId){
//		SrsWo wo = new SrsWo();
//		wo.setId(woId);
//		wo.setDealType(WoDealType.MANUALHANDLING.toString());
//		srsWoService.updateWoDealType(wo);

//		if(baseDataBusiness!=null){
			//转为人工派单后根据配置发送消息
			Map<String, String> data = new HashMap<String, String>();
//			String sendSubSrsMsg = baseDataBusiness.sendSubSrsMsg("",woId, MsgOperate.TURNTOWODEAL_OPERATE, data);
//			_log.info("操作节点后根据配置发送消息结果："+sendSubSrsMsg);
//		}
	}

	/**
	 * 把消息放入消息池
	 * @param srsList
	 * @param msg
	 */
	private void putMsgPool(List<Srs> srsList , Msg msg , String dealType){

		if(srsList == null || srsList.size() == 0){
			return;
		}

//		try {
//
//			for (Srs srs : srsList) {
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("GO_PAGE", "SRS_GRAB_WO");
//				UserBean userbean = Services.getUserBusiness().getUserByObjectId(srs.getObjectId());
//
//				if(userbean == null){
//					_log.error("数据不完整,消息发送失败!objectId={}" , srs.getObjectId());
//					continue ;
//				}
//				String theme = ("2".equals(dealType) || "5".equals(dealType)) ? MsgTheme.SRS_GRAB_WO : MsgTheme.SRS_APPOINT_WO;
//				MsgInfo msgInfo = new MsgInfo(theme ,
//						MsgReceiver.buildByObjectId(userbean.getId()) , params);
//
//				MsgPool.add(msgInfo);
//			}
//		} catch (Exception e) {
//			_log.error("消息发送异常!" , e);
//		}

	}

	/**
	 * 检查工单是否已经有人接受
	 */
	private boolean checkWoAccept(String woId){
		Map<String,Object> woInfo = bizWoMapper.getWoInfoById(woId);
		if(woInfo == null){//具体工单已经无效，返回true自动退出调度。避免无效的调度
			return true;
		}
		String woStatus = woInfo.get("woStatus")==null ? "" : (String)woInfo.get("woStatus");
		if("00".equals(woStatus) || "01".equals(woStatus)){ //小于三的状态为没人接单
			return false;
		}
		return true;
	}

	private void init(){
		if(smsUtilsFegin == null){
			smsUtilsFegin = BeanUtils.getBean(SmsUtilsFegin.class);
		}

		if(orderEngineFegin == null){
			orderEngineFegin =  BeanUtils.getBean(OrderEngineFegin.class);
		}

        if(bizWoMapper == null){
            bizWoMapper =  BeanUtils.getBean(BizWoMapper.class);
        }
	}

}
