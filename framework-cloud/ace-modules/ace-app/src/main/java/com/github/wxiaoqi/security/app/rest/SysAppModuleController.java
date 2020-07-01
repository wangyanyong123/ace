package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.fegin.ModuleFegin;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app客户端用户表
 *
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@RestController
@RequestMapping("module")
@CheckClientToken
@CheckUserToken
@Api(tags="APP模块接口")
public class SysAppModuleController {

	@Autowired
	private ModuleFegin moduleFegin;

	@GetMapping("getModuleList")
	@ApiOperation(value = "获取该项目下的App模块", notes = "获取该项目下的App模块",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="system",value="对应系统(1-客户端APP,2-服务端APP)",dataType="String",required = true ,paramType = "query",example="4"),
			@ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true ,paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public ObjectRestResponse getModuleList(String projectId,String system) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(projectId)) {
			return result.data("项目id为空");
		}
		result = moduleFegin.getAppModules(projectId, system);
		return result;
	}

}