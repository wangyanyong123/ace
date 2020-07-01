package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizUserHouseBiz;
import com.github.wxiaoqi.security.app.vo.clientuser.in.ChooseHouseVo;
import com.github.wxiaoqi.security.app.vo.user.UserInfo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.CurrentHouseDetailInfosVo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.OtherHouseDetailInfosVo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.HouseInfoVo;
import com.github.wxiaoqi.security.app.vo.userhouse.out.UserHouseVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 用户和房屋关系表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("userHouse")
@CheckClientToken
@CheckUserToken
@Api(tags="APP客户端房屋管理接口")
public class BizUserHouseController {

	@Autowired
	private BizUserHouseBiz userHouseBiz;

	@PostMapping("addHouse")
	@ApiOperation(value = "添加房屋", notes = "添加房屋",httpMethod = "POST")
	public ObjectRestResponse addHouse(@RequestBody @ApiParam ChooseHouseVo chooseHouseVo) {
		ObjectRestResponse response = new ObjectRestResponse();
		if(null == chooseHouseVo){
			response.setStatus(501);
			response.setMessage("参数为空！");
			return response;
		}
		if(StringUtils.isEmpty(chooseHouseVo.getIdentityType())){
			response.setStatus(502);
			response.setMessage("身份类型不能为空！");
			return response;
		}
		if(StringUtils.isEmpty(chooseHouseVo.getHouseId())){
			response.setStatus(503);
			response.setMessage("房屋id不能为空！");
			return response;
		}
		return userHouseBiz.authOwner(chooseHouseVo);

	}



	@GetMapping("getHouseLists")
	@ApiOperation(value = "获得当前用户的所有房屋列表", notes = "获得当前用户的所有房屋列表",httpMethod = "GET")
	public ObjectRestResponse<UserHouseVo> getHouseLists() {
		return userHouseBiz.getHouseLists();
	}

	@GetMapping("getCurrentHouseDetailInfos")
	@ApiOperation(value = "获得当前用户的当前房屋的详细信息", notes = "获得当前用户的当前房屋的详细信息",httpMethod = "GET")
	public ObjectRestResponse<CurrentHouseDetailInfosVo> getCurrentHouseDetailInfos() {
		return userHouseBiz.getCurrentHouseDetailInfos();
	}
	@GetMapping("getOtherHousesDetailInfos")
	@ApiOperation(value = "获得当前用户的其他房屋的详细信息", notes = "获得当前用户的其他房屋的详细信息",httpMethod = "GET")
	public ObjectRestResponse<List<OtherHouseDetailInfosVo>> getOtherHousesDetailInfos() {
		return userHouseBiz.getOtherHousesDetailInfos();
	}

	@GetMapping("delete")
	@ApiOperation(value = "删除房屋", notes = "删除房屋",httpMethod = "GET")
	@ApiImplicitParam(name="houseId",value="房屋id",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse delete(String houseId) {
		return userHouseBiz.deleteHouse(houseId);
	}

	@GetMapping("switchHouse")
	@ApiOperation(value = "切换房屋", notes = "切换房屋",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="newHouseId",value="要切换的房屋id",dataType="String",required = true ,paramType = "query",example="138****1234")
	})
	public ObjectRestResponse switchHouse(String newHouseId) {
		return userHouseBiz.switchHouse(newHouseId);
	}

	@GetMapping("getCurrentHouse")
	@ApiOperation(value = "获取当前房屋", notes = "获取当前房屋",httpMethod = "GET")
	public ObjectRestResponse<HouseInfoVo> getCurrentHouse() {
		return userHouseBiz.getCurrentHouse();
	}


	@GetMapping("getUserInfoByHouseId")
	@ApiOperation(value = "获得房屋内的用戶信息", notes = "获得房屋内的用戶信息",httpMethod = "GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="houseId",value="房屋id",dataType="String",required = true ,paramType = "query",example="138****1234"),
		@ApiImplicitParam(name="searchVal",value="根据输入信息模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
		@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
		@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	@IgnoreClientToken
	public TableResultResponse<UserInfo> getUserInfoByHouseId(String houseId, String searchVal, Integer page, Integer limit) {
		List<UserInfo> userInfos = userHouseBiz.getUserInfoByHouseId(houseId, searchVal, page, limit);
		return new TableResultResponse<UserInfo>(userInfos.size(), userInfos);
	}


	@GetMapping("getUserInfoByHouseIdWeb")
	@ApiOperation(value = "获得房屋内的用戶信息", notes = "获得房屋内的用戶信息",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="houseId",value="房屋id",dataType="String",required = true ,paramType = "query",example="138****1234"),
			@ApiImplicitParam(name="searchVal",value="根据输入信息模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	@IgnoreClientToken
	@ApiIgnore
	public TableResultResponse<UserInfo> getUserInfoByHouseIdWeb(String houseId, String searchVal, Integer page, Integer limit) {
		List<UserInfo> userInfos = userHouseBiz.getUserInfoByHouseIdWeb(houseId, searchVal, page, limit);
		return new TableResultResponse<UserInfo>(userInfos.size(), userInfos);
	}
}
