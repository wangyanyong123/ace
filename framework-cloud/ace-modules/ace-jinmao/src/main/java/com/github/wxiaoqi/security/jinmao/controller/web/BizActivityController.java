package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizActivityBiz;
import com.github.wxiaoqi.security.jinmao.vo.activity.in.SaveActivityParam;
import com.github.wxiaoqi.security.jinmao.vo.activity.in.UpdateStatus;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小组活动表
 *
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@RestController
@RequestMapping("web/bizActivity")
@CheckClientToken
@CheckUserToken
@Api(tags = "小组活动管理")
public class BizActivityController {

    @Autowired
    private BizActivityBiz bizActivityBiz;



    /**
     * 查询活动列表
     * @return
     */
    @RequestMapping(value = "/getActivityListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询活动列表---PC端", notes = "查询活动列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(1-草稿，2-已发布，3-已撤回,0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据活动标题、小组名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ActivityVo> getActivityListPc(String enableStatus, String searchVal, Integer page, Integer limit){
        List<ActivityVo> activityList =  bizActivityBiz.getActivityList(enableStatus, searchVal, page, limit);
        int total = bizActivityBiz.selectActivityCount(enableStatus, searchVal);
        return new TableResultResponse<ActivityVo>(total, activityList);
    }




    /**
     * 保存活动
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveActivityPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存活动---PC端", notes = "保存活动---PC端",httpMethod = "POST")
    public ObjectRestResponse saveActivityPc(@RequestBody @ApiParam SaveActivityParam params){
        return bizActivityBiz.saveActivity(params);
    }



    /**
     * 查询该租户下所属项目
     * @return
     */
    @RequestMapping(value = "/getProjectByTenantId",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询该租户下所属项目---PC端", notes = "查询该租户下所属项目---PC端",httpMethod = "GET")
    public TableResultResponse<ProjectVo> getProjectByTenantId(){
        List<ProjectVo> projectList =  bizActivityBiz.getProjectByTenantId();
        return new TableResultResponse<ProjectVo>(projectList.size(), projectList);
    }



    /**
     * 查询项目下所属小组
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getGroupByProjectId/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询项目下所属小组---PC端", notes = "查询项目下所属小组---PC端",httpMethod = "GET")
    public TableResultResponse<GroupVo> getGroupByProjectId(@PathVariable String projectId){
        List<GroupVo> groupList = bizActivityBiz.getGroupByProjectId(projectId);
        return new TableResultResponse<GroupVo>(groupList.size(),groupList);
    }


    /**
     * 查询活动详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getActivityInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询活动详情---PC端", notes = "查询活动详情---PC端",httpMethod = "GET")
    public TableResultResponse<ActivityInfo> getActivityInfoPc(@PathVariable String id){
        List<ActivityInfo> activityInfo = bizActivityBiz.getActivityInfo(id);
        return new TableResultResponse<ActivityInfo>(activityInfo.size(),activityInfo);
    }



    /**
     * 活动操作(0-删除,2-发布,3-撤回)
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateActivityStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "活动操作(0-删除,2-发布,3-撤回)---PC端", notes = "活动操作(0-删除,2-发布,3-撤回)---PC端",httpMethod = "POST")
    public ObjectRestResponse updateActivityStatusPc(@RequestBody @ApiParam UpdateStatus params){
        return bizActivityBiz.updateActivityStatus(params);
    }




    /**
     * 编辑活动
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateActivityPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑活动---PC端", notes = "编辑活动---PC端",httpMethod = "POST")
    public ObjectRestResponse updateActivityPc(@RequestBody @ApiParam SaveActivityParam params){
        return bizActivityBiz.updateActivity(params);
    }








    /**
     * 查询用户反馈列表
     * @return
     */
    @RequestMapping(value = "/getFeedbackList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户反馈列表---PC端", notes = "查询用户反馈列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间(格式yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间(格式yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据用户姓名,电话模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<FeedbackVo> getFeedbackList(String projectId,String startTime, String endTime, String searchVal, Integer page, Integer limit){
        List<FeedbackVo> activityList =  bizActivityBiz.getFeedbackList(projectId,startTime, endTime, searchVal, page, limit);
        int total = bizActivityBiz.selectFeedbackCount(projectId,startTime, endTime, searchVal);
        return new TableResultResponse<FeedbackVo>(total, activityList);
    }




    /**
     * 查询活动下的报名列表
     * @return
     */
    @RequestMapping(value = "/getUserApplyListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询活动下的报名列表---PC端", notes = "查询活动下的报名列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="signType",value="状态查询(1-未签到，2-已签到,0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<UserApplyVo> getUserApplyListPc(String id,String signType, Integer page, Integer limit){
        List<UserApplyVo> userApplyList =  bizActivityBiz.getUserApplyList(id,signType, page, limit);
        int total = bizActivityBiz.selectUserApplyCount(id,signType);
        return new TableResultResponse<UserApplyVo>(total, userApplyList);
    }


    /**
     * 导出活动报名列表excel
     * @return
     */
    @RequestMapping(value = "/exportExcel",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "导出活动报名列表excel---PC端", notes = "导出活动报名列表excel---PC端",httpMethod = "DELETE")
    @ApiImplicitParam(name="id",value="活动id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    public ObjectRestResponse exportExcel(String id){
        return bizActivityBiz.exportExcel(id);
    }



    /**
     * 查询活动签到二维码
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAllQRList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询活动签到二维码---PC端", notes = "查询活动签到二维码---PC端",httpMethod = "GET")
    public TableResultResponse<QRVo> getAllQRList(String id){
        List<QRVo> activityInfo = bizActivityBiz.getAllQRList(id);
        return new TableResultResponse<QRVo>(activityInfo.size(),activityInfo);
    }



}