package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizForumPostsBiz;
import com.github.wxiaoqi.security.jinmao.vo.fosts.in.UpdateCommentStatus;
import com.github.wxiaoqi.security.jinmao.vo.fosts.in.UpdatePostsStatus;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.CommentVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.FostsVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.GroupInfo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.PostsInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小组帖子表
 *
 * @author huangxl
 * @Date 2019-01-28 15:06:24
 */
@RestController
@RequestMapping("web/bizForumPosts")
@CheckClientToken
@CheckUserToken
@Api(tags = "帖子管理")
public class BizForumPostsController {

    @Autowired
    private BizForumPostsBiz bizForumPostsBiz;


    /**
     * 查询帖子列表
     * @param projectId
     * @param groupId
     * @param startTime
     * @param endTime
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getPostsListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询帖子列表---PC端", notes = "查询帖子列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="isTop",value="置顶状态(0-未置顶，1-已置顶,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="groupId",value="小组id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间(格式yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间(格式yyyy-MM-dd)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据帖子标题,内容,发帖人模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<FostsVo> getPostsListPc(String projectId,String isTop, String groupId, String startTime, String endTime,
                                                                     String searchVal, Integer page, Integer limit){
        List<FostsVo> postsList = bizForumPostsBiz.getPostsList(projectId,isTop, groupId, startTime, endTime, searchVal, page, limit);
        int total = bizForumPostsBiz.selectPostsCount(projectId,isTop, groupId, startTime, endTime, searchVal);
        return new TableResultResponse<FostsVo>(total, postsList);
    }



    /**
     * 帖子操作置顶,隐藏,精华
     * @param param
     * @return
     */
    @RequestMapping(value = "/updatePostsStatusPc" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "帖子操作置顶,隐藏,精华---PC端", notes = "帖子操作置顶,隐藏,精华---PC端",httpMethod = "POST")
    public ObjectRestResponse updatePostsStatusPc(@RequestBody @ApiParam UpdatePostsStatus param){
        return  bizForumPostsBiz.updatePostsStatus(param);
    }


    /**
     * 查询帖子详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPostsInfoById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询帖子详情---PC端", notes = "查询帖子详情---PC端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4")
    public TableResultResponse<PostsInfo> getPostsInfoById(@PathVariable String id){
        List<PostsInfo> postsInfo = bizForumPostsBiz.getPostsInfoById(id);
        return new TableResultResponse<PostsInfo>(postsInfo.size(), postsInfo);
    }


    /**
     * 根据项目id查询所关联的小组
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getGroupInfoPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据项目id查询所关联的小组---PC端", notes = "根据项目id查询所关联的小组---PC端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public TableResultResponse<GroupInfo> getGroupInfoPc(String projectId){
        List<GroupInfo> postsList = bizForumPostsBiz.getGroupInfo(projectId);
        return new TableResultResponse<GroupInfo>(postsList.size(), postsList);
    }


    /**
     * 查询评论列表
     * @return
     */
    @RequestMapping(value = "/getCommentListPc" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询评论列表---PC端", notes = "查询评论列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="帖子id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CommentVo> getCommentListPc(String id, Integer page, Integer limit){
        List<CommentVo> commentList = bizForumPostsBiz.getCommentList(id, page, limit);
        int total = bizForumPostsBiz.selectCommentCount(id);
        return new TableResultResponse<CommentVo>(total, commentList);
    }



    /**
     * 评论操作显示,隐藏
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateCommentStatusPc" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "评论操作显示,隐藏---PC端", notes = "评论操作显示,隐藏---PC端",httpMethod = "POST")
    public ObjectRestResponse updateCommentStatusPc(@RequestBody @ApiParam UpdateCommentStatus param){
        return  bizForumPostsBiz.updateCommentStatus(param);
    }



}