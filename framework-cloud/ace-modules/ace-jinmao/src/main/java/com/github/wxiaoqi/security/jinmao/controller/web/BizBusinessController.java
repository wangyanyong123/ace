package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizBusinessBiz;
import com.github.wxiaoqi.security.jinmao.vo.Business.InputParam.SaveBusinessParam;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务表
 *
 * @author zxl
 * @Date 2018-12-03 10:32:32
 */
@RestController
@RequestMapping("web/bizBusiness")
@CheckClientToken
@CheckUserToken
@Api(tags = "业务管理")
public class BizBusinessController{

    @Autowired
    private BizBusinessBiz bizBusinessBiz;


    /**
     * 查询业务列表
     * @return
     */
    @RequestMapping(value = "/getBusinessListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询业务列表---PC端", notes = "查询业务列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="searchVal",value="根据编码,名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultBusinessVo> getBusinessListPc(String searchVal, Integer page, Integer limit){
        List<ResultBusinessVo> businessVoList = bizBusinessBiz.getBusinessList(searchVal, page, limit);
        int total = bizBusinessBiz.selectBusinessCount(searchVal);
        return new TableResultResponse<ResultBusinessVo>(total, businessVoList);
    }


    /**
     * 保存业务
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBusinessPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存业务---PC端", notes = "保存业务---PC端",httpMethod = "POST")
    public ObjectRestResponse saveBusinessPc(@RequestBody @ApiParam SaveBusinessParam params){
        return bizBusinessBiz.saveBusiness(params);
    }


    /**
     * 删除业务
     * @param id
     * @return
     */
    @RequestMapping(value = "/delBusinessInfoPc/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除业务---PC端", notes = "删除业务---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delBusinessInfoPc(@PathVariable String id){
        return bizBusinessBiz.delBusinessInfo(id);
    }


    /**
     * 查询业务详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getBusinessInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询业务详情---PC端", notes = "查询业务详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultBusinessInfoVo> getBusinessInfoPc(@PathVariable String id){
        List<ResultBusinessInfoVo> businessInfoVos = bizBusinessBiz.getBusinessInfo(id);
        return new TableResultResponse<ResultBusinessInfoVo>(businessInfoVos.size(),businessInfoVos);
    }


    /**
     * 编辑业务
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateBusinessPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑业务---PC端", notes = "编辑业务---PC端",httpMethod = "POST")
    public ObjectRestResponse updateBusinessPc(@RequestBody @ApiParam SaveBusinessParam params){
        return bizBusinessBiz.updateBusiness(params);
    }

}