package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoTwoVo;
import com.github.wxiaoqi.security.api.vo.face.SysProjectInfoVo;
import com.github.wxiaoqi.security.app.biz.BizCrmProjectBiz;
import com.github.wxiaoqi.security.app.vo.city.out.ProjectInfoVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 项目表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@RestController
@RequestMapping("project")
@CheckClientToken
@CheckUserToken
@ApiIgnore
public class BizCrmProjectController {

	@Autowired
	private BizCrmProjectBiz crmProjectBiz;

	@GetMapping(value = "/getProjectList")
	@ApiOperation(value = "获取社区列表", notes = "获取社区列表",httpMethod = "GET")
	@ApiImplicitParam(name="cityId",value="城市Id",dataType="String",required = true ,paramType = "query",example="gbfdfafdgfddfd")
	@IgnoreClientToken
	public ObjectRestResponse<List<ProjectInfoVo>> getProjectList(String cityId){
		return crmProjectBiz.getProjectList(cityId);
	}

	@GetMapping(value = "/sysProjectInfo")
	@ApiOperation(value = "同步基础信息接口", notes = "同步基础信息接口",httpMethod = "GET")
	@ApiIgnore
	@IgnoreUserToken
	public ObjectRestResponse<List<SysProjectInfoVo>> sysProjectInfo(){
		return crmProjectBiz.sysProjectInfo();
	}

	@GetMapping(value = "/getSysProjectInfo")
	@ApiOperation(value = "同步基础信息接口", notes = "同步基础信息接口",httpMethod = "GET")
	@ApiIgnore
	@IgnoreUserToken
	public ObjectRestResponse<List<SysProjectInfoTwoVo>> getSysProjectInfo(){
		return crmProjectBiz.getSysProjectInfo();
	}
}