package com.github.wxiaoqi.security.external.controller;

import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.external.service.EntranceGuardService;
import com.github.wxiaoqi.security.external.service.impl.EntranceGuardServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@ServerEndpoint(value = "/EntranceGuardWebSocket/{machineCode}/{userName}/{passWord}", subprotocols = "dumb-increment-protocol")
@Slf4j
@Component
public class EntranceGuardWebSocket {

	private EntranceGuardService entranceGuardService = EntranceGuardServiceImpl.getInstance();

	private static volatile ConcurrentMap<String , Session> sessionMap = new ConcurrentHashMap<String, Session>(0);

	public static void sendMessageByMachinecode(String machineCode , String msg) throws IOException {

		if(sessionMap.get(machineCode) != null){
			sessionMap.get(machineCode).getBasicRemote().sendText(msg);
		}

	}

	@OnMessage
	public void onMessage(String message, @PathParam("machineCode") String machineCode) throws IOException,
			InterruptedException {
		if(message.indexOf("heartbeat") >= 0 ){
			sessionMap.get(machineCode).getBasicRemote().sendText(message.replace("cRequest", "sReply"));
			return;
		}
		String reply = null;
		try {
			reply = entranceGuardService.handleMsg(machineCode, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( !StringUtils.isEmpty(reply) && !"{}".equals(reply)){
			sessionMap.get(machineCode).getBasicRemote().sendText(reply);
		}

	}

	@OnOpen
	public void onOpen(Session session ,@PathParam("machineCode") String machineCode ,
					   @PathParam("userName") String userName,@PathParam("passWord") String passWord) throws Exception {

		log.debug("Client connected , machineCode:{}" , machineCode);

		Map<String, Object> result = new HashMap<>(4);
		result.put("sReply", "login");
		result.put("machineCode", machineCode);
		result.put("dateTime", System.currentTimeMillis());
		if(! entranceGuardService.getConnection(machineCode, userName, passWord)){
			try {//认证失败则关闭session 连接
				result.put("result", "0");
				session.getBasicRemote().sendText(JacksonJsonUtil.beanToJson(result));
				session.close();
			} catch (IOException e) {
				log.error("WebSocket 异常 :{}" , machineCode , e);
			}
		}else{//认证成功
			try {
				if(sessionMap.get(machineCode) != null){
					sessionMap.get(machineCode).close();
				}
				sessionMap.put(machineCode ,session);
				result.put("result", "1");
				log.info(JacksonJsonUtil.beanToJson(result));
				session.getBasicRemote().sendText(JacksonJsonUtil.beanToJson(result));
			} catch (IOException e) {
				log.error("WebSocket 发送异常 :{}" , machineCode , e);
			}
		}

	}

	@OnClose
	public void onClose(Session session , @PathParam("machineCode") String machineCode) throws IOException {
		log.debug("Connection closed-machineCode:{}" , machineCode);
		if(sessionMap.get(machineCode) != null){
			sessionMap.remove(machineCode);
		}

	}

	public static int getOnlineCount(){
		return sessionMap.size();
	}

	public static Map<String , Session> getOnLine(){
		return sessionMap;
	}
}
