package com.github.wxiaoqi.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.config.GexinConfig;
import com.github.wxiaoqi.config.MsgConfig;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.common.util.XMLHandler;
import com.github.wxiaoqi.sms.Msg;
import com.github.wxiaoqi.sms.MsgInfo;
import com.github.wxiaoqi.sms.MsgReceiver;
import com.github.wxiaoqi.sms.biz.*;
import com.github.wxiaoqi.sms.entity.*;
import com.github.wxiaoqi.sms.enums.TempletType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:10 2018/12/5
 * @Modified By:
 */
@Service
@Slf4j
public class SendMsgServiceImpl {
	@Autowired
	private SysMsgThemeBiz msgThemeBiz;

	@Autowired
	private SysMsgTempletBiz msgTempletBiz;

	@Autowired
	private SysMobileInfoBiz mobileInfoBiz;

	@Autowired
	private SysUserMsgSettingBiz userMsgSettingBiz;

	@Autowired
	private SysSmsBiz smsBiz;

	@Autowired
	private GexinConfig gexinConfig;

	@Autowired
	private MsgConfig msgConfig;

	@Autowired
	private SysMsgInfoBiz msgInfoBiz;

	@Autowired
	private SysMsgUserBiz msgUserBiz;

	private static AtomicInteger counter = new AtomicInteger(0);

	public ObjectRestResponse sendStandardMsg(MsgInfo msgInfo ) {
		log.info("sendStandardMsg :{}." , JSONObject.toJSONString(msgInfo) );
		ObjectRestResponse restResponse = new ObjectRestResponse();
		try{
			if(msgInfo == null || msgInfo.getTheme() == null ||
					msgInfo.getReceiver() == null){
				restResponse.setMessage("参数不完整!");
				restResponse.setStatus(501);
				return restResponse;
			}
			boolean flagSucc = sendMsg(msgInfo);
			if(flagSucc){
				restResponse.setMessage("消息发送成功。");
			}else{
				restResponse.setStatus(502);
				restResponse.setMessage("消息发送失败。");
			}
		}catch(Exception ex){
			log.error("消息发送异常:{}",ex);
		}
		return restResponse;
	}

	private boolean sendMsg(MsgInfo msgInfo) throws Exception{

		boolean flagSucc = false;
		String theme = msgInfo.getTheme();//主题
		MsgReceiver receiver = msgInfo.getReceiver();//接收者
		Map<String, String> params = new HashMap<>( msgInfo.getPara());//参数
		SysMsgTheme msgTheme = new SysMsgTheme();
		msgTheme.setThemeCode(theme);
		msgTheme.setStatus("1");
		msgTheme = msgThemeBiz.selectOne(msgTheme);
		List<SysMsgTemplet> msgTemplets = new ArrayList<>();
		if(org.apache.commons.lang3.StringUtils.isNoneBlank(msgTheme.getId())){
			SysMsgTemplet msgTemplet = new SysMsgTemplet();
			msgTemplet.setThemeId(msgTheme.getId());
			msgTemplets = msgTempletBiz.selectList(msgTemplet);
		}
		for (SysMsgTemplet sysMsgTemplet : msgTemplets) {
			flagSucc = sendMsgByTemplet(receiver , msgTheme , sysMsgTemplet, params,msgInfo.getMorePara());
		}
		return flagSucc;
	}

	/**
	 * 根据模版发送消息
	 * @param receiver
	 * @param msgTemplet
	 * @param morePara
	 * @throws Exception
	 */
	private boolean sendMsgByTemplet(MsgReceiver receiver , SysMsgTheme msgTheme ,
									 SysMsgTemplet msgTemplet , Map<String, String> para, Map<String, String> morePara) throws Exception{
		boolean flagSucc = false;
		String templetType = msgTemplet.getTempletType();
		String context = msgTemplet.getTempletContent();
		para.put("page", msgTemplet.getPage());
		Map<String, String> params = new HashMap<String, String>(para);
		Map<String, String> moreParas = new HashMap<String, String>(morePara);
		moreParas.putAll(params);
//		receiver.setObjectId("");
		receiver.setReceiverType(MsgReceiver.ReceiverType.INDIVIDUAL.toString());
		if(TempletType.NOTICE.equals(templetType)){//手机通知
			flagSucc = sendMobileNotice( receiver , msgTemplet, params,moreParas);
		}else if(TempletType.MESSAGE.equals(templetType)){//手机消息
			flagSucc = addUserMessage(receiver, msgTheme, msgTemplet, moreParas);
		}else if(TempletType.SMS.equals(templetType)){//短信
			String tel = receiver.getPhoneNum();
			flagSucc = sendSms(tel, context, moreParas);
		}else if(TempletType.EMAIL.equals(templetType)){//邮件
			String emall = receiver.getEmail() ;
			flagSucc = EmailUtil.emailSend(emall, msgTheme.getThemeDesc() , repalceContext(context, moreParas));
		}

		return flagSucc;
	}

