package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.jinmao.biz.BizReaderDetailBiz;
import com.github.wxiaoqi.security.jinmao.vo.stat.in.DataExcel;
import com.github.wxiaoqi.security.jinmao.vo.stat.out.StatDataVo;
import com.github.wxiaoqi.security.jinmao.vo.stat.out.StatTopicVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 阅读详情统计表
 *
 * @author huangxl
 * @Date 2019-08-14 11:25:32
 */
@RestController
@RequestMapping("web/bizReaderDetail")
@CheckClientToken
@CheckUserToken
@Api(tags = "数据审核管理")
public class BizReaderDetailController {

    @Autowired
    private BizReaderDetailBiz bizReaderDetailBiz;



    /**
     * 获取统计数据列表
     * @return
     */
    @RequestMapping(value = "/getStatDataList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取统计数据列表---PC端", notes = "获取统计数据列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="type",value="1-家里人,2-议事厅,3-社区话题,4-业主圈帖子",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="dataType",value="0-分钟1-小时,2-天,3-周,4-月",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<StatDataVo> getStatDataList(String projectId, String type, String dataType, String startTime, String endTime,
                                                                 Integer page, Integer limit){
        TableResultResponse msg = new TableResultResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t1 = "1";
        String t2 = "2";
        String t0 = "0";
        if(startTime != null  && endTime != null ) {
            int num = 0;
            try {
                num = DateUtils.getDaysBetween(sdf.parse(startTime), sdf.parse(endTime));
                if (t1.equals(dataType)) {
                    //小时
                    if (num > 7) {
                        msg.setStatus(1001);
                        msg.setMessage("起始日期与截止日期不能超过7天");
                        return msg;
                    }
                } else if (t0.equals(dataType)) {
                    //分钟
                    if (num > 1) {
                        msg.setStatus(1001);
                        msg.setMessage("起始日期与截止日期不能超过1天");
                        return msg;
                    }
                } else if (t2.equals(dataType)) {
                    //天
                    if (num > 1) {
                        msg.setStatus(1001);
                        msg.setMessage("起始日期与截止日期不能超过31天");
                        return msg;
                    }
                } else {
                    if (num > 365) {
                        msg.setStatus(1001);
                        msg.setMessage("起始日期与截止日期不能超过1年");
                        return msg;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<StatDataVo> statDataVoList =  bizReaderDetailBiz.getStatDataList(projectId, type, dataType, startTime, endTime, page, limit);
        int total = bizReaderDetailBiz.selectStatDataCount(projectId, type, dataType, startTime, endTime);
        return new TableResultResponse<StatDataVo>(total, statDataVoList);
    }


    /**
     * 导出统计数据excel
     * @return
     */
    @RequestMapping(value = "/exportDataListExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出统计数据excel---PC端", notes = "导出统计数据excel---PC端",httpMethod = "POST")
    public ObjectRestResponse exportDataListExcel(@RequestBody @ApiParam DataExcel excel){
        return bizReaderDetailBiz.exportDataListExcel(excel.getProjectId(), excel.getType(), excel.getDataType(), excel.getStartTime(), excel.getEndTime());
    }



    /**
     * 查询统计各帖子信息
     * @return
     */
    @RequestMapping(value = "/getStatTopicList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询统计各帖子信息---PC端", notes = "获取统计数据列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="type",value="1-家里人,2-议事厅,3-社区话题,4-业主圈帖子",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="dataType",value="0-分钟1-小时,2-天,3-周,4-月",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="pSort",value="pv排序(0-正序,1-倒序)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="uSort",value="uv排序(0-正序,1-倒序)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<StatTopicVo> getStatTopicList(String projectId, String type, String dataType, String startTime, String endTime,
                                                            String pSort,String uSort, Integer page, Integer limit){
        List<StatTopicVo> statDataVoList =  bizReaderDetailBiz.getStatTopicList(projectId, type, dataType, startTime, endTime,pSort,uSort, page, limit);
        //int total = bizReaderDetailBiz.selectStatTopicCount(projectId, type, dataType, startTime, endTime);
        return new TableResultResponse<StatTopicVo>(statDataVoList.size(), statDataVoList);
    }


    /**
     * 导出帖子列表excel
     * @return
     */
    @RequestMapping(value = "/exportStatTopicExcel",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "导出帖子列表excel---PC端", notes = "导出帖子列表excel---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="type",value="1-家里人,2-议事厅,3-社区话题,4-业主圈帖子",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="dataType",value="0-分钟1-小时,2-天,3-周,4-月",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="pSort",value="pv排序(0-正序,1-倒序)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="uSort",value="uv排序(0-正序,1-倒序)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse exportStatTopicExcel(String projectId, String type, String dataType, String startTime, String endTime,
                                                   String pSort,String uSort, Integer page, Integer limit){
        return bizReaderDetailBiz.exportStatTopicExcel(projectId, type, dataType, startTime, endTime, pSort, uSort, page, limit);
    }



}