package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizUserInviteBiz;
import com.github.wxiaoqi.security.app.biz.BizUserHouseBiz;
import com.github.wxiaoqi.security.app.vo.useraudit.in.AuditUserVo;
import com.github.wxiaoqi.security.app.vo.useraudit.in.DeleteUserVo;
import com.github.wxiaoqi.security.app.vo.useraudit.in.InviteUserVo;
import com.github.wxiaoqi.security.app.vo.useraudit.out.HouseAllUserInfosVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户申请成为房屋家属、租客表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("userAudit")
@CheckClientToken
@CheckUserToken
@Api(tags="APP客户端审核管理接口")
public class BizUserAuditController {

	@Autowired
	private BizUserInviteBiz userInviteBiz;

	@Autowired
	private BizUserHouseBiz userHouseBiz;

	@PostMapping("inviteUser")
	@ApiOperation(value = "邀请用户", notes = "邀请用户",httpMethod = "POST")
	public ObjectRestResponse inviteUser(@RequestBody @ApiParam InviteUserVo userVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(null == userVo){
			response.setStatus(501);
			response.setMessage("参数为空！");
			return response;
		}
		if(StringUtils.isEmpty(userVo.getIdentityType())){
			response.setStatus(502);
			response.setMessage("身份类型不能为空！");
			return response;
		}
		if(StringUtils.isEmpty(userVo.getHouseId())){
			response.setStatus(503);
			response.setMessage("房屋id不能为空！");
			return response;
		}
		if(StringUtils.isEmpty(userVo.getMobile())){
			response.setStatus(504);
			response.setMessage("手机号不能为空！");
			return response;
		}
		return userInviteBiz.inviteUser(userVo);

	}

	@PostMapping("audit")
	@ApiOperation(value = "审核", notes = "审核",httpMethod = "POST")
	public ObjectRestResponse audit( @RequestBody @ApiParam AuditUserVo userVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(null == userVo){
			response.setStatus(501);
			response.setMessage("参数为空！");
			return response;
		}
		if(StringUtils.isEmpty(userVo.getAuditId())){
			response.setStatus(502);
			response.setMessage("审核id不能为空！");
			return response;
		}
		if(StringUtils.isEmpty(userVo.getIsPass())){
			response.setStatus(503);
			response.setMessage("审核结果不能为空！");
			return response;
		}
		return userInviteBiz.audit(userVo);

	}

	@PostMapping("delete")
	@ApiOperation(value = "删除用户", notes = "删除用户",httpMethod = "POST")
	public ObjectRestResponse delete(@RequestBody @ApiParam DeleteUserVo userVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(null == userVo){
			response.setStatus(501);
			response.setMessage("参数为空！");
			return response;
		}
		if(StringUtils.isEmpty(userVo.getHouseId())){
			response.setStatus(502);
			response.setMessage("身份类型不能为空！");
			return response;
		}
		if(StringUtils.isEmpty(userVo.getHouseId())){
			response.setStatus(503);
			response.setMessage("房屋id不能为空！");
			return response;
		}
		return userInviteBiz.deleteUser(userVo);

	}

	@GetMapping("getHouseAllUserInfos")
	@ApiOperation(value = "获得房屋内的所有用户详细信息", notes = "获得房屋内的所有用户详细信息",httpMethod = "GET")
	@ApiImplicitParam(name="houseId",value="房屋id",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse<HouseAllUserInfosVo> getHouseAllUserInfos(String houseId) {
		return userHouseBiz.getHouseAllUserInfos(houseId);
	}
}