	/**
	 * 发送通知
	 * @param receiver
	 * @param msgTemplet
	 * @param params
	 * @param morePara
	 * @return
	 */
	private boolean sendMobileNotice(MsgReceiver receiver , SysMsgTemplet msgTemplet ,
									 Map<String , String> params, Map<String, String> morePara){
		Msg msg = new Msg();
		msg.setTitle(repalceContext(msgTemplet.getTitle() , morePara));
		msg.setContext(repalceContext(msgTemplet.getTempletContent() , morePara));
		params.put("sound", msgTemplet.getSound());
		params.put("page", msgTemplet.getPage());
		msg.setParams(params);
		msg.setPage(msgTemplet.getPage());
		msg.setSound(msgTemplet.getSound());
		return pushMsg(msg , receiver);
	}


	/**
	 * 消息发送
	 * @param
	 * @return
	 */
	private boolean pushMsg(Msg msg , MsgReceiver receiver){
		SysMobileInfo mobileInfo = new SysMobileInfo();
		mobileInfo.setUserId(receiver.getObjectId());
		mobileInfo.setStatus("1");
		mobileInfo = mobileInfoBiz.selectOne(mobileInfo);
		int count = 0;
		if(mobileInfo==null){
			log.error("消息发送失败,未获取到用户个推信息，{}" , receiver.getObjectId());
			return count == 1;
		}
		if(org.apache.commons.lang3.StringUtils.isNoneBlank(mobileInfo.getId())){
			SysUserMsgSetting userMsgSetting = new SysUserMsgSetting();
			userMsgSetting.setUserId(receiver.getPhoneNum());
			userMsgSetting.setStatus("1");
			userMsgSetting = userMsgSettingBiz.selectOne(userMsgSetting);
//			if( "1".equals(userMsgSetting.getSendType())){//是否需要发送，默认需要
				boolean sendSucc = push(msg , mobileInfo);
				if(sendSucc){
					count++;
				}
//			}
		}else {
			log.error("数据不完整,消息发送失败!{}" , JSONObject.toJSONString(mobileInfo));

		}
		return count == 1;
	}

