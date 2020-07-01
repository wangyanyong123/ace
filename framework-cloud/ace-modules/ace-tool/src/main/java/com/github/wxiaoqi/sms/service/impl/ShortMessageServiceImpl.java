package com.github.wxiaoqi.sms.service.impl;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.sms.MsgInfo;
import com.github.wxiaoqi.sms.MsgReceiver;
import com.github.wxiaoqi.sms.biz.SysSmsCodeBiz;
import com.github.wxiaoqi.sms.entity.SysMobileInfo;
import com.github.wxiaoqi.sms.entity.SysSmsCode;
import com.github.wxiaoqi.sms.mapper.SysMobileInfoMapper;
import com.github.wxiaoqi.sms.service.ShortMessageService;
import com.github.wxiaoqi.sms.vo.MsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:39 2018/11/20
 * @Modified By:
 */
@Slf4j
@Service
public class ShortMessageServiceImpl implements ShortMessageService {

	@Autowired
	private SysSmsCodeBiz smsCodeBiz;

	@Autowired
	private SendMsgServiceImpl sendMsgService;
	@Autowired
	private SysMobileInfoMapper sysMobileInfoMapper;

	@Override
	public ObjectRestResponse getCode(String mobilePhone, Integer num, Integer lostSecond, String bizType,
									  String email, String userId, String msgTheme,Map<String, String> paramMap) {
		ObjectRestResponse restResponse = new ObjectRestResponse();
		String code = "";
		if(StringUtils.isEmpty(msgTheme)){
			restResponse.setStatus(505);
			restResponse.setMessage("消息主体不能为空");
			return restResponse;
		}
		if(StringUtils.isEmpty(bizType)){
			restResponse.setStatus(506);
			restResponse.setMessage("消息类型不能为空");
			return restResponse;
		}else {
			if("1".equals(bizType)){
				Date loseTime = null;
				if(num.intValue() < 1){
					restResponse.setStatus(501);
					restResponse.setMessage("位数不能小于1");
					return restResponse;
				}
				if(lostSecond.intValue() < 1){
					restResponse.setStatus(502);
					restResponse.setMessage("失效时间不能为空！");
					return restResponse;
				}else {
					loseTime = DateTimeUtil.getLoseTimes(lostSecond.intValue());
				}
				if(!DateTimeUtil.compare_date(loseTime, new Date())){
					restResponse.setStatus(503);
					restResponse.setMessage("失效时间不能小于当前时间！");
					return restResponse;
				}
				code = StringUtils.generateRandomNumber(num.intValue());
				if (StringUtils.isMobile(mobilePhone)){
					SysSmsCode smsCode = new SysSmsCode();
					MsgVo msgVo = new MsgVo();
					smsCode.setCode(code);
					smsCode.setNum(num);
					smsCode.setMobilePhone(mobilePhone);
					smsCode.setStatus("0");
					smsCode.setBizType(bizType);
					smsCode.setLoseTime(loseTime);
					smsCode.setCreateTime(new Date());
					smsCodeBiz.insertSelective(smsCode);
					if (sendSms(msgTheme, code, mobilePhone, email, userId, paramMap)){
						log.info("send sms:mobilePhone={} , code ={}}" , mobilePhone , code);
						msgVo.setCode(StringUtils.isEmpty(code)?msgTheme:code);
						msgVo.setPhone(mobilePhone);
						restResponse.setData(msgVo);
						restResponse.setMessage("发送成功");
						return restResponse;
					}else {
						log.error("send sms:mobilePhone={} , code ={}}" , mobilePhone , code);
						msgVo.setCode(StringUtils.isEmpty(code)?msgTheme:code);
						msgVo.setPhone(mobilePhone);
						restResponse.setData(msgVo);
						restResponse.setStatus(601);
						restResponse.setMessage("发送失败");
						return restResponse;
					}
				}else{
					restResponse.setStatus(504);
					restResponse.setMessage("手机号码不对！");
					return restResponse;
				}
			}
		}
		if (sendSms(msgTheme, code, mobilePhone, email, userId, paramMap)){
			log.info("send sms:mobilePhone={} , code ={} , email ={} , userId ={}}", mobilePhone, code, email, userId);
			restResponse.setMessage("发送成功");
			return restResponse;
		}else {
			log.error("send sms:mobilePhone={} , code ={} , email ={} , userId ={}}", mobilePhone, code, email, userId);
			restResponse.setStatus(601);
			restResponse.setMessage("发送失败");
			return restResponse;
		}
	}

	/**
	 * 验证码认证
	 * @param mobile
	 * @param volidCode
	 * @return
	 */
	@Override
	public ObjectRestResponse checkCode(String mobile, String volidCode) {
		ObjectRestResponse response = new ObjectRestResponse();
		if (StringUtils.isAnyoneEmpty(mobile, volidCode)){
			response.setStatus(501);
			response.setMessage("参数错误！");
			return response;
		}
		SysSmsCode smsCode = smsCodeBiz.getLastCode(mobile,volidCode);
		if (null == smsCode) {
			response.setStatus(502);
			response.setMessage("验证码错误，请重新输入！");
			return response;
		}

		if (DateTimeUtil.compare_date(smsCode.getLoseTime(), DateTimeUtil.getLocalTime())){
			if ("0".equals(smsCode.getStatus())){
				smsCode.setStatus("1");
				smsCode.setModifyTime(new Date());
				smsCodeBiz.updateSelectiveById(smsCode);
			} else {
				response.setStatus(503);
				response.setMessage("验证码已经被使用，请重新获取！");
			}
		} else {
			response.setStatus(504);
			response.setMessage("验证码已过期！");
		}
		return response;
	}

