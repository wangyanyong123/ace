package com.github.wxiaoqi.security.merchant.controller;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.merchant.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.merchant.vo.user.ChangePasswordVo;
import com.github.wxiaoqi.security.merchant.vo.user.ResetPasswordVO;
import com.github.wxiaoqi.security.merchant.vo.user.UpdateInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * app服务端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("user")
@CheckClientToken
@CheckUserToken
@Api(tags="APP商户端用户管理接口")
public class BaseAppServerUserController {

	@Autowired
	private BaseAppServerUserBiz appServerUserBiz;


	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping("sendCode")
	@ApiOperation(value = "发送验证码", notes = "发送验证码",httpMethod = "GET")
	public ObjectRestResponse sendCode(String mobilePhone) {
		return appServerUserBiz.sendCode(mobilePhone);
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@PostMapping("resetPassword")
	@ApiOperation(value = "重置密码", notes = "重置密码",httpMethod = "POST")
	public ObjectRestResponse resetPassword(@RequestBody @ApiParam @Valid ResetPasswordVO resetPasswordVO) {
		return appServerUserBiz.resetPassword(resetPasswordVO);
	}


	/**
	 * 修改密码
	 */
	@PostMapping("changePassword")
	@ApiOperation(value = "修改密码", notes = "修改密码",httpMethod = "POST")
	public ObjectRestResponse changePassword(@RequestBody @ApiParam @Valid ChangePasswordVo changePasswordVo) {
		return appServerUserBiz.changePassword(changePasswordVo);
	}


	@GetMapping("info")
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息",httpMethod = "GET")
	public ObjectRestResponse info() {
		return appServerUserBiz.getInfo();
	}

	/**
	 * 修改用户信息
	 * @return
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息",httpMethod = "POST")
	public ObjectRestResponse updateInfo(@RequestBody @ApiParam @Valid UpdateInfo updateInfo){
		return appServerUserBiz.updateInfo(updateInfo);
	}

}