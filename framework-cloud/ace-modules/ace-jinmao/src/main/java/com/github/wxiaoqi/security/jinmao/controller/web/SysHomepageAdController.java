package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.SysHomepageAdBiz;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.in.AdHomePageParam;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.in.adOpratingParam;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdAppHomePageList;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdHomePageInfo;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdHomePageVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app广告位表
 *
 * @author huangxl
 * @Date 2019-05-27 09:55:49
 */
@RestController
@RequestMapping("web/sysHomepageAd")
@CheckClientToken
@CheckUserToken
@Api(tags = "app广告位")
public class SysHomepageAdController {


    @Autowired
    private SysHomepageAdBiz sysHomepageAdBiz;


    /**
     * 查询app广告业列表
     * @return
     */
    @RequestMapping(value = "/getAdHomePageListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询app广告业列表---PC端", notes = "查询app广告业列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="isPublish",value="状态查询(0:全部1:待发布，2:已发布,3:已撤回)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据活动标题、小组名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<AdHomePageVo> getAdHomePageListPc(String isPublish,String searchVal,String projectId,Integer page, Integer limit){
        List<AdHomePageVo> adHomePageList =  sysHomepageAdBiz.getAdHomePageList(isPublish, searchVal, projectId, page, limit);
        int total = sysHomepageAdBiz.selectAdHomePageCount(isPublish, searchVal, projectId);
        return new TableResultResponse<AdHomePageVo>(total, adHomePageList);
    }


    /**
     * 添加app广告
     * @param params
     * @return
     */
    @RequestMapping(value = "/addAdHomePagePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加app广告---PC端", notes = "添加app广告---PC端",httpMethod = "POST")
    public ObjectRestResponse addAdHomePagePc(@RequestBody @ApiParam AdHomePageParam params){
        return sysHomepageAdBiz.addAdHomePage(params);
    }



    /**
     * 查询app广告业详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAdHomePageInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询app广告业详情---PC端", notes = "查询app广告业详情---PC端",httpMethod = "GET")
    public TableResultResponse<AdHomePageInfo> getAdHomePageInfoPc(@PathVariable String id){
        List<AdHomePageInfo> info = sysHomepageAdBiz.getAdHomePageInfo(id);
        return new TableResultResponse<AdHomePageInfo>(info.size(),info);
    }


    /**
     * 编辑app广告
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateAdHomePagePc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑app广告---PC端", notes = "编辑app广告---PC端",httpMethod = "POST")
    public ObjectRestResponse updateAdHomePagePc(@RequestBody @ApiParam AdHomePageParam params){
        return sysHomepageAdBiz.updateAdHomePage(params);
    }


    /**
     * 删除,发布,撤回操作
     * @param param
     * @return
     */
    @RequestMapping(value = "/opratingAdHomePagePc", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除,发布,撤回操作---PC端", notes = "删除,发布,撤回操作---PC端",httpMethod = "POST")
    public ObjectRestResponse opratingAdHomePagePc(@RequestBody @ApiParam adOpratingParam param){
        return sysHomepageAdBiz.opratingAdHomePage(param);
    }


    /**
     * 获取app广告
     * @return
     */
    @RequestMapping(value = "/getAdAppHomePageList",method = RequestMethod.GET)
    @ResponseBody
    @IgnoreUserToken
    @IgnoreClientToken
    @ApiOperation(value = "获取app广告---app端", notes = "获取app广告---app端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<AdAppHomePageList> getAdAppHomePageList(){
        return sysHomepageAdBiz.getAdAppHomePageList();
    }


}