package com.github.wxiaoqi.security.merchant.oauth.config;

import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:45 2018/12/26
 * @Modified By:
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

	@Autowired
	@Qualifier("consumerTokenServices")
	ConsumerTokenServices consumerTokenServices;

	@RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
	@ResponseBody
	public ObjectRestResponse revokeToken(String access_token) throws Exception {
		String realToken = getRealToken(access_token);
		ObjectRestResponse<Boolean> restResponse = new ObjectRestResponse<Boolean>();
		if (consumerTokenServices.revokeToken(realToken)) {
			restResponse.setData(true);
			restResponse.setMessage("注销成功");
		} else {
			restResponse.setData(false);
			restResponse.setMessage("注销失败");
		}
		return restResponse;
	}

	private String getRealToken(String originToken) {
		if (originToken != null && originToken.startsWith(RequestHeaderConstants.JWT_TOKEN_TYPE)) {
			originToken = originToken.substring(RequestHeaderConstants.JWT_TOKEN_TYPE.length(), originToken.length());
		}
		return originToken;
	}
}