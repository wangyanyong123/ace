package com.github.wxiaoqi.security.im.rest;

import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.im.biz.BizHousekeeperUserBiz;
import com.github.wxiaoqi.security.im.vo.housekeeperchat.out.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 
 *
 * @author zxl
 * @Date 2018-12-17 18:32:27
 */
@RestController
@RequestMapping("houseKeeperChat")
@CheckClientToken
@CheckUserToken
@Api(tags="员工端聊天")
public class HouseKeeperChatMessageController {
	@Autowired
	private BizHousekeeperUserBiz housekeeperUserBiz;

	@GetMapping("getUserInfoList")
	@ApiOperation(value = "获得已聊过天的用户列表", notes = "获得已聊过天的用户列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="searchVal",value="根据姓名（昵称）模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<UserInfoVo> getUserInfoList(String searchVal, Integer page, Integer limit){
		List<UserInfoVo> userInfoList = housekeeperUserBiz.getUserInfoList(searchVal, page, limit);
		int total = housekeeperUserBiz.getUserInfoNum(searchVal);
		return new TableResultResponse<UserInfoVo>(total, userInfoList);
	}

	@GetMapping("getBlockList")
	@ApiOperation(value = "获得地块列表---通讯录", notes = "获得地块列表---通讯录",httpMethod = "GET")
	public ObjectRestResponse<List<BlockInfoVo>> getBlockList(){
		return housekeeperUserBiz.getBlockList();
	}

	@GetMapping("getBuildList")
	@ApiOperation(value = "获得楼栋列表---通讯录", notes = "获得楼栋列表---通讯录",httpMethod = "GET")
	@ApiImplicitParam(name="blockId",value="地块id",dataType="String",required = true, paramType = "query",example="1sdsgsfdghsfdgsd")
	public ObjectRestResponse<List<BuildInfoVo>> getBuildList(String blockId){
		ObjectRestResponse<List<BuildInfoVo>> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(blockId)){
			response.setStatus(501);
			response.setMessage("blockId不能为空！");
			return response;
		}
		response.setData(housekeeperUserBiz.getBuildList(blockId));
		return response;
	}

	@GetMapping("getUnitList")
	@ApiOperation(value = "获得单元列表---通讯录", notes = "获得单元列表---通讯录",httpMethod = "GET")
	@ApiImplicitParam(name="buildId",value="楼栋id",dataType="String",required = true, paramType = "query",example="1sdsgsfdghsfdgsd")
	public ObjectRestResponse<List<UnitInfoVo>> getUnitList(String buildId){
		ObjectRestResponse<List<UnitInfoVo>> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(buildId)){
			response.setStatus(501);
			response.setMessage("buildId不能为空！");
			return response;
		}
		response.setData(housekeeperUserBiz.getUnitList(buildId));
		return response;
	}

	@GetMapping("getFloorList")
	@ApiOperation(value = "获得楼层列表---通讯录", notes = "获得楼层列表---通讯录",httpMethod = "GET")
	@ApiImplicitParam(name="unitId",value="单元id",dataType="String",required = true, paramType = "query",example="1sdsgsfdghsfdgsd")
	public ObjectRestResponse<List<FloorInfoVo>> getFloorList(String unitId){
		ObjectRestResponse<List<FloorInfoVo>> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(unitId)){
			response.setStatus(501);
			response.setMessage("unitId不能为空！");
			return response;
		}
		response.setData(housekeeperUserBiz.getFloorList(unitId));
		return response;
	}

	@GetMapping("getHouseList")
	@ApiOperation(value = "获得房屋列表---通讯录", notes = "获得房屋列表---通讯录",httpMethod = "GET")
	@ApiImplicitParam(name="floorId",value="楼层id",dataType="String",required = true, paramType = "query",example="1sdsgsfdghsfdgsd")
	public ObjectRestResponse<List<HouseInfoVo>> getHouseList(String floorId){
		ObjectRestResponse<List<HouseInfoVo>> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(floorId)){
			response.setStatus(501);
			response.setMessage("floorId不能为空！");
			return response;
		}
		response.setData(housekeeperUserBiz.getHouseList(floorId));
		return response;
	}

	@GetMapping("getHouseholdList")
	@ApiOperation(value = "获得住户列表---通讯录", notes = "获得住户列表---通讯录",httpMethod = "GET")
	@ApiImplicitParam(name="houseId",value="房屋id",dataType="String",required = true, paramType = "query",example="1sdsgsfdghsfdgsd")
	public ObjectRestResponse<List<HouseholdVo>> getHouseholdList(String houseId){
		ObjectRestResponse<List<HouseholdVo>> response = new ObjectRestResponse<>();
		if (StringUtils.isEmpty(houseId)){
			response.setStatus(501);
			response.setMessage("houseId不能为空！");
			return response;
		}
		response.setData(housekeeperUserBiz.getHouseholdList(houseId));
		return response;
	}

}