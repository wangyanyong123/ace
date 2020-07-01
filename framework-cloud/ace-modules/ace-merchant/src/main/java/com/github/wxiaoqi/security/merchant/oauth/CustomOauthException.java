package com.github.wxiaoqi.security.merchant.oauth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义异常类，指定json序列化方式
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
	public CustomOauthException(String msg) {
		super(msg);
	}
}
