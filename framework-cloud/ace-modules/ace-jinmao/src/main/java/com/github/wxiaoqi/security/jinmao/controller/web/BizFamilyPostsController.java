package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizFamilyPostsBiz;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.in.UpdateTopicStatus;
import com.github.wxiaoqi.security.jinmao.vo.familyPosts.FamilyPostsInfo;
import com.github.wxiaoqi.security.jinmao.vo.familyPosts.FamilyPostsVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("web/bizFamilyPosts")
@CheckClientToken
@CheckUserToken
@Api(tags = "家里人帖子管理")
public class BizFamilyPostsController {

    @Autowired
    private BizFamilyPostsBiz bizFamilyPostsBiz;


    /**
     * 查询家里人帖子列表
     * @return
     */
    @RequestMapping(value = "/getFamilyPostsList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询家里人帖子列表---PC端", notes = "查询家里人帖子列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="showType",value="显示状态(0=隐藏，1=显示,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="isTop",value="置顶状态(0-未置顶，1-已置顶,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据帖子内容/发帖人模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<FamilyPostsVo> getFamilyPostsList(String projectId, String showType, String isTop, String startTime, String endTime,
                                                                    String searchVal, Integer page, Integer limit){
        List<FamilyPostsVo> topicTagList =  bizFamilyPostsBiz.getFamilyPostsList(projectId, showType, isTop, startTime, endTime, searchVal, page, limit);
        int total = bizFamilyPostsBiz.selectFamilyPostsCount(projectId, showType, isTop, startTime, endTime, searchVal);
        return new TableResultResponse<FamilyPostsVo>(total, topicTagList);
    }


    /**
     * 查询家里人帖子详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getFamilyPostsInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询家里人帖子详情---PC端", notes = "查询家里人帖子详情---PC端",httpMethod = "GET")
    public ObjectRestResponse<FamilyPostsInfo> getFamilyPostsInfo(@PathVariable String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        return bizFamilyPostsBiz.getFamilyPostsInfo(id);
    }



    /**
     * 家里人帖子操作置顶,隐藏
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateFamilyPostsStatus" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "家里人帖子操作置顶,隐藏---PC端", notes = "家里人帖子操作置顶,隐藏---PC端",httpMethod = "POST")
    public ObjectRestResponse updateFamilyPostsStatus(@RequestBody @ApiParam UpdateTopicStatus param){
        return  bizFamilyPostsBiz.updateFamilyPostsStatus(param);
    }












}