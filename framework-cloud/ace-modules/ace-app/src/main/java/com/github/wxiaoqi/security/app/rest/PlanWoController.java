package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.order.out.PlanWoDetailVo;
import com.github.wxiaoqi.security.app.biz.BizWoBiz;
import com.github.wxiaoqi.security.app.vo.plan.PlanWoInVo;
import com.github.wxiaoqi.security.app.vo.plan.in.PlanWoParamVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author huangxl
 * @Date 2019-2-27 18:00:01
 */
@RestController
@RequestMapping("planWo")
@CheckClientToken
@CheckUserToken
@Api(tags="计划工单接口")
public class PlanWoController {

	@Autowired
	private BizWoBiz bizWoBiz;

	/**
	 * 生成工单
	 * @param createWoInVo 参数
	 * @return
	 */
	@RequestMapping(value = "/createWo" ,method = RequestMethod.POST)
	@IgnoreUserToken
	@ApiIgnore
	public ObjectRestResponse createWo(@RequestBody PlanWoInVo createWoInVo) throws Exception {
		return bizWoBiz.createPlanWoOrder(createWoInVo);
	}

	@RequestMapping(value = "/getPlanWoContentById" ,method = RequestMethod.GET)
	@ApiOperation(value = "获取工单的内容", notes = "获取工单的内容",httpMethod = "GET")
	@ApiImplicitParam(name="woId",value="工单ID",dataType="String",required = true ,paramType = "query",example="")
	public ObjectRestResponse<List<PlanWoDetailVo>> getPlanWoContentById(String woId){
		return bizWoBiz.getPlanWoContentById(woId);
	}

	@RequestMapping(value = "/savePlanWo" ,method = RequestMethod.POST)
	@ApiOperation(value = "保存工单", notes = "保存工单",httpMethod = "POST")
	public ObjectRestResponse savePlanWo(@RequestBody @ApiParam PlanWoParamVo planWoParamVo){
		return bizWoBiz.savePlanWo(planWoParamVo);
	}

	@RequestMapping(value = "/savePlanWoByOne" ,method = RequestMethod.POST)
	@ApiOperation(value = "保存工单--单个房间", notes = "保存工单--单个房间",httpMethod = "POST")
	public ObjectRestResponse savePlanWoByOne(@RequestBody @ApiParam PlanWoParamVo planWoParamVo){
		return bizWoBiz.savePlanWoByOne(planWoParamVo);
	}

}