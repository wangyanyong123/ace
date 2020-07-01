package com.github.wxiaoqi.security.app.oauth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:55 2018/12/28
 * @Modified By:
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
	public CustomOauthException(String msg) {
		super(msg);
	}
}
