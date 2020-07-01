package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BaseAppClientUserBiz;
import com.github.wxiaoqi.security.app.biz.BizUserGradeRuleBiz;
import com.github.wxiaoqi.security.app.biz.BizUserProjectBiz;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizUserProject;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.vo.clientuser.in.ChangePasswordVo;
import com.github.wxiaoqi.security.app.vo.clientuser.in.RetrievePasswordVo;
import com.github.wxiaoqi.security.app.vo.clientuser.in.UserRegisterVo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.intergral.out.UserGradeVo;
import com.github.wxiaoqi.security.app.vo.sms.SysMobileInfoVo;
import com.github.wxiaoqi.security.app.vo.user.UpdateInfo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * app客户端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("clientUser")
@CheckClientToken
@CheckUserToken
@Api(tags="APP客户端用户管理接口")
public class BaseAppClientUserController {

	@Autowired
	private BaseAppClientUserBiz appClientUserBiz;

	@Autowired
	private ToolFegin toolFegin;

	@Autowired
	private BizUserProjectBiz userProjectBiz;
	@Autowired
	private BizUserGradeRuleBiz gradeRuleBiz;

	@GetMapping("isLogin")
	@ApiOperation(value = "", notes = "验证是否已登录",httpMethod = "GET")
	public ObjectRestResponse checkVolidCode() {
		return ObjectRestResponse.ok();
	}

