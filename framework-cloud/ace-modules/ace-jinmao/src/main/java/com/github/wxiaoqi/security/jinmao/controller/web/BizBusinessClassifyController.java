package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizBusinessClassifyBiz;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import com.github.wxiaoqi.security.jinmao.vo.Classify.InputParam.EditFirstClassifyParam;
import com.github.wxiaoqi.security.jinmao.vo.Classify.InputParam.SaveClassifyParam;
import com.github.wxiaoqi.security.jinmao.vo.Classify.InputParam.StatusParam;
import com.github.wxiaoqi.security.jinmao.vo.Classify.OutParam.ResultClassifyVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务商品分类表
 *
 * @author zxl
 * @Date 2018-12-03 10:32:32
 */
@RestController
@RequestMapping("web/bizBusinessClassify")
@CheckClientToken
@CheckUserToken
@Api(tags = "商品分类管理")
public class BizBusinessClassifyController{

    @Autowired
    private BizBusinessClassifyBiz bizBusinessClassifyBiz;


    /**
     * 查询商品分类列表
     * @return
     */
    @RequestMapping(value = "/getBusinessListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品分类列表---PC端", notes = "查询商品分类列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="busId",value="业务id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据编码,名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<ResultClassifyVo> getBusinessListPc(String busId, String searchVal, Integer page, Integer limit){
        List<ResultClassifyVo> classifyVoList = bizBusinessClassifyBiz.getClassifyList(busId, searchVal, page, limit);
        int total = bizBusinessClassifyBiz.selectClassifyCount(busId, searchVal);
        return new TableResultResponse<ResultClassifyVo>(total, classifyVoList);
    }


    /**
     * 查询所有的订单类型的业务列表
     * @return
     */
    @RequestMapping(value = "/getBusinessListByType",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所有的订单类型的业务列表---PC端", notes = "查询所有的订单类型的业务列表---PC端",httpMethod = "GET")
    public TableResultResponse<ResultBusinessVo> getBusinessListByType(){
        List<ResultBusinessVo> businessVoList = bizBusinessClassifyBiz.getBusinessListByType();
        return new TableResultResponse<ResultBusinessVo>(businessVoList.size(), businessVoList);
    }


    /**
     * 删除商品分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/delClassifyInfoPc/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除商品分类---PC端", notes = "删除商品分类---PC端",httpMethod = "DELETE")
    public ObjectRestResponse delClassifyInfoPc(@PathVariable String id){
        return bizBusinessClassifyBiz.delClassifyInfo(id);
    }


    /**
     * 保存商品分类
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveClassifyPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存商品分类---PC端", notes = "保存商品分类---PC端",httpMethod = "POST")
    public ObjectRestResponse saveClassifyPc(@RequestBody @ApiParam SaveClassifyParam params){
        return bizBusinessClassifyBiz.saveClassify(params);
    }



    /**
     * 查询商品分类详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getClassifyInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商品分类详情---PC端", notes = "查询商品分类详情---PC端",httpMethod = "GET")
    public TableResultResponse<ResultClassifyVo> getClassifyInfoPc(@PathVariable String id){
        List<ResultClassifyVo> classifyInfo = bizBusinessClassifyBiz.getClassifyInfo(id);
        return new TableResultResponse<ResultClassifyVo>(classifyInfo.size(),classifyInfo);
    }

    /**
     * 编辑商品分类
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateClassifyPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑商品分类---PC端", notes = "编辑商品分类---PC端",httpMethod = "POST")
    public ObjectRestResponse updateClassifyPc(@RequestBody @ApiParam SaveClassifyParam params){
        return bizBusinessClassifyBiz.updateClassify(params);
    }


    /**
     * 修改禁用与启用状态
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateBusStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改禁用与启用状态---PC端", notes = "修改禁用与启用状态---PC端",httpMethod = "POST")
    public ObjectRestResponse updateBusStatusPc(@RequestBody @ApiParam StatusParam param){
        String id = param.getId();
        String busStatus = param.getBusStatus();
        return bizBusinessClassifyBiz.updateBusStatus(id, busStatus);
    }

}