package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.biz.BizForumPostsBiz;
import com.github.wxiaoqi.security.app.biz.BizUserIntegralBiz;
import com.github.wxiaoqi.security.app.vo.posts.in.*;
import com.github.wxiaoqi.security.app.vo.posts.out.CommentTree;
import com.github.wxiaoqi.security.app.vo.posts.out.PostsInfo;
import com.github.wxiaoqi.security.app.vo.posts.out.PostsVo;
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
 * 小组帖子表
 *
 * @author zxl
 * @Date 2018-12-18 10:45:45
 */
@RestController
@RequestMapping("bizForumPosts")
@CheckClientToken
@CheckUserToken
@Api(tags = "APP小组发帖(业主圈)")
public class BizForumPostsController{

    @Autowired
    private BizForumPostsBiz bizForumPostsBiz;
    @Autowired
    private BizUserIntegralBiz integralBiz;


    /**
     * 查询帖子列表
     * @return
     */
    @RequestMapping(value = "/getPostsList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询帖子列表---APP端", notes = "查询帖子列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="groupId",value="小组id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="postsType",value="帖子类型(1-普通帖子,2-精华帖)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<PostsVo>> getPostsList(String groupId, String postsType, Integer page, Integer limit){
        return  bizForumPostsBiz.getPostsList(groupId, postsType, page, limit);
    }


    /**
     * 查询帖子置顶列表
     * @return
     */
    @RequestMapping(value = "/getTopPostsList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询帖子置顶列表---APP端", notes = "查询帖子置顶列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="groupId",value="小组id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<PostsVo>> getTopPostsList(String groupId, Integer page, Integer limit){
        return  bizForumPostsBiz.getTopPostsList(groupId, page, limit);
    }




    /**
     * 查询帖子详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPostsInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询帖子详情---APP端", notes = "查询帖子详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<PostsInfo> getPostsInfo(String id){
        return bizForumPostsBiz.getPostsInfo(id);
    }


    /**
     * 判断当前用户是否被禁止
     * @return
     */
    @RequestMapping(value = "/selectUserIsForbid", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "判断当前用户是否被禁止发帖---APP端", notes = "判断当前用户是否被禁止发帖---APP端",httpMethod = "GET")
    public ObjectRestResponse selectUserIsForbid(){
        return bizForumPostsBiz.selectUserIsForbid();
    }


    /**
     * 查询帖子评论列表
     * @return
     */
    @RequestMapping(value = "/getCommentList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询帖子评论列表---APP端", notes = "查询帖子评论列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<CommentTree>> getCommentList(String id, Integer page, Integer limit){
        return  bizForumPostsBiz.getCommentList(id, page, limit);
    }


    /**
     * 保存帖子
     * @param param
     * @return
     */
    @RequestMapping(value = "/savePosts" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存帖子---APP端", notes = "保存帖子---APP端",httpMethod = "POST")
    public ObjectRestResponse savePosts(@RequestBody @ApiParam SavePostsParam param){
        return  bizForumPostsBiz.savePosts(param);
    }




    /**
     * 帖子操作置顶,隐藏,精华
     * @param param
     * @return
     */
    @RequestMapping(value = "/updatePostsStatus" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "帖子操作置顶,隐藏,精华---APP端", notes = "帖子操作置顶,隐藏,精华---APP端",httpMethod = "POST")
    public ObjectRestResponse updatePostsStatus(@RequestBody @ApiParam UpdatePostsStatus param){
        return  bizForumPostsBiz.updatePostsStatus(param);
    }



    /**
     * 保存帖子评论
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveComment" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存帖子评论---APP端", notes = "保存帖子评论---APP端",httpMethod = "POST")
    public ObjectRestResponse saveComment(@RequestBody @ApiParam SaveCommentParam param){
        return  bizForumPostsBiz.saveComment(param);
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
        return  bizForumPostsBiz.saveLikeOperating(param);
    }

    /**
     * 隐藏评论操作
     * @param commenId
     * @return
     */
    @RequestMapping(value = "/hideComment" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "隐藏评论操作---APP端", notes = "隐藏评论操作---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="commenId",value="评论id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse hideComment(String commenId){
        return  bizForumPostsBiz.hideComment(commenId);
    }



    /**
     * 分享帖子详情
     * @param id
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/sharePostsInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享帖子详情---APP端", notes = "分享帖子详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<PostsInfo> sharePostsInfo(String id){
        return bizForumPostsBiz.sharePostsInfo(id);
    }


    /**
     * 分享帖子评论列表
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/shareCommentList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享帖子评论列表---APP端", notes = "分享帖子评论列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<CommentTree>> shareCommentList(String id, Integer page, Integer limit){
        return  bizForumPostsBiz.shareCommentList(id, page, limit);
    }



    /**
     * 查询我的帖子
     * @return
     */
    @RequestMapping(value = "/getUserPostsList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询我的帖子---APP端", notes = "查询我的帖子---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<FamilyPostsVo>> getUserPostsList(Integer page, Integer limit){
        return  bizForumPostsBiz.getUserPostsList(page, limit);
    }


    /**
     * 删帖
     * @param param
     * @return
     */
    @RequestMapping(value = "/deletePosts" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删帖---APP端", notes = "删帖---APP端",httpMethod = "POST")
    public ObjectRestResponse deletePosts(@RequestBody @ApiParam DeletePostsParam param) {
        return bizForumPostsBiz.deletePostsById(param);
    }

}