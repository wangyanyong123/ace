package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizUserHouseBatchAuthBiz;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 批量认证表
 *
 * @author zxl
 * @Date 2019-09-25 15:19:18
 */
@RestController
@RequestMapping("batchAuth")
@ApiIgnore
public class BizUserHouseBatchAuthController {
	@Autowired
	private BizUserHouseBatchAuthBiz houseBatchAuthBiz;

	@GetMapping("batchAuth")
	@IgnoreUserToken
	@IgnoreClientToken
	public ObjectRestResponse batchAuth() {
		houseBatchAuthBiz.batchAuth();
		return ObjectRestResponse.ok("成功");
	}


}