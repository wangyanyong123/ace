package com.github.wxiaoqi.security.app.rest;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.wxiaoqi.security.app.fegin.HotlineFegin;
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
@RequestMapping("hotline")
@CheckClientToken
@CheckUserToken
@Api(tags="APP热线服务接口")
public class BaseAppHotlineController {

	@Autowired
	private HotlineFegin hotlineFegin;

	@GetMapping("getHotlineList")
	@ApiOperation(value = "查询服务热线列表", notes = "查询服务热线列表",httpMethod = "GET")
	@ApiImplicitParam(name="projectId",value="项目id",dataType="String",required = true ,paramType = "query",example="138****1234")
	public ObjectRestResponse getHotlineList(String projectId) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(projectId)) {
			return result.data("项目id为空");
		}
		result = hotlineFegin.getAppHotlineList(projectId);
		return result;
	}

	@GetMapping("getSuperviseTel")
	@ApiOperation(value = "查询服务监督热线", notes = "查询服务监督热线",httpMethod = "GET")
	public ObjectRestResponse getSuperviseTel(){
		ObjectRestResponse result = new ObjectRestResponse();
		//获取Apollo配置
		Config config = ConfigService.getConfig("ace-app");
		String property = config.getProperty("announcement.superviseTel","");
		result.setData(property);
		return result;
	}

}