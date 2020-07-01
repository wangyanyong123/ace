package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BaseAppServerUserBiz;
import com.github.wxiaoqi.security.app.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.app.vo.AuthUser;
import com.github.wxiaoqi.security.app.vo.clientuser.in.ChangePasswordVo;
import com.github.wxiaoqi.security.app.vo.clientuser.in.RetrievePasswordVo;
import com.github.wxiaoqi.security.app.vo.serveruser.in.ActivateUserVo;
import com.github.wxiaoqi.security.app.vo.serveruser.out.ServerUserVo;
import com.github.wxiaoqi.security.app.vo.user.UpdateInfo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * app服务端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("serverUser")
@CheckClientToken
@CheckUserToken
@Api(tags="APP服务端用户管理接口")
public class BaseAppServerUserController {

	@Autowired
	private  BaseAppServerUserBiz appServerUserBiz;
	@Autowired
	private BaseAppServerUserMapper baseAppServerUserMapper;

	@Autowired
	private ToolFegin toolFegin;

	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping("volidCode")
	@ApiOperation(value = "获取激活验证码", notes = "获取激活验证码",httpMethod = "GET")
	@ApiImplicitParam(name="mobile",value="手机号",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse getValidCode(String mobile) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(mobile)) {
			result.setStatus(501);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isMobile(mobile)){
			try {
				BaseAppServerUser appServerUser = appServerUserBiz.getUserByMobile(mobile);
				if (null == appServerUser) {
					result.setStatus(506);
					result.setMessage("账号不存在！");
					return result;
				}else if(appServerUser.getIsActive().equals("1")){
					result.setStatus(506);
					result.setMessage("该账号已经激活，请前去登录！");
					return result;
				}
				if(!StringUtils.isEmpty(appServerUser.getId()) && "1".equals(appServerUser.getEnableStatus())
						&&"1".equals(appServerUser.getStatus())){
					result = toolFegin.getCode(mobile,4, 300,"1", MsgThemeConstants.CUSTOMER_ACTIVATE_REG,null);
					if(200 == result.getStatus()){
						result.setData(null);
					}
				}else {
					result.setStatus(503);
					result.setMessage("账号被禁用！");
					return result;
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setMessage("获取验证码失败");
			}
		}else {
			result.setStatus(502);
			result.setMessage("手机号错误！");
			return result;
		}
		return result;
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping("checkVolidCode")
	@ApiOperation(value = "校验激活验证码", notes = "校验激活验证码",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="mobile",value="手机号",dataType="String",required = true ,paramType = "query",example="138****1234"),
			@ApiImplicitParam(name="volidCode",value="验证码",dataType="String",required = true ,paramType = "query",example="138****1234")
	})
	public ObjectRestResponse checkVolidCode(String mobile,String volidCode) {
		return toolFegin.codeIsTrue(mobile,volidCode);
	}

	@IgnoreUserToken
	@PostMapping("activate")
	@ApiOperation(value = "激活账号", notes = "激活账号",httpMethod = "POST")
	public ObjectRestResponse<ServerUserVo> activate(@RequestBody @ApiParam ActivateUserVo activateUserVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == activateUserVo){
			result.setStatus(501);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(activateUserVo.getMobile())) {
			result.setStatus(502);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isMobile(activateUserVo.getMobile())){
			if(org.apache.commons.lang3.StringUtils.isNoneBlank(activateUserVo.getVolidCode())){
				result = toolFegin.checkCode(activateUserVo.getMobile(),activateUserVo.getVolidCode());
				if(200 == result.getStatus()){
					if(StringUtils.isEmpty(activateUserVo.getPassword())){
						result.setStatus(505);
						result.setMessage("密码为空！");
						return result;
					} else {
						BaseAppServerUser serverUser = this.appServerUserBiz.getUserByMobile(activateUserVo.getMobile());
						if (null == serverUser) {
							result.setStatus(506);
							result.setMessage("账号不存在！");
							return result;
						}
						serverUser.setPassword(activateUserVo.getPassword());
						appServerUserBiz.activateAccount(serverUser);
						ServerUserVo serverUserVo = new ServerUserVo();
						BeanUtils.copyProperties(serverUser, serverUserVo);
						return ObjectRestResponse.ok(serverUserVo);
					}
				}else {
					return result;
				}
			}else {
				result.setStatus(504);
				result.setMessage("验证码为空！");
				return result;
			}
		}else {
			result.setStatus(503);
			result.setMessage("手机号错误！");
			return result;
		}
	}

	@IgnoreUserToken
	@PostMapping("activateNew")
	@ApiOperation(value = "激活账号", notes = "激活账号",httpMethod = "POST")
	public ObjectRestResponse<ServerUserVo> activateNew(@RequestBody @ApiParam ActivateUserVo activateUserVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == activateUserVo){
			result.setStatus(501);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(activateUserVo.getMobile())) {
			result.setStatus(502);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isMobile(activateUserVo.getMobile())){
			if(StringUtils.isEmpty(activateUserVo.getPassword())){
				result.setStatus(503);
				result.setMessage("密码为空！");
				return result;
			} else {

				BaseAppServerUser serverUser = this.appServerUserBiz.getUserByMobile(activateUserVo.getMobile());

				if (null == serverUser) {
					result.setStatus(504);
					result.setMessage("账号不存在！");
					return result;
				}

				serverUser.setPassword(activateUserVo.getPassword());
				appServerUserBiz.activateAccount(serverUser);
				ServerUserVo serverUserVo = new ServerUserVo();
				BeanUtils.copyProperties(serverUser, serverUserVo);
				return ObjectRestResponse.ok(serverUserVo);
			}

		}else {
			result.setStatus(505);
			result.setMessage("手机号错误！");
			return result;
		}
	}


	@GetMapping("info")
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息",httpMethod = "GET")
	public ObjectRestResponse<ServerUserVo> info() {
		BaseAppServerUser serverUser = appServerUserBiz.getUserById(BaseContextHandler.getUserID());
		String companyName = baseAppServerUserMapper.selectCompanyNameById(BaseContextHandler.getUserID());
		ServerUserVo userVo = new ServerUserVo();
		BeanUtils.copyProperties(serverUser,userVo);
		userVo.setCompanyName(companyName);
		int num = appServerUserBiz.isHasSupervision(BaseContextHandler.getUserID());
		if(num>0){
			userVo.setIsSupervision("1");
		}else {
			userVo.setIsSupervision("0");
		}
		return ObjectRestResponse.ok(userVo);
	}

	@GetMapping("getProjectId")
	@ApiOperation(value = "获取项目id--仅限管家、客服、物业人员使用", notes = "获取项目id--仅限管家、客服、物业人员使用",httpMethod = "GET")
	public ObjectRestResponse getProjectId() {
		String projectId = appServerUserBiz.getProjectId(BaseContextHandler.getUserID());
		return ObjectRestResponse.ok(projectId);
	}

	/**
	 * 修改密码
	 */
	@PostMapping("changePassword")
	@ApiOperation(value = "修改密码", notes = "修改密码",httpMethod = "POST")
	public ObjectRestResponse changePassword(@RequestBody @ApiParam ChangePasswordVo changePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == changePasswordVo){
			result.setStatus(502);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(changePasswordVo.getNewPassword())) {
			result.setStatus(501);
			result.setMessage("新密码为空");
			return result;
		}
		if(StringUtils.isEmpty(changePasswordVo.getOldPassword())) {
			result.setStatus(503);
			result.setMessage("旧密码为空");
			return result;
		}
		return appServerUserBiz.changePassword(changePasswordVo);
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping("forgetVolidCode")
	@ApiOperation(value = "获取忘记密码验证码", notes = "获取忘记密码验证码",httpMethod = "GET")
	@ApiImplicitParam(name="mobile",value="手机号",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse forgetVolidCode(String mobile) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(mobile)) {
			result.setStatus(501);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isMobile(mobile)){
			BaseAppServerUser appServerUser = new BaseAppServerUser();
			appServerUser.setIsActive("1");
			appServerUser.setStatus("1");
			appServerUser.setMobilePhone(mobile);
			appServerUser = appServerUserBiz.selectOne(appServerUser);
			if (null != appServerUser && !StringUtils.isEmpty(appServerUser.getId())){
				try {
					result = toolFegin.getCode(mobile,4, 300,"1", MsgThemeConstants.FORGET_PASSWORD,null);
					if(200 == result.getStatus()){
						result.setData(null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					result.setMessage("获取验证码失败");
				}
			}else if(null != appServerUser && appServerUser.getIsActive().equals("0")) {
				result.setStatus(504);
				result.setMessage("该账号未被激活，请前去激活！");
				return result;
			}else {
				result.setStatus(503);
				result.setMessage("账号不存在！");
				return result;
			}
		}else {
			result.setStatus(502);
			result.setMessage("手机号错误！");
			return result;
		}
		return result;
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@PostMapping("retrievePassword")
	@ApiOperation(value = "重置密码", notes = "重置密码",httpMethod = "POST")
	public ObjectRestResponse retrievePassword(@RequestBody @ApiParam RetrievePasswordVo retrievePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == retrievePasswordVo){
			result.setStatus(501);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(retrievePasswordVo.getNewPassword())) {
			result.setStatus(502);
			result.setMessage("新密码为空");
			return result;
		}
		if(StringUtils.isEmpty(retrievePasswordVo.getMobilePhone())) {
			result.setStatus(503);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isEmpty(retrievePasswordVo.getVolidCode())) {
			result.setStatus(504);
			result.setMessage("验证码为空");
			return result;
		}
		return appServerUserBiz.retrievePassword(retrievePasswordVo);
	}
	@PostMapping("updateUserInfo")
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息",httpMethod = "POST")
	public ObjectRestResponse updateUserInfo(@RequestBody @ApiParam UpdateInfo updateInfo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == updateInfo){
			result.setStatus(501);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(updateInfo.getUserId())) {
			result.setStatus(502);
			result.setMessage("用户id不能为空");
			return result;
		}
		updateInfo.setNickname(null);
		updateInfo.setName(null);
		return appServerUserBiz.updateUserInfo(updateInfo);
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping("mobileStatus")
	@ApiOperation(value = "手机号状态", notes = "手机号状态",httpMethod = "GET")
	@ApiImplicitParam(name="mobile",value="手机号",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse mobileStatus(String mobile) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(mobile)) {
			result.setStatus(501);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isMobile(mobile)){
			BaseAppServerUser serverUser = appServerUserBiz.getUserByMobile(mobile);
			if (serverUser == null) {
				result.setStatus(505);
				result.setMessage("手机号不存在!");
				return result;
			}else {
				if( "0".equals(serverUser.getIsActive())){
					result.setStatus(503);
					result.setMessage("账号未激活!");
					return result;
				}
				if( "0".equals(serverUser.getEnableStatus()) || "0".equals(serverUser.getStatus())){
					result.setStatus(504);
					result.setMessage("账号被禁用!");
					return result;
				}
			}
		}else {
			result.setStatus(502);
			result.setMessage("手机号错误！");
			return result;
		}
		return result;
	}

	@IgnoreUserToken
	@ApiOperation("根据账户名获取用户信息")
	@RequestMapping(value = "/getUserInfoByUsername", method = RequestMethod.POST)
	public ObjectRestResponse<AuthUser> validate(String username) {
		AuthUser user = new AuthUser();
		BeanUtils.copyProperties(appServerUserBiz.getUserByUsername(username), user);
		user.setUsername(username);
		return new ObjectRestResponse<AuthUser>().data(user);
	}

}