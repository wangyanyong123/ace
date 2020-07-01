package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizCrmHouseBiz;
import com.github.wxiaoqi.security.app.biz.BizUserHouseBiz;
import com.github.wxiaoqi.security.app.vo.city.out.BlockInfoVo;
import com.github.wxiaoqi.security.app.vo.city.out.HouseInfoVo;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoTree;
import com.github.wxiaoqi.security.app.vo.house.HouseInfoVO;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 房屋表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@RestController
@RequestMapping("house")
@CheckClientToken
@CheckUserToken
@Api(tags = "房屋接口管理")
public class BizCrmHouseController {
	@Autowired
	private BizCrmHouseBiz crmHouseBiz;

	@Autowired
	private BizUserHouseBiz userHouseBiz;

	@GetMapping(value = "/getUnitInfoListByFloorId")
	@ApiOperation(value = "获取房屋列表", notes = "获取房屋列表",httpMethod = "GET")
	@ApiImplicitParam(name="floorId",value="楼层id",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
	public ObjectRestResponse<List<HouseInfoVo>> getUnitInfoListByFloorId(String floorId){
		int type = 1;
		return crmHouseBiz.getUnitInfoListByFloorId(floorId,type);
	}

	@GetMapping(value = "/getHouseInfoList")
	@ApiOperation(value = "获取房屋列表", notes = "获取房屋列表",httpMethod = "GET")
	@ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="深圳市")
	public ObjectRestResponse<List<BlockInfoVo>> getHouseInfoList(String projectId){
		int type = 1;
		return crmHouseBiz.getHouseInfoList(projectId,type);
	}

	@GetMapping(value = "/getHouseInfoTree")
	@ApiOperation(value = "获取房屋列表", notes = "获取房屋列表",httpMethod = "GET")
	@ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="深圳市")
	@IgnoreClientToken
	public TableResultResponse<HouseInfoTree> getHouseInfoTreeByProjectId(String projectId){
		int type = 1;
		return crmHouseBiz.getHouseInfoTree(projectId,type);
	}

	@GetMapping(value = "/getPublicAreaList")
	@ApiOperation(value = "获取公共区域列表", notes = "获取公共区域列表",httpMethod = "GET")
	@ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="深圳市")
	public ObjectRestResponse<List<BlockInfoVo>> getPublicAreaList(String projectId){
		int type = 2;
		return crmHouseBiz.getHouseInfoList(projectId,type);
	}

	@GetMapping("getHomesHouses")
	@ApiOperation(value = "获取家里的房屋列表", notes = "获取家里的房屋列表",httpMethod = "GET")
	public ObjectRestResponse<List<HouseInfoVO>> getHomesHouses() {
		return userHouseBiz.getHomesHouses();
	}
}