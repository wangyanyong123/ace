package com.github.wxiaoqi.security.app.oauth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:00 2018/12/28
 * @Modified By:
 */
@Component("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

		OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
		return ResponseEntity
				.status(oAuth2Exception.getHttpErrorCode())
				.body(new CustomOauthException(oAuth2Exception.getMessage()));
	}
}