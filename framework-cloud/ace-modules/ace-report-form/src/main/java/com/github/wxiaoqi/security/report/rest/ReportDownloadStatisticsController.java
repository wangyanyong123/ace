package com.github.wxiaoqi.security.report.rest;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.report.biz.ReportDownloadStatisticsBiz;
import com.github.wxiaoqi.security.report.entity.ReportDownloadStatistics;
import com.github.wxiaoqi.security.report.vo.RegisterAndAuthVo;
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
 * 用户下载量表
 *
 * @author huangxl
 * @Date 2019-01-29 14:25:41
 */
@RestController
@RequestMapping("downloadStatistics")
@CheckClientToken
@CheckUserToken
@Api(tags = "注册认证统计")
public class ReportDownloadStatisticsController {

	@Autowired
	private ReportDownloadStatisticsBiz statisticsBiz;

	@GetMapping("registerAndAuth")
	@ApiOperation(value = "注册和认证信息统计", notes = "注册和认证信息统计",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name="beginDate",value="开始时间 格式：yyyy-MM-dd",dataType="String",required = true ,paramType = "query",example="2019-01-29"),
			@ApiImplicitParam(name="endDate",value="结束时间 格式：yyyy-MM-dd",dataType="String",required = true,paramType = "query",example="2019-01-29"),
			@ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
			@ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
	})
	public TableResultResponse<RegisterAndAuthVo> registerAndAuth(String beginDate, String endDate, Integer page, Integer limit) {
		List<RegisterAndAuthVo> registerAndAuthVos = statisticsBiz.registerAndAuth(beginDate, endDate, page, limit);
		int total = statisticsBiz.registerAndAuthCount(beginDate, endDate);
		return new TableResultResponse<RegisterAndAuthVo>(total, registerAndAuthVos);
	}
}