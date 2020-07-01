package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizProductGoodtoVisitBiz;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 好物探访表
 *
 * @author zxl
 * @Date 2018-12-13 09:56:57
 */
@RestController
@RequestMapping("bizProductGoodtoVisit")
@CheckClientToken
@CheckUserToken
@Api(tags = "好物探访")
public class BizProductGoodtoVisitController {

    @Autowired
    private BizProductGoodtoVisitBiz bizProductGoodtoVisitBiz;

    /**
     * 查询好物探仿列表
     * @return
     */
    @RequestMapping(value = "/getGoodVisitListApp",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询好物探访列表---App端", notes = "查询好物探访列表---App端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="社区ID",dataType="String",paramType = "query",example="124415446156ada"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="10")
    })
    public ObjectRestResponse getGoodVisitList(String projectId,Integer page, Integer limit) {
        return bizProductGoodtoVisitBiz.getGoodVisitList(projectId,page,limit);
    }


    @RequestMapping(value = "/getGoodVisitDetailsApp",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询好物探访详情---App端", notes = "查询好物探访详情---App端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="好物探访id",dataType="String",paramType = "query",example="4")
    })
    public ObjectRestResponse getGoodVisitDetails(String id) {
        return bizProductGoodtoVisitBiz.getGoodVisitDetails(id);
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "/shareGoodVisitApp",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享好物探访---App端", notes = "分享好物探访---App端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="好物探访id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse shareGoodVisit(String id) {
        return bizProductGoodtoVisitBiz.getGoodVisitDetails(id);
    }

}