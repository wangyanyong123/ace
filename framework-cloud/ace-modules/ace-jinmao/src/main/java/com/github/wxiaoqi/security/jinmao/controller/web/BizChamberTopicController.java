package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizChamberTopicBiz;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicVo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.UpdateTopicStatus;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 议事厅话题表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@RestController
@RequestMapping("web/bizChamberTopic")
@CheckClientToken
@CheckUserToken
@Api(tags = "议事厅话题管理")
public class BizChamberTopicController{

    @Autowired
    private BizChamberTopicBiz bizChamberTopicBiz;


    /**
     * 查询议事厅话题列表
     * @return
     */
    @RequestMapping(value = "/getChamberTopicList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询议事厅话题列表---PC端", notes = "查询议事厅话题列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="tagId",value="话题标签id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="topicType",value="帖子类型(1=话题，2=投票)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="showType",value="显示状态(0=隐藏，1=显示,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="isTop",value="置顶状态(0-未置顶，1-已置顶,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据帖子内容/发帖人模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ChamberTopicVo> getChamberTopicList(String projectId,String tagId,  String topicType, String showType,String isTop,
                                                                  String startTime,String endTime, String searchVal,Integer page, Integer limit){
        List<ChamberTopicVo> topicList =  bizChamberTopicBiz.getChamberTopicList(projectId, tagId, topicType, showType, isTop, startTime, endTime, searchVal, page, limit);
        int total = bizChamberTopicBiz.selectChamberTopicCount(projectId, tagId, topicType, showType, isTop, startTime, endTime, searchVal);
        return new TableResultResponse<ChamberTopicVo>(total, topicList);
    }


    /**
     * 查询议事厅话题详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getChamberTopicInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询议事厅话题详情---PC端", notes = "查询议事厅话题详情---PC端",httpMethod = "GET")
    public ObjectRestResponse<ChamberTopicInfo> getChamberTopicInfo(@PathVariable String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        return bizChamberTopicBiz.getChamberTopicInfo(id);
    }



    /**
     * 议事厅话题操作置顶,隐藏
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateFamilyPostsStatus" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "议事厅话题操作置顶,隐藏---PC端", notes = "议事厅话题操作置顶,隐藏---PC端",httpMethod = "POST")
    public ObjectRestResponse updateChamberTopicStatus(@RequestBody @ApiParam UpdateTopicStatus param){
        return  bizChamberTopicBiz.updateChamberTopicStatus(param);
    }








}