package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.fegin.AnnouncementFegin;
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
@RequestMapping("ann")
@CheckClientToken
@CheckUserToken
@Api(tags="APP物业公告接口")
public class BaseAppAnnController {

	@Autowired
	private AnnouncementFegin announcementFegin;

	@GetMapping("getAnnList")
	@ApiOperation(value = "查询物业公告列表", notes = "查询物业公告列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public ObjectRestResponse getAnnList(String projectId,Integer page, Integer limit) {
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(projectId)) {
			return result.data("项目id为空");
		}
		result = announcementFegin.getAppAnnouncementList(projectId,page,limit);
		return result;
	}


	@GetMapping("getAnnInfo")
	@ApiOperation(value = "查询物业公告详情", notes = "查询物业公告详情",httpMethod = "GET")
	@ApiImplicitParam(name="id",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
	public ObjectRestResponse getAnnInfo(String id){
		ObjectRestResponse result = new ObjectRestResponse();
		if(StringUtils.isEmpty(id)) {
			return result.data("项目id为空");
		}
		result = announcementFegin.getAppAnnouncementInfo(id);
		return result;
	}

}