	@Override
	public ObjectRestResponse codeIsTrue(String mobilePhone, String volidCode) {
		ObjectRestResponse response = new ObjectRestResponse();
		if (StringUtils.isAnyoneEmpty(mobilePhone, volidCode)){
			response.setStatus(501);
			response.setMessage("参数错误！");
			return response;
		}
		SysSmsCode smsCode = smsCodeBiz.getLastCode(mobilePhone,volidCode);
		if (null == smsCode) {
			response.setStatus(502);
			response.setMessage("验证码错误，请重新输入！");
			return response;
		}

		if (DateTimeUtil.compare_date(smsCode.getLoseTime(), DateTimeUtil.getLocalTime())){
			if ("0".equals(smsCode.getStatus())){
				response.setMessage("验证码有效！");
			} else {
				response.setStatus(503);
				response.setMessage("验证码已经被使用，请重新获取！");
			}
		} else {
			response.setStatus(504);
			response.setMessage("验证码已过期！");
		}
		return response;
	}

	@Override
	public boolean sendEmail(String email, String title, String content) {
		return EmailUtil.emailSend(email, title, content);
	}

	private boolean sendSms(String msgTheme, String code, String phoneNum, String email, String userId, Map<String, String> paramMap){
		MsgInfo msgInfo = new MsgInfo();
		msgInfo.setTheme(msgTheme);
		msgInfo.setReceiver(MsgReceiver.buildByAll(phoneNum,userId,email));
		if(null != paramMap){
			if(!StringUtils.isEmpty(code)){
				paramMap.put("randomCode", code);
			}
			msgInfo.setPara(paramMap);
		}else {
			if(!StringUtils.isEmpty(code)){
				Map<String, String> params = new HashMap<>();
				params.put("randomCode", code);
				msgInfo.setPara(params);
			}
		}
		return sendMsgService.sendStandardMsg(msgInfo).getStatus() == 200;
	}

	public ObjectRestResponse updateMobileInfo(SysMobileInfo mobileInfo) {
		ObjectRestResponse rtnMsg = new ObjectRestResponse();
		if (mobileInfo == null || StringUtils.isEmpty(mobileInfo.getUserId())
				|| StringUtils.isEmpty(mobileInfo.getCid()) || "null".equals(mobileInfo.getCid())) {
			rtnMsg.setData(false);
			return rtnMsg;
		}
		SysMobileInfo oldMobileInfo = sysMobileInfoMapper.getMobileInfoByUserId(mobileInfo.getUserId());

		boolean isNewClient = false;

		if(oldMobileInfo!=null){
			if(!StringUtils.isEmpty(mobileInfo.getMacId())){//如果为空，则通过client_Type, os, osVersion, version 这4个字段进行判断

				if(mobileInfo.getMacId().equals(oldMobileInfo.getMacId())){
					isNewClient = false;
				}else{
					isNewClient = true;
				}
			}

			if((oldMobileInfo.getClientType()==null&&mobileInfo.getClientType()!=null)||oldMobileInfo.getClientType()!=null&&!oldMobileInfo.getClientType().equals(mobileInfo.getClientType())){
				isNewClient=true;
			}
			if((oldMobileInfo.getOs()==null&&mobileInfo.getOs()!=null)||oldMobileInfo.getOs()!=null&&!oldMobileInfo.getOs().equals(mobileInfo.getOs())){
				isNewClient=true;
			}
			if((oldMobileInfo.getOsVersion()==null&&mobileInfo.getOsVersion()!=null)||oldMobileInfo.getOsVersion()!=null&&!oldMobileInfo.getOsVersion().equals(mobileInfo.getOsVersion())){
				isNewClient=true;
			}
			if((oldMobileInfo.getVersion()==null&&mobileInfo.getVersion()!=null)||oldMobileInfo.getVersion()!=null&&!oldMobileInfo.getVersion().equals(mobileInfo.getVersion())){
				isNewClient=true;
			}
		}else{
			isNewClient = true;
		}


		this.updateMobileInfoServer(mobileInfo);
		if(isNewClient){
			mobileInfo.setId(UUIDUtils.generateUuid());
			mobileInfo.setCreateBy(mobileInfo.getUserId());
			mobileInfo.setCreateTime(new Date());
			sysMobileInfoMapper.insertMobileInfoLog(mobileInfo);
		}
		rtnMsg.setData(true);
		return rtnMsg;
	}

	public void updateMobileInfoServer(SysMobileInfo mobileInfo) {
		final String userId = mobileInfo.getUserId();
		Date currTime = Calendar.getInstance().getTime();
		int changeCount = 0;
		try {
			mobileInfo.setModifyBy(userId);
			mobileInfo.setModifyTime(currTime);
			changeCount = sysMobileInfoMapper.updateMobileInfo(mobileInfo);
		} catch (Exception e) {
			log.error("更新用户手机信息异常：" , e);
		}
		//更新失败【没有用户信息】，直接插入
		if(changeCount == 0){
			mobileInfo.setCreateBy(userId);
			mobileInfo.setCreateTime(currTime);
			mobileInfo.setId(UUIDUtils.generateUuid());
			mobileInfo.setUserId(mobileInfo.getUserId());
			sysMobileInfoMapper.insertMobileInfo(mobileInfo);
		}
//		CachedUtil.set(MsgCachedType.MOBILEINFO, mobileInfo.getObjectId(), 60*60*24 , mobileInfo);
		sysMobileInfoMapper.delDirtyData(mobileInfo);

	}

}
