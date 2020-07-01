package com.github.wxiaoqi.security.report.rest;

import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.report.biz.ReportRegisterStatisticsBiz;
import com.github.wxiaoqi.security.report.vo.BuildRegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

import java.util.List;

/**
 * 楼栋用户注册载量表
 *
 * @author zxl
 * @Date 2019-03-11 14:26:24
 */
@RestController
@RequestMapping("registerStatistics")
@CheckClientToken
@CheckUserToken
@Api(tags = "楼栋注册统计")
public class ReportRegisterStatisticsController {

	@Autowired
	private ReportRegisterStatisticsBiz reportRegisterStatisticsBiz;

	@GetMapping("buildRegister")
	@ApiOperation(value = "楼栋注册统计", notes = "楼栋注册统计",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="dsvfgbfgsgfdf123444514"),
			@ApiImplicitParam(name="beginDate",value="开始时间 格式：yyyy-MM-dd",dataType="String",paramType = "query",example="2019-01-29"),
			@ApiImplicitParam(name="endDate",value="结束时间 格式：yyyy-MM-dd",dataType="String",paramType = "query",example="2019-01-29"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<BuildRegisterVo> buildRegister(String projectId, String beginDate, String endDate, Integer page, Integer limit) {
		List<BuildRegisterVo> buildRegisterVos = reportRegisterStatisticsBiz.buildRegister(projectId,beginDate, endDate, page, limit);
		int total = reportRegisterStatisticsBiz.buildRegisterCount(projectId,beginDate, endDate);
		return new TableResultResponse<BuildRegisterVo>(total, buildRegisterVos);
	}
}