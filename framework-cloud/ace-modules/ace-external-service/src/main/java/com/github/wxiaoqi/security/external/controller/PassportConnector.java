package com.github.wxiaoqi.security.external.controller;

import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.external.config.QrConfiguration;
import com.github.wxiaoqi.security.external.service.EntranceGuardService;
import com.github.wxiaoqi.security.external.service.impl.EntranceGuardServiceImpl;
import com.github.wxiaoqi.security.external.vo.PassportInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:17 2018/12/28
 * @Modified By:
 */
@ServerEndpoint(value = "/PassportConnector/{busType}/{token}")
@Component
@Slf4j
public class PassportConnector {

	@Autowired
	private QrConfiguration qrConfiguration;

	private static volatile ConcurrentMap<String , Session> sessionMap = new ConcurrentHashMap<String, Session>(0);

	private EntranceGuardService entranceGuardService = EntranceGuardServiceImpl.getInstance();

	public static void sendMessageByToken(String token , String msg) throws IOException {
			log.debug("Send message:{}" , msg);

		if(sessionMap.get(token) != null){
			sessionMap.get(token).getBasicRemote().sendText(msg);
		}

	}

	public static Session getSessionByToken(String token){
		return sessionMap.get(token);
	}

	@OnMessage
	public void onMessage(String message, @PathParam("token") String token) throws IOException,
			InterruptedException {
		log.debug("Received: {}" , message);
	}

	/**
	 * 打开通行证页面：
	 ｛
	 act："connect"//连接最新二维码
	 code：""//000：成功，001：token无效
	 qrVal:""//最新的二维码值,验证token无效则不返回
	 qrNum:""//二维码编码
	 describe:""//描述
	 qrType:""//认证状态  0 未认证或认证失败   1 审核中或已认证
	 objectType:""// 类型 如UIT001,UIT002
	 loseTime:""// 有效期 yyyy-MM-dd HH:mm
	 ｝
	 * @param session
	 * @param token
	 * @param busType /busType:01首页，02通行证页面
	 */
	@OnOpen
	public void onOpen(Session session , @PathParam("token") String token ,
					   @PathParam("busType") String busType) throws  Exception {

		log.debug("Client connected,token:{},busType:{}" , token , busType );

		if(StringUtils.isEmpty(token)){//token为空关闭连接
			try {
				session.close();
			} catch (IOException e) {
				log.error("关闭异常:{}" , token , e);
			}
			return;
		}

		Map<String, Object> result = new HashMap<>(7);
		result.put("act", "connect" );

		UserAuthUtil userAuthUtil = BeanUtils.getBean(UserAuthUtil.class);
		IJWTInfo userInfo = userAuthUtil.getInfoFromToken(token);

		if(userInfo == null){
			try {//认证失败则关闭session 连接
				result.put("code", "001");
				session.getBasicRemote().sendText(JacksonJsonUtil.beanToJson(result));
				session.close();
			} catch (IOException e) {
				log.error("通行证连接 异常 :{}" , token , e);
			}
		}else{//认证成功
			try {
				sessionMap.put(token ,session);
				String userId = userInfo.getId();

				PassportInfo passportInfo = entranceGuardService.getLastPassportByToken(token ,
						userId , session);

				if(null != passportInfo) {
					result.put("code", "000");
					result.put("qrNum", passportInfo.getNewQrNum());
					result.put("loseTime", passportInfo.getLoseTime());// 有效期 yyyy-MM-dd HH:mm
				}
				if(null == qrConfiguration){
					qrConfiguration = BeanUtils.getBean(QrConfiguration.class);
				}
				if("01".equals(busType)){
					result.put("activeTime", qrConfiguration.getRefreshTime());
				}else if("02".equals(busType)){
					result.put("activeTime", qrConfiguration.getRefreshTime());
				}else{
					result.clear();
					result.put("code", "002");//非法的业务
					result.put("describe", "无效的busType");//非法的业务
					session.getBasicRemote().sendText(JacksonJsonUtil.beanToJson(result));
					session.close();
					return;
				}

				log.debug(JacksonJsonUtil.beanToJson(result));
				session.getBasicRemote().sendText(JacksonJsonUtil.beanToJson(result));

			} catch (IOException e) {
				log.error("通行证连接 异常 : {}" , token , e);
			}
		}

	}

	@OnClose
	public void onClose(Session session , @PathParam("token") String token) throws IOException {
		log.debug("Connection closed");
		if(sessionMap.get(token) != null){
			sessionMap.remove(token);
//			QrJob.clearJob(token);
			entranceGuardService.updatePublicQr(token);//关闭长连接更新二维码
		}

	}

	/**
	 * 获取更新时间频度
	 * @return
	 */
	public static int getPassportAutoUpdateTime(){
		return 60;
	}
}
