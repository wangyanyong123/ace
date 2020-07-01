package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.fegin.AdHomePageFegin;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app客户端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("ad")
@CheckClientToken
@CheckUserToken
@Api(tags="app广告")
public class SysAppAdController {

	@Autowired
	private AdHomePageFegin adHomePageFegin;

    @IgnoreUserToken
    @IgnoreClientToken
	@GetMapping("getAdAppHomePageList")
	@ApiOperation(value = "获取app广告", notes = "获取app广告",httpMethod = "GET")
	public ObjectRestResponse getAdAppHomePageList() {
		ObjectRestResponse result = new ObjectRestResponse();
		result = adHomePageFegin.getAdAppHomePageList();
		return result;
	}

}