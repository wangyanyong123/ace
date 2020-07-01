package com.github.wxiaoqi.security.jinmao.controller.app;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizPropertyAnnouncementBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ann")
@CheckClientToken
@CheckUserToken
@Api(tags = "物业公告--App")
public class BizPropertyAnnouncementAppController {

    @Autowired
    private BizPropertyAnnouncementBiz bizPropertyAnnouncementBiz;

    /**
     * 查询物业公告列表
     * @return
     */
    @RequestMapping(value = "/getAppAnnouncementList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业公告列表---App端", notes = "查询物业公告列表---App端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse getAppAnnouncementList(String projectId, Integer page, Integer limit){
        return bizPropertyAnnouncementBiz.getAppAnnouncementList(projectId,page,limit);
    }


    /**
     * 查询物业公告详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAppAnnouncementInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询物业公告详情---App端", notes = "查询物业公告详情---App端",httpMethod = "GET")
    public ObjectRestResponse getAppAnnouncementInfo(@PathVariable String id){
        return bizPropertyAnnouncementBiz.getAppAnnouncementInfo(id);
    }



}