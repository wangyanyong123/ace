package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizCrmUnitBiz;
import com.github.wxiaoqi.security.app.vo.city.out.UnitInfoVo;
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
 * 单元表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:11
 */
@RestController
@RequestMapping("unit")
@CheckClientToken
@CheckUserToken
@Api(tags = "单元信息管理接口")
public class BizCrmUnitController {
	@Autowired
	private BizCrmUnitBiz crmUnitBiz;

	@GetMapping(value = "/getUnitInfoListByBuildId")
	@ApiOperation(value = "获取单元列表", notes = "获取单元列表",httpMethod = "GET")
	@ApiImplicitParam(name="buildId",value="楼栋id",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
	public ObjectRestResponse<List<UnitInfoVo>> getUnitInfoListByBuildId(String buildId){
		int type = 1;
		return crmUnitBiz.getUnitInfoListByBuildId(buildId,type);
	}
}