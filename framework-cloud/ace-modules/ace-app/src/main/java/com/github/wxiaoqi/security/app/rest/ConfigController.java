package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
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
 * @Author: Lzx
 * @Description:
 * @Date: Created in 16:23 2018/11/30
 * @Modified By:
 */
@RestController
@RequestMapping("config")
@CheckClientToken
@CheckUserToken
@Api(tags="配置管理")
public class ConfigController {

	@Autowired
	private ToolFegin toolFegin;

	@GetMapping("getOssConfig")
	@ApiOperation(value = "获取上传oss路径", notes = "获取上传oss路径", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="isPub",value="是否公有(1-公有，2-私有)",dataType="String",required = true ,paramType = "query",example="1")
	})
	public ObjectRestResponse getOssConfig(String isPub) {
		if(StringUtils.isEmpty(isPub)){
			ObjectRestResponse response = new ObjectRestResponse();
			response.setStatus(501);
			response.setMessage("isPub不能为空！");
			return response;
		}
		return toolFegin.getOssConfig(isPub);
	}

	@GetMapping("getSTSToken")
	@ApiOperation(value = "获取阿里云StsToken", notes = "获取阿里云StsToken", httpMethod = "GET")
	public ObjectRestResponse getSTSToken() {
		return toolFegin.getSTSToken();
	}
}
