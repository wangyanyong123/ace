package com.github.wxiaoqi.sms.service;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.sms.entity.SysMobileInfo;
import com.github.wxiaoqi.sms.entity.SysSmsCode;

import java.util.Date;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description: 短信服务
 * @Date: Created in 10:38 2018/11/20
 * @Modified By:
 */
public interface ShortMessageService {

	/**
	 * 发短信
	 * @param mobilePhone
	 * @param num
	 * @param lostSecond
	 * @param bizType
	 * @param msgTheme
	 * @return
	 */
	ObjectRestResponse getCode(String mobilePhone, Integer num, Integer lostSecond, String bizType, String email, String userId, String msgTheme, Map<String, String> paramMap);

	/**
	 * 验证码认证
	 * @param mobile
	 * @param volidCode
	 * @return
	 */
	ObjectRestResponse checkCode(String mobile, String volidCode);

	ObjectRestResponse codeIsTrue(String mobilePhone, String volidCode);

	boolean sendEmail(String email, String title, String content);

	public ObjectRestResponse updateMobileInfo(SysMobileInfo mobileInfo) ;
}
