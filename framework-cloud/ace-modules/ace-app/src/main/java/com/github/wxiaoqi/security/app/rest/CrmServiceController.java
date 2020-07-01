package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.CrmServiceBiz;
import com.github.wxiaoqi.security.app.entity.BizSubscribe;
import com.github.wxiaoqi.security.app.service.CrmServiceTask;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:41 2018/11/28
 * @Modified By:
 */
@RestController
@RequestMapping("clientUser")
@Api(tags="crm同步数据接口")
@ApiIgnore
public class CrmServiceController {
	@Autowired
	private CrmServiceTask crmServiceTask;
	@Autowired
	private CrmServiceBiz crmServiceBiz;
	@GetMapping("synchDataFromCrm")
	@ApiOperation(value = "同步数据", notes = "同步数据",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="date",value="时间",dataType="String",required = false ,paramType = "query",example="138****1234"),
			@ApiImplicitParam(name="project",value="项目编码，用逗号分隔",dataType="String",required = false ,paramType = "query",example="138****1234"),
			@ApiImplicitParam(name="type",value="类型",dataType="String",required = true ,paramType = "query",example="138****1234")
	})
	@IgnoreClientToken
	@IgnoreUserToken
	public ObjectRestResponse synchDataFromCrm(String date, String project, String type) {
		String[] projectArr = null;
		if(!StringUtils.isEmpty(project)){
			projectArr = project.split(",");
		}
		crmServiceTask.synchDataFromCrm(date,projectArr,type);
		return ObjectRestResponse.ok("成功");
	}

	@GetMapping("synchDataFromCrmByProject")
	@ApiOperation(value = "同步数据", notes = "同步数据",httpMethod = "GET")
	public ObjectRestResponse synchDataFromCrmByProject() {
		crmServiceTask.synchDataFromCrmByProject();
		return ObjectRestResponse.ok("成功");
	}

	@PostMapping("syncPropertyToCrm")
	@ApiOperation(value = "同步物业缴费数据", notes = "同步物业缴费数据", httpMethod = "POST")
	public ObjectRestResponse<BizSubscribe> sycPropertyToCrm(@RequestBody @ApiParam BizSubscribe bizSubscribe) {
		return crmServiceBiz.syncPropertyStatus(bizSubscribe);
	}
}
