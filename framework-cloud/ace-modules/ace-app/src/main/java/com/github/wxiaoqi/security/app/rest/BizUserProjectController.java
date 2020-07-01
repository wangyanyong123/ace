package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizUserProjectBiz;
import com.github.wxiaoqi.security.app.vo.userproject.out.OtherHousesAndProjectDetailInfosVo;
import com.github.wxiaoqi.security.app.vo.userproject.out.ProjectInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 用户和项目关系表
 *
 * @author zxl
 * @Date 2018-11-22 15:22:31
 */
@RestController
@RequestMapping("userProject")
@CheckClientToken
@CheckUserToken
@Api(tags="APP客户端用户社区管理接口")
public class BizUserProjectController {

	@Autowired
	private BizUserProjectBiz userProjectBiz;

	@PostMapping("add")
	@ApiOperation(value = "添加社区", notes = "添加社区",httpMethod = "POST")
	@ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324")
	public ObjectRestResponse register(@RequestParam("projectId") String projectId) {
		return userProjectBiz.insertProject(projectId);
	}

	@GetMapping("delete")
	@ApiOperation(value = "删除社区", notes = "删除社区",httpMethod = "GET")
	@ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse delete(String projectId) {
		return userProjectBiz.deleteProject(projectId);
	}

	@GetMapping("switchProject")
	@ApiOperation(value = "切换社区", notes = "切换社区",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="newProjectId",value="要切换的社区id",dataType="String",required = true ,paramType = "query",example="138****1234"),
			@ApiImplicitParam(name="autoSwitchHouse",value="是否自动切换房屋 0、不切换，1、切换",dataType="String",required = false ,paramType = "query",example="138****1234")
	})
	public ObjectRestResponse switchProject(String newProjectId,String autoSwitchHouse) {
		if(StringUtils.isEmpty(autoSwitchHouse)){
			autoSwitchHouse = "1";
		}
		System.out.println("autoSwitchHouse的值:"+autoSwitchHouse);
		return userProjectBiz.switchProject(newProjectId,autoSwitchHouse);
	}

	@GetMapping("getCurrentProject")
	@ApiOperation(value = "获取当前社区", notes = "获取当前社区",httpMethod = "GET")
	public ObjectRestResponse<ProjectInfoVo> getCurrentProject() {
		return userProjectBiz.getCurrentProject();
	}

	@GetMapping("getProjectLists")
	@ApiOperation(value = "获得当前用户的所有社区列表", notes = "获得当前用户的所有社区列表",httpMethod = "GET")
	public ObjectRestResponse<List<ProjectInfoVo>> getProjectLists() {
		return userProjectBiz.getProjectLists();
	}
//
//	@GetMapping("getCurrentHouseDetailInfos")
//	@ApiOperation(value = "获得当前用户的当前房屋的详细信息", notes = "获得当前用户的当前房屋的详细信息",httpMethod = "GET")
//	public ObjectRestResponse<CurrentHouseDetailInfosVo> getCurrentHouseDetailInfos() {
//		return userHouseBiz.getCurrentHouseDetailInfos();
//	}
//	@GetMapping("getOtherHousesDetailInfos")
//	@ApiOperation(value = "获得当前用户的其他房屋的详细信息", notes = "获得当前用户的其他房屋的详细信息",httpMethod = "GET")
//	public ObjectRestResponse<List<OtherHousesDetailInfosVo>> getOtherHousesDetailInfos() {
//		return userHouseBiz.getOtherHousesDetailInfos();
//	}

	@GetMapping("getOtherHousesAndProjectDetailInfos")
	@ApiOperation(value = "获得当前用户的其他社区和房屋的详细信息", notes = "获得当前用户的其他社区和房屋的详细信息",httpMethod = "GET")
	public ObjectRestResponse<List<OtherHousesAndProjectDetailInfosVo>> getOtherHousesAndProjectDetailInfos() {
		return userProjectBiz.getOtherHousesAndProjectDetailInfos();
	}
}