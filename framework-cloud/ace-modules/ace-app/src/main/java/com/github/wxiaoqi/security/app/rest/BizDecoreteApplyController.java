package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizDecoreteApplyBiz;
import com.github.wxiaoqi.security.app.biz.BizUserHouseBiz;
import com.github.wxiaoqi.security.app.vo.decorete.in.DecoreteApplyParam;
import com.github.wxiaoqi.security.app.vo.decorete.out.DecoreteVo;
import com.github.wxiaoqi.security.app.vo.decorete.out.MyDecoreteVo;
import com.github.wxiaoqi.security.app.vo.decorete.out.WoDetail;
import com.github.wxiaoqi.security.app.vo.userhouse.out.HouseInfoVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 装修监理申请表
 *
 * @author huangxl
 * @Date 2019-04-01 15:20:10
 */
@RestController
@RequestMapping("bizDecoreteApply")
@CheckClientToken
@CheckUserToken
@Api(tags = "App装修监理")
public class BizDecoreteApplyController {

    @Autowired
    private BizDecoreteApplyBiz bizDecoreteApplyBiz;
    @Autowired
    private BizUserHouseBiz bizUserHouseBiz;


    /**
     * 查询当前项目下的装修监理服务
     * @return
     */
    @RequestMapping(value = "/getDecoreteList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前项目下的装修监理服务---APP端", notes = "查询当前项目下的装修监理服务---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<DecoreteVo> getDecoreteList(String projectId){
        return bizDecoreteApplyBiz.getDecoreteList(projectId);
    }




    /**
     * 查询装修监理服务的详情
     * @return
     */
    @RequestMapping(value = "/getDecoreteInfo" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询装修监理服务的详情---APP端", notes = "查询装修监理服务的详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="装修监理id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<DecoreteVo> getDecoreteInfo(String id){
        return bizDecoreteApplyBiz.getDecoreteInfo(id);
    }




    @RequestMapping(value = "/saveDecoreteApply" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提交装修监理服务---App端", notes = "提交装修监理服务---App端",httpMethod = "POST")
    public ObjectRestResponse saveDecoreteApply(@RequestBody @ApiParam DecoreteApplyParam param) {
        return bizDecoreteApplyBiz.saveDecoreteApply(param);
    }


    /**
     * 查询当前项目下的用户预约装修监理记录
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getUserDecoreteList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前项目下的用户预约装修监理记录---APP端", notes = "查询当前项目下的用户预约装修监理记录---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<MyDecoreteVo>> getUserDecoreteList(String projectId, Integer page, Integer limit){
        return bizDecoreteApplyBiz.getUserDecoreteList(projectId, page, limit);
    }


    /**
     * 查看用户预约详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getMyDecoreteInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看用户预约详情---APP端", notes = "查看用户预约详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="工单id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<WoDetail> getMyDecoreteInfo(String id){
        return bizDecoreteApplyBiz.getMyDecoreteInfo(id);
    }


    /**
     * 查看用户当前项目下的所有房屋
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getCurrentUserAllHouse", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看用户当前项目下的所有房屋---APP端", notes = "查看用户当前项目下的所有房屋---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<List<HouseInfoVo>> getCurrentUserAllHouse(String projectId) {
        return bizUserHouseBiz.getCurrentUserAllHouse(projectId);
    }



}