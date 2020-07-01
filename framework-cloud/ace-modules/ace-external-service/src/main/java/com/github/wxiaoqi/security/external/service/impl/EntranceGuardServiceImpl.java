package com.github.wxiaoqi.security.external.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.auth.client.dto.ExternalUserVo;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.external.biz.BizExternalUserBiz;
import com.github.wxiaoqi.security.external.biz.BizFacilitiesBiz;
import com.github.wxiaoqi.security.external.constant.EntranceGuardConstant;
import com.github.wxiaoqi.security.external.controller.PassportConnector;
import com.github.wxiaoqi.security.external.entity.BizFacilities;
import com.github.wxiaoqi.security.external.service.EntranceGuardBusiness;
import com.github.wxiaoqi.security.external.service.EntranceGuardService;
import com.github.wxiaoqi.security.external.vo.PassportInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:24 2018/12/29
 * @Modified By:
 */
@Service
@Slf4j
public class EntranceGuardServiceImpl implements EntranceGuardService {

	@Autowired
	private BizExternalUserBiz externalUserBiz;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public static Map<String, List<String>> passPortMap = new HashMap<>();

	private static EntranceGuardService doorService;

	@Autowired
	private EntranceGuardBusiness entranceBusiness;

	@Autowired
	private BizFacilitiesBiz facilitiesBiz;

	private static ExecutorService exec = null;