	@IgnoreUserToken
	@IgnoreClientToken
	@GetMapping("volidCode")
	@ApiOperation(value = "获取注册验证码", notes = "获取注册验证码",httpMethod = "GET")
	@ApiImplicitParam(name="mobile",value="手机号",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse getValidCode(String mobile) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(mobile)) {
			result.setStatus(501);
			result.setMessage("手机号为空");
			return result;
		}
		BaseAppClientUser userByMobile = this.appClientUserBiz.getUserByMobile(mobile);
		if (null != userByMobile) {
			result.setStatus(503);
			result.setMessage("该手机号码已注册，请前去登录！");
			return result;
		}
		if(StringUtils.isMobile(mobile)){
			try {
				result = toolFegin.getCode(mobile,4, 300,"1", MsgThemeConstants.CUSTOMER_SIDE_REG,null);
				if(200 == result.getStatus()){
					result.setData(null);
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
	@ApiOperation(value = "校验注册验证码", notes = "校验注册验证码",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="mobile",value="手机号",dataType="String",required = true ,paramType = "query",example="138****1234"),
			@ApiImplicitParam(name="volidCode",value="验证码",dataType="String",required = true ,paramType = "query",example="138****1234")
	})
	public ObjectRestResponse checkVolidCode(String mobile,String volidCode) {
		return toolFegin.codeIsTrue(mobile,volidCode);
	}


	@IgnoreUserToken
	@PostMapping("register")
	@ApiOperation(value = "注册", notes = "注册",httpMethod = "POST")
	public ObjectRestResponse<UserVo> register(@RequestBody @ApiParam UserRegisterVo userRegisterVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == userRegisterVo){
			result.setStatus(501);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(userRegisterVo.getMobile())) {
			result.setStatus(502);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isMobile(userRegisterVo.getMobile())){
			if(org.apache.commons.lang3.StringUtils.isNoneBlank(userRegisterVo.getVolidCode())){
				result = toolFegin.checkCode(userRegisterVo.getMobile(),userRegisterVo.getVolidCode());
				if(200 == result.getStatus()){
					if(StringUtils.isEmpty(userRegisterVo.getPassword())){
						result.setStatus(505);
						result.setMessage("密码为空！");
						return result;
					}else {
						BaseAppClientUser userByMobile = this.appClientUserBiz.getUserByMobile(userRegisterVo.getMobile());
						if (null != userByMobile) {
							result.setStatus(506);
							result.setMessage("该手机号码已注册，请前去登录！");
							return result;
						}
						BaseAppClientUser appuser = new BaseAppClientUser();
						appuser.setMobilePhone(userRegisterVo.getMobile());
						appuser.setPassword(userRegisterVo.getPassword());
						appuser.setNickname(userRegisterVo.getNickname());
						appuser.setRegistOs(userRegisterVo.getRegistOs());
						appClientUserBiz.insertSelective(appuser);
						if(!StringUtils.isEmpty(userRegisterVo.getProjectId())){
							BizUserProject bizUserProject = new BizUserProject();
							bizUserProject.setId(UUIDUtils.generateUuid());
							bizUserProject.setUserId(appuser.getId());
							bizUserProject.setProjectId(userRegisterVo.getProjectId());
							bizUserProject.setIsNow("1");
							bizUserProject.setStatus("1");
							bizUserProject.setCreateBy(appuser.getId());
							bizUserProject.setCreateTime(new Date());
							userProjectBiz.insertSelective(bizUserProject);
						}
						UserVo userVo = new UserVo();
						BeanUtils.copyProperties(appuser,userVo);
						return ObjectRestResponse.ok(userVo);
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

	@GetMapping("info")
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息",httpMethod = "GET")
	public ObjectRestResponse<UserVo> info() {
		BaseAppClientUser clientUser = appClientUserBiz.getUserById(BaseContextHandler.getUserID());
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(clientUser,userVo);
		CurrentUserInfosVo currentUserInfosVo = userProjectBiz.getCurrentUserInfos();
		UserGradeVo gradeVo = gradeRuleBiz.getMyGradeAndTask().getData();
		if (gradeVo != null) {
			userVo.setGradeImg(gradeVo.getGradeImg());
			userVo.setGradeTitle(gradeVo.getGradeTitle());
		}
		if(null != currentUserInfosVo && !StringUtils.isEmpty(currentUserInfosVo.getProjectId())){
			userVo.setIsAddCurrentProject("1");
			userVo.setAbbreviation(currentUserInfosVo.getAbbreviation());
		}else {
			userVo.setIsAddCurrentProject("0");
		}
		return ObjectRestResponse.ok(userVo);
	}

	@PostMapping("nickname")
	@ApiOperation(value = "修改昵称", notes = "修改昵称",httpMethod = "POST")
	@ApiImplicitParam(name="nickname",value="昵称",dataType="String",required = true ,paramType = "query",example="张三")
	public ObjectRestResponse<UserVo> saveNickname(String nickname) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(StringUtils.isEmpty(nickname)){
			response.setStatus(501);
			response.setMessage("昵称不能为空！");
			return response;
		}
		BaseAppClientUser clientUser = appClientUserBiz.getUserById(BaseContextHandler.getUserID());
		clientUser.setNickname(nickname);
		clientUser.setUpdTime(new Date());
		appClientUserBiz.updateSelectiveById(clientUser);
		UserVo userVo = new UserVo();
		BeanUtils.copyProperties(clientUser,userVo);
		return ObjectRestResponse.ok(userVo);
	}

	/**
	 * 修改密码
	 */
	@PostMapping("changePassword")
	@ApiOperation(value = "修改密码", notes = "修改密码",httpMethod = "POST")
	public ObjectRestResponse changePassword(@RequestBody @ApiParam ChangePasswordVo changePasswordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == changePasswordVo){
			result.setStatus(501);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(changePasswordVo.getNewPassword())) {
			result.setStatus(502);
			result.setMessage("新密码为空");
			return result;
		}
		if(StringUtils.isEmpty(changePasswordVo.getOldPassword())) {
			result.setStatus(503);
			result.setMessage("旧密码为空");
			return result;
		}
		return appClientUserBiz.changePassword(changePasswordVo);
	}

	@GetMapping("getCurrentUserInfos")
	@ApiOperation(value = "获取当前用户的当前所在的社区、房屋、角色", notes = "获取当前用户的当前所在的社区、房屋、角色",httpMethod = "GET")
	public ObjectRestResponse<CurrentUserInfosVo> getCurrentUserInfos() {
		return appClientUserBiz.getCurrentUserInfos();
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
		BaseAppClientUser userByMobile = this.appClientUserBiz.getUserByMobile(mobile);
		if (null == userByMobile) {
			result.setStatus(503);
			result.setMessage("该手机号码不存在，请前去注册！");
			return result;
		}
		if(StringUtils.isMobile(mobile)){
			BaseAppClientUser appClientUser = new BaseAppClientUser();
			appClientUser.setIsDeleted("1");
			appClientUser.setStatus("1");
			appClientUser.setMobilePhone(mobile);
			appClientUser = appClientUserBiz.selectOne(appClientUser);
			if (null != appClientUser && !StringUtils.isEmpty(appClientUser.getId())){
				try {
					result = toolFegin.getCode(mobile,4, 300,"1", MsgThemeConstants.FORGET_PASSWORD,null);
					if(200 == result.getStatus()){
						result.setData(null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					result.setMessage("获取验证码失败！");
				}
			}else {
				result.setStatus(509);
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
	public ObjectRestResponse retrievePassword(@RequestBody @ApiParam RetrievePasswordVo passwordVo) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(null == passwordVo){
			result.setStatus(502);
			result.setMessage("参数为空！");
			return result;
		}
		if(StringUtils.isEmpty(passwordVo.getNewPassword())) {
			result.setStatus(501);
			result.setMessage("新密码为空");
			return result;
		}
		if(StringUtils.isEmpty(passwordVo.getMobilePhone())) {
			result.setStatus(503);
			result.setMessage("手机号为空");
			return result;
		}
		if(StringUtils.isEmpty(passwordVo.getVolidCode())) {
			result.setStatus(504);
			result.setMessage("验证码为空");
			return result;
		}
		return appClientUserBiz.retrievePassword(passwordVo);
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
		return appClientUserBiz.updateUserInfo(updateInfo);
	}

	/**
	 * 用户手机信息更新
	 * @param mobileInfo 参数
	 * @return
	 */
	@RequestMapping(value = "/updateMobileInfo" ,method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户手机信息更新", notes = "用户手机信息更新",httpMethod = "POST")
	public ObjectRestResponse updateMobileInfo(@RequestBody @ApiParam SysMobileInfoVo mobileInfo) {
		String cid = mobileInfo.getCid();
		String userId = mobileInfo.getUserId();
		String clientType = mobileInfo.getClientType();
		String os = mobileInfo.getOs();
		String osVersion = mobileInfo.getOsVersion();
		String version = mobileInfo.getVersion();
		String macId = mobileInfo.getMacId();
		return toolFegin.updateMobileInfo(cid,userId,clientType,os,osVersion,version,macId);
	}
}