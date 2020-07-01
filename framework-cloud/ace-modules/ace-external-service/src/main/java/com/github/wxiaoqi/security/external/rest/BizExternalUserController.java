package com.github.wxiaoqi.security.external.rest;

import com.github.wxiaoqi.security.auth.client.dto.ExternalUserVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.external.biz.BizExternalUserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 对外提供接口用户信息
 *
 * @author zxl
 * @Date 2018-12-25 18:23:09
 */
@RestController
@RequestMapping("eUser")
@CheckClientToken
@ApiIgnore
public class BizExternalUserController {
	@Autowired
	private BizExternalUserBiz externalUserBiz;

	@RequestMapping(value = "/getExtrnalUser", method = RequestMethod.POST)
	public ObjectRestResponse<ExternalUserVo> getExtrnalUser(@RequestParam("appId") String appId){
		return externalUserBiz.getExtrnalUser(appId);
	}

	@RequestMapping(value = "/getExtrnalUserMenu", method = RequestMethod.POST)
	public ObjectRestResponse getExtrnalUserMenu(@RequestParam("appId") String appId, @RequestParam("uri")String uri){
		return externalUserBiz.getExtrnalUserMenu(appId,uri);
	}

}