	static{
		exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1 );
	}

	public EntranceGuardServiceImpl(){
		doorService = this;
	}

	public static EntranceGuardService getInstance(){
		return doorService;
	}

	/**
	 *
	 */
	@Override
	public boolean getConnection(String machineCode, String appId, String appSecret) {

		if (StringUtils.isEmpty(machineCode) || StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSecret)) {
			return false;
		}
		BizFacilities facilities = new BizFacilities();
		facilities.setFacilitiesCode(machineCode);
		facilities.setStatus("1");
		List<BizFacilities> bizFacilitiesList = facilitiesBiz.selectList(facilities);
		if(bizFacilitiesList.size() < 1){
			return false;
		}
		ObjectRestResponse<ExternalUserVo> response =  externalUserBiz.getExtrnalUser(appId);
		if(null != response){
			ExternalUserVo userVo = response.getData();
			if(null != userVo && !StringUtils.isEmpty(userVo.getId())){
				if(appSecret.equals(userVo.getAppSecret())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 处理接收的消息
	 */
	@Override
	public String handleMsg(String machineCode, String msg) throws Exception{
 		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(msg.indexOf("}{") != -1){
				msg = msg.substring(0 , msg.length()- 19);
			}
			Map dataMap = JacksonJsonUtil.jsonToBean(msg, Map.class);
			result = handle(dataMap ,machineCode);
		} catch (Exception e) {
			log.error("处理接收的消息:{}" , msg);
			log.error("处理异常:" , e.fillInStackTrace());
			e.printStackTrace();//打印更多错误日志
			result.put("msg", "0");
			return JacksonJsonUtil.beanToJson(result);
		}
		return  JacksonJsonUtil.beanToJson(result);
	}

	private Map<String, Object> handle(Map<String, Object> dataMap , String machineCode){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "1");
		result.put("mid", dataMap.get("mid"));
		/*通行记录*/
		if("passRecord".equals(dataMap.get("cRequest"))){
			result.put("sReply", "openDoor");
			result.put("result", "1");
			String passportCode = dataMap.get("passportCode") == null ?
					(dataMap.get("userName") + "") : (dataMap.get("passportCode") + "") ;
			PassportInfo passportInfo = entranceBusiness.doPassEvent(passportCode , machineCode);
//			String userId = redisTemplate.opsForValue().get(EntranceGuardConstant.PUBQR_USER + dataMap.get("passportCode"));
//			passportInfo.setToken(getToken(userId));
//			passportInfo.setToken(getTokenByPassport( String.valueOf( dataMap.get("passportCode"))));
			String userId = redisTemplate.opsForValue().get(EntranceGuardConstant.PUBQR_USER + dataMap.get("passportCode"));
			passportInfo.setToken(redisTemplate.opsForValue().get(EntranceGuardConstant.USER_PUBLIC_TOKEN + userId));
			passportInfo.setPassType(entranceBusiness.getFacilitiesTypeByCode(machineCode));
			noticeClient("1" , passportInfo);//通知客户端开门成功
			if(null != passportInfo.getToken()){
				markPassportToken(userId, passportInfo.getToken());
			}
		}else if("QrCode".equals(dataMap.get("cRequest"))){
			/*实时开门*/
			String isOpenDoor = entranceBusiness.doOpenDoorNew(machineCode, dataMap.get("passportCode")+"");
			log.info("开门：machineCode={}:passport={}:isOpen={}" , machineCode , dataMap.get("passportCode") , isOpenDoor);
			if( "1".equals(isOpenDoor)){
				result.put("result", "1");
			}else{
				result.put("result", "0");
				PassportInfo passportInfo = new PassportInfo();
				String userId = redisTemplate.opsForValue().get(EntranceGuardConstant.PUBQR_USER + dataMap.get("passportCode"));
//				passportInfo.setToken(getToken(userId));

				passportInfo.setToken(redisTemplate.opsForValue().get(EntranceGuardConstant.USER_PUBLIC_TOKEN + userId));

				if(StringUtils.isEmpty(passportInfo.getToken())){
					passportInfo.setToken(redisTemplate.opsForValue().get(EntranceGuardConstant.USER_PUBLIC_QR + "token"));
				}
				passportInfo.setPassType(entranceBusiness.getFacilitiesTypeByCode(machineCode));
				noticeClient(isOpenDoor, passportInfo);//通知客户端开门失败
			}
			result.put("sReply", "QrCode");
			result.put("type", "req");
		}else if(!StringUtils.isEmpty(dataMap.get("cReply").toString())){
			return null;
		}
		return result;
	}

	/**
	 * 根据二维码通行证获取token
	 * @param passport
	 * @return
	 */
	private String getTokenByPassport(String passport){
		return redisTemplate.opsForValue().get(EntranceGuardConstant.USER_PUBLIC_QR + passport);
	}




	/**
	 刷二维码开门通知通行证客户端：
	 ｛
	 act:"openDoor"//开门
	 code：""//000开门成功，001开门失败
	 qrVal:""//开门成功返回最新的二维码值，开门失败不返回数据
	 qrNum:""//二维码编码
	 describe:""//描述
	 loseTime:""// 有效期 yyyy-MM-dd HH:mm
	 ｝
	 * @param passportInfo
	 * @param isPass : 1:成功 2：无通行权限 0：失败
	 */
	private void noticeClient(String isPass , PassportInfo passportInfo){

		if(passportInfo == null || StringUtils.isEmpty(passportInfo.getToken())){
			log.error("没有可用的长连接!" );
			return;
		}

		String[] tokens = passportInfo.getToken().split(",");
		for (String token : tokens) {
			Session session = PassportConnector.getSessionByToken(token.trim());
			if(session != null && session.isOpen()){
				Map<String , Object> msgMap = new HashMap<String, Object>();
				msgMap.put("act", "openDoor");

				if(StringUtils.isEmpty(passportInfo.getPassType())){
					passportInfo.setPassType("");
				}

				if("1".equals(isPass)){
					msgMap.put("code", "000");
//					msgMap.put("qrVal", passportInfo.getNewQrVal());
					msgMap.put("qrNum", passportInfo.getNewQrNum());
					msgMap.put("passType", passportInfo.getPassType());
					//这里加类别
					if(passportInfo.getPassType().equals("1")){
						msgMap.put("describe", "请进!");
					}else if(passportInfo.getPassType().equals("2")){
						msgMap.put("describe", "再见!");
					}else{
						msgMap.put("describe", "刷码成功!");
					}

				}else if("3".equals(isPass)){//无通行权限
						msgMap.put("code", "003");
						msgMap.put("describe", "错误二维码");

				}else if("2".equals(isPass)){//无通行权限
					msgMap.put("code", "002");
					msgMap.put("describe", "通行码不存在或没有开通此通行权限");

				}else {
						msgMap.put("code", "001");
						msgMap.put("describe", "抱歉，刷码失败，请刷新后再试!");
				}

				try {
					session.getBasicRemote().sendText(JacksonJsonUtil.beanToJson(msgMap));
				} catch (Exception e) {
					log.error("beanToJson异常:" , e );
				}
			}
		}

	}

	@Override
	public PassportInfo getLastPassportByToken(String token , String userId , Session session) {
		log.error("临时日志：token:"+token+",user:"+userId);
		PassportInfo passportInfo = entranceBusiness.getLastPassportByToken(token,userId);
		markPassportToken(userId, token);
		return passportInfo;
	}


	private void markPassportToken(String userId , String token){
		if(StringUtils.isEmpty(userId)){
			return;
		}
//		redisTemplate.opsForValue().set(EntranceGuardConstant.USER_PUBLIC_QR + passportInfo.getNewQrVal(), token,3600, TimeUnit.SECONDS);
		redisTemplate.opsForValue().set(EntranceGuardConstant.USER_PUBLIC_TOKEN + userId, token,3600, TimeUnit.SECONDS);
	}

	@Override
	public void updatePublicQr(String token) {
		UserAuthUtil userAuthUtil = BeanUtils.getBean(UserAuthUtil.class);
		try {
			IJWTInfo userInfo = userAuthUtil.getInfoFromToken(token);
			if(userInfo != null && userInfo.getId() != null){
				entranceBusiness.updatePublicQr(userInfo.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

