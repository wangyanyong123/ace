package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizCrmFloorBiz;
import com.github.wxiaoqi.security.app.vo.city.out.FloorInfoVo;
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
 * 楼层表
 *
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@RestController
@RequestMapping("floor")
@CheckClientToken
@CheckUserToken
@Api(tags = "楼层信息管理接口")
public class BizCrmFloorController {
	@Autowired
	private BizCrmFloorBiz crmFloorBiz;

	@GetMapping(value = "/getFloorInfoListByUnitId")
	@ApiOperation(value = "获取楼层列表", notes = "获取楼层列表",httpMethod = "GET")
	@ApiImplicitParam(name="unitId",value="单元id",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
	public ObjectRestResponse<List<FloorInfoVo>> getFloorInfoListByUnitId(String unitId){
		int type = 1;
		return crmFloorBiz.getFloorInfoListByUnitId(unitId,type);
	}
}