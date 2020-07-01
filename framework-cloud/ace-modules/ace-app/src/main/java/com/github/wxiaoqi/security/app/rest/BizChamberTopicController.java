package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizChamberTopicBiz;
import com.github.wxiaoqi.security.app.biz.BizForumPostsBiz;
import com.github.wxiaoqi.security.app.vo.topic.in.SaveChamberTopicParam;
import com.github.wxiaoqi.security.app.vo.topic.in.SaveVoteParam;
import com.github.wxiaoqi.security.app.vo.topic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.app.vo.topic.out.ChamberTopicInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.ChamberTopicVo;
import com.github.wxiaoqi.security.app.vo.topic.out.TagVo;
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
 * 议事厅话题表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@RestController
@RequestMapping("bizChamberTopic")
@CheckClientToken
@CheckUserToken
@Api(tags = "App议事厅话题管理")
public class BizChamberTopicController {

    @Autowired
    private BizChamberTopicBiz bizChamberTopicBiz;
    @Autowired
    private BizForumPostsBiz bizForumPostsBiz;


    /**
     * 查询议事厅话题列表
     * @return
     */
    @RequestMapping(value = "/getChamberTopicList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询议事厅话题列表---APP端", notes = "查询议事厅话题列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="1-查询所有,2-查询用户发起的议事厅",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ChamberTopicVo>> getChamberTopicList(String type,String projectId, Integer page, Integer limit){
        return  bizChamberTopicBiz.getChamberTopicList(type,projectId, page, limit);
    }


    /**
     * 查询用户参与的议事厅话题列表
     * @return
     */
    @RequestMapping(value = "/getMyplayerChamberTopicList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户参与的议事厅话题列表---APP端", notes = "查询用户参与的议事厅话题列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<ChamberTopicVo>> getMyplayerChamberTopicList(String projectId, Integer page, Integer limit){
        return  bizChamberTopicBiz.getMyplayerChamberTopicList(projectId, page, limit);
    }



    /**
     * 保存议事厅话题
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveChamberTopic" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存议事厅话题---APP端", notes = "保存议事厅话题---APP端",httpMethod = "POST")
    public ObjectRestResponse saveChamberTopic(@RequestBody @ApiParam SaveChamberTopicParam param){
        return  bizForumPostsBiz.saveChamberTopic(param);
    }


    /**
     * 查询议事厅话题标签列表
     * @return
     */
    @RequestMapping(value = "/getTagList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询议事厅话题标签列表---APP端", notes = "查询议事厅话题标签列表---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<List<TagVo>> getTagList(String projectId){
        return  bizChamberTopicBiz.getTagList(projectId);
    }


    /**
     * 查询议事厅话题详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCommunityTopicInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询议事厅话题详情---h5", notes = "查询议事厅话题详情---h5",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    })
    public ObjectRestResponse<ChamberTopicInfo> getChamberTopicInfo(String id, String projectId){
        String temp = "0";
        return bizChamberTopicBiz.getChamberTopicInfo(id,projectId,temp);
    }

    /**
     * 分享议事厅话题详情
     * @param id
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/shareChamberTopicInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享议事厅话题详情---h5", notes = "分享议事厅话题详情---h5",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<ChamberTopicInfo> shareChamberTopicInfo(String id){
        return bizChamberTopicBiz.getChamberTopicInfo(id,null,"1");
    }



    /**
     * 用户投票
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveTopicVote" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户投票---h5", notes = "用户投票---h5",httpMethod = "POST")
    public ObjectRestResponse saveTopicVote(@RequestBody @ApiParam SaveVoteParam param){
        return  bizChamberTopicBiz.saveTopicVote(param);
    }


    /**
     * 议事厅话题操作置顶,隐藏
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateChamberTopicStatus" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "议事厅话题操作置顶,隐藏---h5", notes = "议事厅话题操作置顶,隐藏---h5",httpMethod = "POST")
    public ObjectRestResponse updateChamberTopicStatus(@RequestBody @ApiParam UpdateTopicStatus param){
        return  bizChamberTopicBiz.updateChamberTopicStatus(param);
    }




}