	private boolean push(Msg msg , SysMobileInfo mobileInfo) {

		// 1、ios客户端APP；2、android客户端APP；3、ios服务端APP；4、android服务端APP
		String clientType = mobileInfo.getClientType();
		AbstractTemplate template = null;
		IQueryResult isOnline = null;
		IGtPush pushClient = null;
		IGtPush pushService = null;
		if(isClient(clientType)){
			pushClient = new IGtPush(gexinConfig.getHost() , gexinConfig.getAppkeyClient() , gexinConfig.getMasterClient());
			try {
				pushClient.connect();
				isOnline = pushClient.getClientIdStatus(gexinConfig.getAppIdClient(), mobileInfo.getCid());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{//勤务员
			pushService = new IGtPush(gexinConfig.getHost(), gexinConfig.getAppkeyService(), gexinConfig.getMasterService());
			try {
				pushService.connect();
				isOnline = pushService.getClientIdStatus(gexinConfig.getAppIdService(), mobileInfo.getCid());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		SingleMessage message = new SingleMessage();
		if("Offline".equals(isOnline.getResponse().get("result"))){
			message.setOffline(true);
			message.setOfflineExpireTime(2 * 1000 * 3600);
		}
		template = getTemplate(msg , mobileInfo);
		message.setData(template);

		Target target = new Target();
		if(isClient(clientType)){
			target.setAppId(gexinConfig.getAppIdClient());
		}else{//勤务员
			target.setAppId(gexinConfig.getAppIdService());
		}

		// 用户别名推送，cid和用户别名只能2者选其一
		if(mobileInfo.getCid() != null){
			target.setClientId(mobileInfo.getCid());
		}else{
			return false;
		}

		IPushResult ret = null;
		if(isClient(clientType)){
			ret = pushClient.pushMessageToSingle(message, target);
		}else{//勤务员
			ret = pushService.pushMessageToSingle(message, target);
		}
		log.error("推送消息.. cid ={} , result={}." , mobileInfo.getCid() , ret.getResponse().toString());
		Map<?, ?> data = ret.getResponse();
		if(data != null &&  "ok".equals(data.get("result"))){
			return true;
		}
		if(pushService != null){
			try {
				pushService.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(pushClient != null){
			try {
                pushClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public TransmissionTemplate getTemplate(Msg msg , SysMobileInfo mobileInfo) {
		TransmissionTemplate template = new TransmissionTemplate();
		if(isClient(mobileInfo.getClientType())){
			template.setAppId(gexinConfig.getAppIdClient());
			template.setAppkey(gexinConfig.getAppkeyClient());
		}else{
			template.setAppId(gexinConfig.getAppIdService());
			template.setAppkey(gexinConfig.getAppkeyService());
		}
		if(msg.getParams() == null){
			msg.setParams(new HashMap<String, String>());
		}
		msg.getParams().put("title", msg.getTitle());
		msg.getParams().put("context", msg.getContext());
		msg.getParams().put("sound", msg.getSound());
		template.setTransmissionContent(JSONObject.toJSONString(msg.getParams()));
		template.setTransmissionType(2);

		APNPayload payload = new APNPayload();
//		payload.setBadge(1);
		payload.setSound(msg.getSound() == null ? "" : msg.getSound());
		payload.setCategory(JSONObject.toJSONString(msg.getParams()));
		// 字典模式使用下者
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg(msg.getTitle()));
		template.setAPNInfo(payload);

		return template;
	}
	private static boolean isClient(String clientType){
		return "1".equals(clientType) || "2".equals(clientType);
	}

	//---------------------------------------短信----------------------------------------

	private boolean sendSms(String tel, String context, Map<String, String> params) throws Exception{
		boolean flagSms = false;
		String returnJson = sendSMS(tel , repalceContext(context, params));
		// 把json数据转换成Map
		Map returnMap = JSON.parseObject(returnJson, Map.class);
		String strXml = returnMap.get("response").toString().replace("\r\n", "");
		log.info("发送短信回传的结果Xml：" + strXml);
		// 解析xml
		List paramsList = XMLHandler.getListFromXML(strXml, "error");
		if(paramsList != null && paramsList.size() > 0){
			Map map = (Map) paramsList.get(0);
			String errorCode = map.get("error").toString();
			// 发送成功
			if(errorCode.equals("0")){
				flagSms = true;
			}else{
				flagSms = false;
				log.info("手机号为：" + tel + "的短信发送失败。");
			}
		}
		return flagSms;
	}

	/**
	 * 发送短信验证码
	 *
	 * @param mobile
	 *            手机号码
	 * @param msg
	 *            随机码
	 */
	public String sendSMS(String mobile, String msg) {
		String response = null;
		String seqid = getAtomicCounter() + "";
		try {

			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(msgConfig.getUrl());

			client.getParams().setContentCharset("UTF-8");
			method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

			msg = msg.replaceAll("【", "<").replaceAll("】", ">").replaceAll("\\[", "<").replaceAll("]", ">");

			NameValuePair[] data = { // 提交短信
					new NameValuePair("cdkey", msgConfig.getSoftwareSerialNo()),
					new NameValuePair("password", msgConfig.getKey()),
					new NameValuePair("phone", mobile), new NameValuePair("message", msg),
					new NameValuePair("addserial", ""), new NameValuePair("seqid", seqid),
					new NameValuePair("smspriority", "5"), };

			method.setRequestBody(data);
			client.executeMethod(method);

			response = method.getResponseBodyAsString();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送短信异常:{}", response);
		}

		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("response", response);
		returnData.put("seqid", seqid);

		@SuppressWarnings("unused")
		boolean status = false;
		// code=0表示短信发送成功
		if (response.indexOf("<error>0</error") != 0) {
			status = true;
		} else {
			status = false;
			log.error("发送短信异常：{}", response);
		}

		SysSms sms = new SysSms();
		sms.setId(UUIDUtils.generateUuid());
		sms.setPhone(mobile);
		sms.setMessage(msg);
		sms.setSeqid(seqid);
		sms.setSmspriority("5");
		sms.setSmsStatus(response);
		sms.setCreateBy(StringUtils.isEmpty(BaseContextHandler.getUserID())?mobile:BaseContextHandler.getUserID());
		sms.setCreateTime(new Date());
		smsBiz.insertSelective(sms);
		if (null != smsBiz.selectById(sms.getId())) {
			log.info(" 短信发送记录插入成功:{}.", JSONObject.toJSONString(sms));
		} else {
			log.error(" 短信发送记录插入失败:{}.", JSONObject.toJSONString(sms));
		}

		return JSONObject.toJSONString(returnData);
	}

	public static long getAtomicCounter() {
		if (counter.get() > 999999) {
			counter.set(1);
		}
		long time = System.currentTimeMillis();
		long returnValue = time * 100 + counter.incrementAndGet();
		return returnValue;
	}

	//------------------------------------ 手机消息 ------------------------------------------------
	public boolean addUserMessage(MsgReceiver receiver, SysMsgTheme msgTheme,
								  SysMsgTemplet msgTemplet, Map<String, String> params) {

		String msgId = UUIDUtils.generateUuid();
		addMsgInfo(msgId, receiver, msgTheme, msgTemplet, params);
		addMsgUser(receiver, msgId);
		return true;
	}
	/**
	 * 添加消息数据
	 * @param msgId
	 * @param receiver
	 * @param msgTheme
	 * @param msgTemplet
	 * @param params
	 * @return
	 */
	private boolean addMsgInfo(String msgId , MsgReceiver receiver, SysMsgTheme msgTheme,
							   SysMsgTemplet msgTemplet ,  Map<String, String> params){

		Date createTime = Calendar.getInstance().getTime();

		SysMsgInfo msgInfo = new SysMsgInfo();
		msgInfo.setId(msgId);
		msgInfo.setCreateBy(BaseContextHandler.getUserID()==null ? "admin" : BaseContextHandler.getUserID());
		msgInfo.setCreateTime(createTime);
		msgInfo.setMetaData(JSONObject.toJSONString(params));
		msgInfo.setMsgContent(repalceContext(msgTemplet.getTempletContent() , params));
		msgInfo.setMsgTitle(repalceContext(msgTemplet.getTitle() , params));
		msgInfo.setMsgType(msgTemplet.getTempletType());
		msgInfo.setObjectId(receiver.getObjectId());
		msgInfo.setObjectType(receiver.getReceiverType());
		msgInfo.setSendType(msgTemplet.getTempletType());
		msgInfo.setStatus("1");

		msgInfoBiz.insertSelective(msgInfo);//添加到消息表
		return true;
	}
	/**
	 * 添加消息和用户的关联
	 * @param receiver
	 * @param msgId
	 * @return
	 */
	private boolean addMsgUser(MsgReceiver receiver , String msgId){

		Date createTime = Calendar.getInstance().getTime();
		SysMsgUser msgUser = new SysMsgUser();
		msgUser.setCreateBy(BaseContextHandler.getUserID()==null ? "admin" : BaseContextHandler.getUserID());
		msgUser.setCreateTime(createTime);
		msgUser.setId(UUIDUtils.generateUuid());
		msgUser.setMsgId(msgId);
		msgUser.setMsgStatus("1");
		msgUser.setUserId(receiver.getObjectId());
		msgUser.setStatus("1");
		msgUserBiz.insertSelective(msgUser);
		return true;
	}
	//------------------------------------------------------------------------------------
	private String repalceContext(String context , Map<String, String> params){
		if(context == null || params == null){
			return context;
		}
		for(String key : params.keySet()){
			String regex = "#(" + key +")";
			if(context.indexOf(regex) != -1){
				context = context.replace(regex , params.get(key));
			}
		}
		return context;
	}
}
