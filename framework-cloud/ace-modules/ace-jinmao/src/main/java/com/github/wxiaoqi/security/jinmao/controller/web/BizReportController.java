package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizReportBiz;
import com.github.wxiaoqi.security.jinmao.vo.report.in.StatusParam;
import com.github.wxiaoqi.security.jinmao.vo.report.out.FeedbackVo;
import com.github.wxiaoqi.security.jinmao.vo.report.out.ReportPersonVo;
import com.github.wxiaoqi.security.jinmao.vo.report.out.ReportVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 举报管理表
 *
 * @author huangxl
 * @Date 2019-03-04 17:13:39
 */
@RestController
@RequestMapping("web/bizReport")
@CheckClientToken
@CheckUserToken
@Api(tags = "举报管理")
public class BizReportController {

    @Autowired
    private BizReportBiz bizReportBiz;

    /**
     * 查询举报列表
     * @return
     */
    @RequestMapping(value = "/getReportListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询举报列表---PC端", notes = "查询举报列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="groupId",value="小组id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="reportType",value="举报对象(1-帖子,2-帖子评论,3-活动评论)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据活动标题、小组名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ReportVo> getReportListPc(String projectId, String groupId, String reportType, String searchVal,
                                                         Integer page, Integer limit){
        List<ReportVo> reportList =  bizReportBiz.getReportList(projectId, groupId, reportType, searchVal, page, limit);
        int total = bizReportBiz.selectReportCount(projectId, groupId, reportType, searchVal);
        return new TableResultResponse<ReportVo>(total, reportList);
    }


    /**
     * 允许/禁止评论,发帖操作
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateForbidStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "允许/禁止评论,发帖操作---PC端", notes = "允许/禁止评论,发帖操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateForbidStatusPc(@RequestBody @ApiParam StatusParam params){
        return bizReportBiz.updateForbidStatus(params);
    }


    /**
     * 查看举报人信息
     * @return
     */
    @RequestMapping(value = "/getReportPersonListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看举报人信息---PC端", notes = "查看举报人信息---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="reportId",value="举报id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据用户,电话模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ReportPersonVo> getReportPersonListPc(String reportId, String searchVal, Integer page, Integer limit){
        List<ReportPersonVo> reportPersonList =  bizReportBiz.getReportPersonList(reportId, searchVal, page, limit);
        int total = bizReportBiz.seelctReportPersonCount(reportId,searchVal);
        return new TableResultResponse<ReportPersonVo>(total, reportPersonList);
    }


    /**
     * 根据用户id查询反馈信息
     * @return
     */
    @RequestMapping(value = "/getFeedbackListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据用户id查询反馈信息---PC端", notes = "根据用户id查询反馈信息---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据用户,电话模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<FeedbackVo> getFeedbackListPc(String userId, String searchVal, Integer page, Integer limit){
        List<FeedbackVo> feedbackList =  bizReportBiz.getFeedbackList(userId,searchVal,page,limit);
        return new TableResultResponse<FeedbackVo>(feedbackList.size(), feedbackList);
    }


}