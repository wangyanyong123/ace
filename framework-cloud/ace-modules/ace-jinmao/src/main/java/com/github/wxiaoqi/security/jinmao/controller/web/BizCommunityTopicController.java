package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizCommunityTopicBiz;
import com.github.wxiaoqi.security.jinmao.biz.BizForumPostsBiz;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.CommunityTopicParam;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicInfo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.jinmao.vo.fosts.in.UpdateCommentStatus;
import com.github.wxiaoqi.security.jinmao.vo.fosts.out.CommentVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("web/bizCommunityTopic")
@CheckClientToken
@CheckUserToken
@Api(tags = "社区话题")
public class BizCommunityTopicController {

     @Autowired
     private BizCommunityTopicBiz bizCommunityTopicBiz;
     @Autowired
     private BizForumPostsBiz bizForumPostsBiz;



    /**
     * 查询社区话题列表
     * @return
     */
    @RequestMapping(value = "/getCommunityTopicList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询社区话题列表---PC端", notes = "查询社区话题列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="showType",value="显示状态(0=隐藏，1=显示,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="isTop",value="置顶状态(0-未置顶，1-已置顶,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据话题标题/发帖人模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CommunityTopicVo> getCommunityTopicList(String projectId,String showType,String isTop,String startTime,String endTime,
                                                                 String searchVal,Integer page, Integer limit){
        List<CommunityTopicVo> topicTagList =  bizCommunityTopicBiz.getCommunityTopicList(projectId, showType, isTop, startTime, endTime, searchVal, page, limit);
        int total = bizCommunityTopicBiz.selectCommunityTopicCount(projectId, showType, isTop, startTime, endTime, searchVal);
        return new TableResultResponse<CommunityTopicVo>(total, topicTagList);
    }





    /**
     * 保存社区话题
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveCommunityTopic",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存社区话题---PC端", notes = "保存社区话题---PC端",httpMethod = "POST")
    public ObjectRestResponse saveCommunityTopic(@RequestBody @ApiParam CommunityTopicParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(params.getTitle())){
            msg.setStatus(1002);
            msg.setMessage("话题标题不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(params.getContent())){
            msg.setStatus(1002);
            msg.setMessage("话题内容不能为空");
            return msg;
        }
        if(params.getProjectVo() == null || params.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        return bizCommunityTopicBiz.saveCommunityTopic(params);
    }

    /**
     * 查询社区话题详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCommunityTopicInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询社区话题详情---PC端", notes = "查询社区话题详情---PC端",httpMethod = "GET")
    public ObjectRestResponse<CommunityTopicInfo> getCommunityTopicInfo(@PathVariable String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        return bizCommunityTopicBiz.getCommunityTopicInfo(id);
    }


    /**
     * 编辑社区话题
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateCommunityTopic",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑社区话题---PC端", notes = "编辑社区话题---PC端",httpMethod = "POST")
    public ObjectRestResponse updateCommunityTopic(@RequestBody @ApiParam CommunityTopicParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(params.getTitle())){
            msg.setStatus(1002);
            msg.setMessage("话题标题不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(params.getContent())){
            msg.setStatus(1002);
            msg.setMessage("话题内容不能为空");
            return msg;
        }
        if(params.getProjectVo() == null || params.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        return bizCommunityTopicBiz.updateCommunityTopic(params);
    }


    /**
     * 社区话题操作置顶,隐藏
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateCommunityTopicStatus" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "社区话题操作置顶,隐藏---PC端", notes = "社区话题操作置顶,隐藏---PC端",httpMethod = "POST")
    public ObjectRestResponse updateCommunityTopicStatus(@RequestBody @ApiParam UpdateTopicStatus param){
        return  bizCommunityTopicBiz.updateCommunityTopicStatus(param);
    }




    /**
     * 查询评论列表
     * @return
     */
    @RequestMapping(value = "/getCommentListPc" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询评论列表---PC端", notes = "查询评论列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="对象(帖子、活动，家里人帖子，议事厅话题，社区话题)id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CommentVo> getCommentListPc(String id, Integer page, Integer limit){
        List<CommentVo> commentList = bizCommunityTopicBiz.getCommentList(id, page, limit);
        int total = bizCommunityTopicBiz.selectCommentCount(id);
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