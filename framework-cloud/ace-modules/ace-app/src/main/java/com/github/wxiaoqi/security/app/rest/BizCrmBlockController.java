package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizCrmBlockBiz;
import com.github.wxiaoqi.security.app.vo.city.out.BlockInfoVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
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
 * 地块表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@RestController
@RequestMapping("block")
@CheckClientToken
@CheckUserToken
@Api(tags = "地块信息管理接口")
public class BizCrmBlockController {

	@Autowired
	private BizCrmBlockBiz crmBlockBiz;

	@GetMapping(value = "/getBlockAndBuildInfoListByProjectId")
	@ApiOperation(value = "获取地块和楼栋列表", notes = "获取地块和楼栋列表",httpMethod = "GET")
	@ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
	public ObjectRestResponse<List<BlockInfoVo>> getBlockAndBuildInfoListByProjectId(String projectId){
		int type = 1;
		return crmBlockBiz.getBlockAndBuildInfoListByProjectId(projectId,type);
	}

}