package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizFamilyAdvertisingBiz;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.ActivityVo;
import com.github.wxiaoqi.security.jinmao.vo.communityTopic.out.CommunityTopicVo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.in.SaveFamilyAdParam;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.FamilyAdInfo;
import com.github.wxiaoqi.security.jinmao.vo.familyad.out.FamilyAdVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 家里人广告位
 *
 * @author huangxl
 * @Date 2019-08-12 14:50:41
 */
@RestController
@RequestMapping("web/bizFamilyAdvertising")
@CheckClientToken
@CheckUserToken
@Api(tags = "家里人广告位")
public class BizFamilyAdvertisingController {

    @Autowired
    private BizFamilyAdvertisingBiz bizFamilyAdvertisingBiz;


    /**
     * 查询家里人广告位列表
     * @return
     */
    @RequestMapping(value = "/getFamilyAdList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询家里人广告位列表---PC端", notes = "查询家里人广告位列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据标题查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<FamilyAdVo> getFamilyAdList(String projectId, String searchVal, Integer page, Integer limit){
        List<FamilyAdVo> familyAdList =  bizFamilyAdvertisingBiz.getFamilyAdList(projectId, searchVal, page, limit);
        int total = bizFamilyAdvertisingBiz.selectFamilyAdCount(projectId, searchVal);
        return new TableResultResponse<FamilyAdVo>(total, familyAdList);
    }


    /**
     * 查询邻里活动列表
     * @return
     */
    @RequestMapping(value = "/getActivityList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询邻里活动列表---PC端", notes = "查询邻里活动列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据标题查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ActivityVo> getActivityList(String projectId,String startTime, String endTime, String searchVal, Integer page, Integer limit){
        List<ActivityVo> activityList =  bizFamilyAdvertisingBiz.getActivityList(projectId,startTime,endTime,searchVal, page, limit);
        int total = bizFamilyAdvertisingBiz.selectActivityCount(projectId, startTime,endTime, searchVal);
        return new TableResultResponse<ActivityVo>(total, activityList);
    }


    /**
     * 查询社区话题列表
     * @return
     */
    @RequestMapping(value = "/getCommunityTopicList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询社区话题列表---PC端", notes = "查询社区话题列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id(多个项目用逗号隔开)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="isTop",value="置顶状态(0-未置顶，1-已置顶,null=全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startTime",value="开始时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endTime",value="结束时间",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据话题标题/发帖人模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<CommunityTopicVo> getCommunityTopicList(String projectId,String isTop, String startTime, String endTime,
                                                                       String searchVal, Integer page, Integer limit){
        List<CommunityTopicVo> topicTagList =  bizFamilyAdvertisingBiz.getCommunityTopicList(projectId,isTop, startTime, endTime, searchVal, page, limit);
        int total = bizFamilyAdvertisingBiz.selectCommunityTopicCount(projectId, isTop, startTime, endTime, searchVal);
        return new TableResultResponse<CommunityTopicVo>(total, topicTagList);
    }




    /**
     * 保存家里人广告位
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveFamilyAd",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存家里人广告位---PC端", notes = "保存家里人广告位---PC端",httpMethod = "POST")
    public ObjectRestResponse saveFamilyAd(@RequestBody @ApiParam SaveFamilyAdParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isAnyEmpty(params.getTitle(),params.getViewSort())){
            msg.setStatus(1002);
            msg.setMessage("标题/排序不能为空!");
            return msg;
        }
        if(params.getProjectVo() == null && params.getProjectVo().size() == 0){
            msg.setStatus(1003);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        if(params.getAdImageList() == null && params.getAdImageList().size() == 0){
            msg.setStatus(1003);
            msg.setMessage("广告图片不能为空!");
            return msg;
        }
        String busn = "1";
        String busw = "2";
        if(busn.equals(params.getSkipBus())){
            if(StringUtils.isAnyEmpty(params.getBusClassify())){
                msg.setStatus(1004);
                msg.setMessage("业务类型不能为空!");
                return msg;
            }
        }else if(busw.equals(params.getSkipBus())){
            if(StringUtils.isEmpty(params.getSkipUrl())){
                msg.setStatus(1003);
                msg.setMessage("跳转地址不能为空!");
                return msg;
            }
        }
        return bizFamilyAdvertisingBiz.saveFamilyAd(params);
    }


    /**
     * 删除家里人广告位
     * @param id
     * @return
     */
    @RequestMapping(value = "/delFamilyAdTag/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除家里人广告位---PC端", notes = "删除家里人广告位---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delFamilyAdTag(@PathVariable String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        return bizFamilyAdvertisingBiz.delFamilyAdTag(id);
    }


    /**
     * 查询家里人广告位详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getFamilyAdInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询家里人广告位详情---PC端", notes = "查询家里人广告位详情---PC端",httpMethod = "GET")
    public ObjectRestResponse<FamilyAdInfo> getFamilyAdInfo(@PathVariable String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        return bizFamilyAdvertisingBiz.getFamilyAdInfo(id);
    }


    /**
     * 编辑家里人广告
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateFamilyAd",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑家里人广告---PC端", notes = "编辑家里人广告---PC端",httpMethod = "POST")
    public ObjectRestResponse updateFamilyAd(@RequestBody @ApiParam SaveFamilyAdParam params){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(params == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isAnyEmpty(params.getTitle(),params.getViewSort())){
            msg.setStatus(1002);
            msg.setMessage("标题/排序不能为空!");
            return msg;
        }
        if(params.getProjectVo() == null && params.getProjectVo().size() == 0){
            msg.setStatus(1003);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        if(params.getAdImageList() == null && params.getAdImageList().size() == 0){
            msg.setStatus(1003);
            msg.setMessage("广告图片不能为空!");
            return msg;
        }
        String busn = "1";
        String busw = "2";
        if(busn.equals(params.getSkipBus())){
            if(StringUtils.isAnyEmpty(params.getBusClassify())){
                msg.setStatus(1004);
                msg.setMessage("业务类型不能为空!");
                return msg;
            }
        }else if(busw.equals(params.getSkipBus())){
            if(StringUtils.isEmpty(params.getSkipUrl())){
                msg.setStatus(1003);
                msg.setMessage("跳转地址不能为空!");
                return msg;
            }
        }
        return bizFamilyAdvertisingBiz.updateFamilyAd(params);
    }








}