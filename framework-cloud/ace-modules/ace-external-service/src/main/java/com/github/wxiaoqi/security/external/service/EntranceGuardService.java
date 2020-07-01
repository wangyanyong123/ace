package com.github.wxiaoqi.security.external.service;

import com.github.wxiaoqi.security.external.vo.PassportInfo;

import javax.websocket.Session;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:23 2018/12/29
 * @Modified By:
 */
public interface EntranceGuardService {


	/**
	 * 门禁设备请求连接
	 * @param machineCode
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	boolean getConnection( String machineCode , String appId, String appSecret);

	/**
	 * 处理消息 返回处理结果
	 * @param machineCode
	 * @param msg
	 * @return
	 */
	String handleMsg(String machineCode , String msg) throws Exception;

	/**
	 * 获取最新的通行证
	 * @param token
	 * @param userId
	 * @param session
	 * @return
	 */
	PassportInfo getLastPassportByToken(String token , String userId , Session session );

	/**
	 * 更新共码
	 * @param token
	 */
	void updatePublicQr(String token);

}

