package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizVisitorSignlogsBiz;
import com.github.wxiaoqi.security.app.vo.visitor.in.VisitorSignVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 访客登记表
 *
 * @author zxl
 * @Date 2019-01-08 17:51:58
 */
@RestController
@RequestMapping("bizVisitorSignLogs")
@CheckClientToken
@CheckUserToken
@Api(tags = "访客登记")
public class BizVisitorSignlogsController {

    @Autowired
    private BizVisitorSignlogsBiz bizVisitorSignlogsBiz;

    @GetMapping("getVisitListApp")
    @ApiOperation(value = "获取访客记录列表", notes = "获取访客记录列表",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="社区id",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324")
    public ObjectRestResponse getVisitListApp(String projectId,Integer page,Integer limit) {
        return bizVisitorSignlogsBiz.getVisitList(projectId,page,limit);
    }

    @IgnoreUserToken
    @IgnoreClientToken
    @GetMapping("shareVisitInfoApp")
    @ApiOperation(value = "分享访客记录详情", notes = "分享访客记录详情",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="访客登记id",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324")
    public ObjectRestResponse shareVisitInfoApp(String id) {
        return bizVisitorSignlogsBiz.getVisitLogInfo(id);
    }

    @GetMapping("getVisitInfoApp")
    @ApiOperation(value = "获取访客记录详情", notes = "获取访客记录详情",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="访客登记id",dataType="String",required = true ,paramType = "query",example="4sdadgsfdgs2324")
    public ObjectRestResponse getVisitInfoApp(String id) {
        return bizVisitorSignlogsBiz.getVisitLogInfo(id);
    }


    @PostMapping("saveVisitInfoApp")
    @ApiOperation(value = "保存访客登记", notes = "保存访客登记", httpMethod = "POST")
    public ObjectRestResponse saveVisitInfoApp(@RequestBody @ApiParam VisitorSignVo param) {
        return bizVisitorSignlogsBiz.saveVisitSign(param);
    }

    @GetMapping("whetherVisitSign")
    @ApiOperation(value = "判断是否有访客功能", notes = "判断是否有访客功能",httpMethod = "GET")
    public ObjectRestResponse whetherVisitSign() {
        return bizVisitorSignlogsBiz.whetherVisitSign();
    }
}