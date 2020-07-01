package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizDecoreteSuperviseBiz;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.in.DecoreteParams;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.in.DecoreteStatus;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out.DecoreteDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out.DecoreteListVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 装修监理表
 *
 * @Date 2019-04-01 15:47:05
 */
@RestController
@RequestMapping("web/decoreteSupervise")
@CheckClientToken
@CheckUserToken
@Api(tags = "装修监理")
public class BizDecoreteSuperviseController{

    @Autowired
    private BizDecoreteSuperviseBiz bizDecoreteSuperviseBiz;

    @RequestMapping(value = "/getDecoreteSuperviseList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询装修监理列表---PC端", notes = "查询装修监理列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="status",value="状态查询(0-待发布，1-未发布，2-已发布,3-全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目ID",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<DecoreteListVo> getDecoreteSuperviseList(String projectId, String status, Integer page, Integer limit) {
        List<DecoreteListVo> decoreteSupervise = bizDecoreteSuperviseBiz.getDecoreteSupervise(projectId, status, page, limit);
        int total = bizDecoreteSuperviseBiz.getDecoreteSuperviseCount(projectId,status);
        return new TableResultResponse<DecoreteListVo>(total, decoreteSupervise);
    }

    @RequestMapping(value = "/getDecoreteSuperviseInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询装修监理详情---PC端", notes = "查询装修监理详情---PC端",httpMethod = "GET")
    public TableResultResponse<DecoreteDetailVo> getDecoreteSuperviseInfo(@PathVariable String id) {
        List<DecoreteDetailVo> decoreteDetail = bizDecoreteSuperviseBiz.getDecoreteDetail(id);
        return new TableResultResponse<DecoreteDetailVo>(decoreteDetail.size(),decoreteDetail);
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改装修监理状态---PC端", notes = "修改装修监理状态---PC端", httpMethod = "POST")
    public ObjectRestResponse updateStatus(@RequestBody @ApiParam DecoreteStatus param) {
        return bizDecoreteSuperviseBiz.updateStatus(param);
    }

    @RequestMapping(value = "/saveDecoreteSuperviseInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存装修监理信息---PC端", notes = "保存装修监理信息---PC端",httpMethod = "POST")
    public ObjectRestResponse saveDecoreteSuperviseInfo(@RequestBody @ApiParam DecoreteParams param) {
        return bizDecoreteSuperviseBiz.saveDecoreteSupervise(param);
    }

    @RequestMapping(value = "/updateDecoreteSuperviseInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑装修监理信息---PC端", notes = "编辑装修监理信息---PC端",httpMethod = "POST")
    public ObjectRestResponse updateDecoreteSuperviseInfo(@RequestBody @ApiParam DecoreteParams param) {
        return bizDecoreteSuperviseBiz.updateDecoreteSupervise(param);
    }

    @RequestMapping(value = "/deleteDecoreteSupervise/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除装修监理---PC端", notes = "删除装修监理---PC端",httpMethod = "GET")
    public ObjectRestResponse deleteDecoreteSupervise(@PathVariable String id) {
        return bizDecoreteSuperviseBiz.deleteDecoreteSupervise(id);
    }
}