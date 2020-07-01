package com.github.wxiaoqi.security.jinmao.controller.app;

import com.github.wxiaoqi.security.api.vo.household.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizCrmHouseBiz;
import com.github.wxiaoqi.security.jinmao.entity.BizCrmHouse;
import com.github.wxiaoqi.security.jinmao.feign.HouseHoldFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:56 2018/12/5
 * @Modified By:
 */
@RestController
@RequestMapping("app/household")
@CheckClientToken
@CheckUserToken
@Api(tags="住户查询模块")
public class HouseHoldController {
	@Autowired
	private HouseHoldFeign houseHoldFeign;
	@Autowired
	private BizCrmHouseBiz crmHouseBiz;

	@GetMapping(value = "/getCityProjectList")
	@ApiOperation(value = "获取城市-社区列表", notes = "获取城市-社区列表",httpMethod = "GET")
	public ObjectRestResponse<List<CityProjectInfoVo>> getCityProjectList(){
		return houseHoldFeign.getCityProjectList();
	}

	@GetMapping(value = "/getCityList")
	@ApiOperation(value = "获取城市列表", notes = "获取城市列表",httpMethod = "GET")
	@ApiImplicitParam(name="cityName",value="城市名",dataType="String",required = false ,paramType = "query",example="深圳市")
	public ObjectRestResponse<List<CityInfoVo>> getCityList(String cityName){
		return houseHoldFeign.getCityList(cityName);
	}
	@GetMapping(value = "/getProjectList")
	@ApiOperation(value = "获取社区列表", notes = "获取社区列表",httpMethod = "GET")
	@ApiImplicitParam(name="cityId",value="城市Id",dataType="String",required = true ,paramType = "query",example="gbfdfafdgfddfd")
	public ObjectRestResponse<List<ProjectInfoVo>> getProjectList(String cityId){
		return houseHoldFeign.getProjectList(cityId);
	}

	@GetMapping(value = "/getHouseInfoTree")
	@ApiOperation(value = "获取房屋列表", notes = "获取房屋列表",httpMethod = "GET")
	@ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="深圳市")
	public TableResultResponse<HouseInfoTree> getHouseInfoTreeByProjectId(String projectId){
		return houseHoldFeign.getHouseInfoTree(projectId);
	}
	@GetMapping("getUserInfoByHouseId")
	@ApiOperation(value = "获得房屋内的用戶信息", notes = "获得房屋内的用戶信息",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="houseId",value="房屋id",dataType="String",required = true ,paramType = "query",example="138****1234"),
			@ApiImplicitParam(name="searchVal",value="根据输入信息模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<UserInfo> getUserInfoByHouseId(String houseId, String searchVal, Integer page, Integer limit) {
		return houseHoldFeign.getUserInfoByHouseIdWeb(houseId, searchVal, page, limit);
	}


	@GetMapping("getRegistryByProject")
	@ApiOperation(value = "导出项目下的用户注册状态", notes = "导出项目下的用户注册状态",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true ,paramType = "query",example="138****1234")

	})
	public ObjectRestResponse getRegistryByProject(String projectId) {
		ObjectRestResponse response = new ObjectRestResponse();
		if (StringUtils.isEmpty(projectId)) {
			response.setMessage("项目不能为空");
			response.setStatus(101);
			return response;
		}
		return crmHouseBiz.getRegistryByProject(projectId);
	}
}
