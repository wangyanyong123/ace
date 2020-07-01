package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizCommunityTopicBiz;
import com.github.wxiaoqi.security.app.biz.BizFamilyPostsBiz;
import com.github.wxiaoqi.security.app.biz.BizForumPostsBiz;
import com.github.wxiaoqi.security.app.vo.topic.in.SaveFamilyPostsParam;
import com.github.wxiaoqi.security.app.vo.topic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsInfo;
import com.github.wxiaoqi.security.app.vo.topic.out.FamilyPostsVo;
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
 * 家里人帖子表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@RestController
@RequestMapping("bizFamilyPosts")
@CheckClientToken
@CheckUserToken
@Api(tags = "APP家里人帖子管理")
public class BizFamilyPostsController {

    @Autowired
    private BizFamilyPostsBiz bizFamilyPostsBiz;
    @Autowired
    private BizCommunityTopicBiz bizCommunityTopicBiz;
    @Autowired
    private BizForumPostsBiz bizForumPostsBiz;


    /**
     * 查询项目下的帖子活动
     * @return
     */
    @RequestMapping(value = "/getAllPostsList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询项目下的帖子活动---APP端", notes = "查询项目下的帖子活动---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<FamilyPostsVo>> getAllPostsList(String projectId, Integer page, Integer limit){
        return  bizFamilyPostsBiz.getAllPostsList(projectId, page, limit);
    }


    /**
     * 查询家里人帖子列表
     * @return
     */
    @RequestMapping(value = "/getFamilyPostsList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询家里人帖子列表---APP端", notes = "查询家里人帖子列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<FamilyPostsVo>> getFamilyPostsList(String projectId, Integer page, Integer limit){
        return  bizFamilyPostsBiz.getFamilyPostsList(projectId, page, limit);
    }


    /**
     * 查询好友的家里人帖子列表
     * @return
     */
    @RequestMapping(value = "/getMyFriendFamilyPostsList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询好友的家里人帖子列表---APP端", notes = "查询好友的家里人帖子列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="userId",value="用户id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<FamilyPostsVo>> getMyFriendFamilyPostsList(String projectId,String userId, Integer page, Integer limit){
        return  bizFamilyPostsBiz.getMyFriendFamilyPostsList(projectId,userId, page, limit);
    }


    /**
     * 查询家里人帖子详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getFamilyPostsInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询家里人帖子详情---h5", notes = "查询家里人帖子详情---h5",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<FamilyPostsInfo> getFamilyPostsInfo(String id,String projectId){
        String temp = "0";
        return bizFamilyPostsBiz.getFamilyPostsInfo(id,projectId,temp);
    }




    /**
     * 保存家里人帖子
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveFamilyPosts" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存家里人帖子---APP端", notes = "保存家里人帖子---APP端",httpMethod = "POST")
    public ObjectRestResponse saveFamilyPosts(@RequestBody @ApiParam SaveFamilyPostsParam param){
        return  bizForumPostsBiz.saveFamilyPosts(param);
    }


    /**
     * 家里人帖子操作置顶,隐藏
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateFamilyPostsStatus" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "家里人帖子操作置顶,隐藏---h5", notes = "家里人帖子操作置顶,隐藏---h5",httpMethod = "POST")
    public ObjectRestResponse updateFamilyPostsStatus(@RequestBody @ApiParam UpdateTopicStatus param){
        return  bizFamilyPostsBiz.updateFamilyPostsStatus(param);
    }



    /**
     * 分享家里人帖子详情
     * @param id
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/shareFamilyPostsInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享家里人帖子详情---h5", notes = "分享家里人帖子详情---h5",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<FamilyPostsInfo> shareFamilyPostsInfo(String id){
        return bizFamilyPostsBiz.getFamilyPostsInfo(id,null,"1");
    }





}