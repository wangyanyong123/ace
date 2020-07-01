package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BizCommunityTopicBiz;
import com.github.wxiaoqi.security.app.biz.BizUserIntegralBiz;
import com.github.wxiaoqi.security.app.vo.posts.in.SaveLikeParam;
import com.github.wxiaoqi.security.app.vo.topic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.app.vo.topic.out.CommunityTopicInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 社区话题表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@RestController
@RequestMapping("bizCommunityTopic")
@CheckClientToken
@CheckUserToken
@Api(tags = "APP社区话题")
public class BizCommunityTopicController {

    @Autowired
    private BizCommunityTopicBiz bizCommunityTopicBiz;
    @Autowired
    private BizUserIntegralBiz integralBiz;


    /**
     * 查询帖子列表
     * @return
     */
    @RequestMapping(value = "/getCommunityTopicList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询社区话题列表---APP端", notes = "查询社区话题列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<CommunityTopicVo>> getCommunityTopicList(String projectId, Integer page, Integer limit){
        return  bizCommunityTopicBiz.getCommunityTopicList(projectId, page, limit);
    }


    /**
     * 查询社区话题详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCommunityTopicInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询社区话题详情---APP端", notes = "查询社区话题详情---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    })
    public ObjectRestResponse<CommunityTopicInfo> getCommunityTopicInfo(String id,String projectId){
        String temp = "0";
        //完成任务-阅读平台文章任务
        integralBiz.finishDailyTask("task_102", BaseContextHandler.getUserID());
        return bizCommunityTopicBiz.getCommunityTopicInfo(id,projectId,temp);
    }

    /**
     * 分享社区话题详情
     * @param id
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/shareCommunityTopicInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享社区话题详情---h5", notes = "分享社区话题详情---h5",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<CommunityTopicInfo> shareCommunityTopicInfo(String id){
        return bizCommunityTopicBiz.getCommunityTopicInfo(id,null,"1");
    }


    /**
     * 用户点赞与取消点赞操作
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveLikeOperating" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户点赞与取消点赞操作---APP端", notes = "用户点赞与取消点赞操作---APP端",httpMethod = "POST")
    public ObjectRestResponse saveLikeOperating(@RequestBody @ApiParam SaveLikeParam param){
        //完成任务-帖子点赞
        integralBiz.finishDailyTask("task_100", BaseContextHandler.getUserID());
        return  bizCommunityTopicBiz.saveLikeOperating(param);
    }


    /**
     *社区话题操作置顶,隐藏
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateCommunityTopicStatus" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "社区话题操作置顶,隐藏---h5", notes = "社区话题操作置顶,隐藏---h5",httpMethod = "POST")
    public ObjectRestResponse updateCommunityTopicStatus(@RequestBody @ApiParam UpdateTopicStatus param){
        return  bizCommunityTopicBiz.updateCommunityTopicStatus(param);
    }




}