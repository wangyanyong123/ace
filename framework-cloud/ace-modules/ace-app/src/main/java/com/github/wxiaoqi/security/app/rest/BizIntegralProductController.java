package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizIntegralProductBiz;
import com.github.wxiaoqi.security.app.vo.integralproduct.CashVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.IntegralProductInfo;
import com.github.wxiaoqi.security.app.vo.integralproduct.IntegralProductVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.ScreenVo;
import com.github.wxiaoqi.security.app.vo.integralproduct.in.BuyIntegralProductParam;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分商品表
 *
 * @author huangxl
 * @Date 2019-08-28 10:04:24
 */
@RestController
@RequestMapping("bizIntegralProduct")
@CheckClientToken
@CheckUserToken
@Api(tags="APP积分商城")
public class BizIntegralProductController{

    @Autowired
    private BizIntegralProductBiz bizIntegralProductBiz;


    /**
     * 查询积分商品列表
     * @return
     */
    @RequestMapping(value = "/getIntegralProductList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分商品列表---APP端", notes = "查询积分商品列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="isRecommend",value="1-查询推荐积分商品,0-查询所有",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="startIntegral",value="起始积分值",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="endIntegral",value="截止积分值",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<IntegralProductVo>> getIntegralProductList(String isRecommend,String projectId,String startIntegral,String endIntegral,
                                                                      Integer page, Integer limit){
        return bizIntegralProductBiz.getIntegralProductList(isRecommend,projectId,startIntegral,endIntegral,page,limit);
    }



    /**
     * 查询兑换记录
     * @return
     */
    @RequestMapping(value = "/getCashList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询兑换记录---APP端", notes = "查询兑换记录---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<List<CashVo>> getCashList(String projectId,Integer page, Integer limit){
        return bizIntegralProductBiz.getCashList(projectId,page,limit);
    }

    /**
     * 查询积分筛选范围
     * @return
     */
    @RequestMapping(value = "/getScreenList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分筛选范围---APP端", notes = "查询积分筛选范围---APP端",httpMethod = "GET")
    public ObjectRestResponse<List<ScreenVo>> getScreenList(){
        return bizIntegralProductBiz.getScreenList();
    }


    /**
     * 查询当前用户积分
     * @return
     */
    @RequestMapping(value = "/getCurrentIntegral", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询当前用户积分---APP端", notes = "查询当前用户积分---APP端",httpMethod = "GET")
    public ObjectRestResponse getCurrentIntegral(){
        return bizIntegralProductBiz.getCurrentIntegral();
    }



    /**
     * 查询积分商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getIntegralProductInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询积分商品详情---APP端", notes = "查询积分商品详情---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="商品id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<IntegralProductInfo> getIntegralProductInfo(String id){
        return bizIntegralProductBiz.getIntegralProductInfo(id);
    }



    /**
     * 兑换商品
     * @param param
     * @return
     */
    @RequestMapping(value = "/cashIntegralProduct" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "兑换商品---APP端", notes = "兑换商品---APP端",httpMethod = "POST")
    public ObjectRestResponse cashIntegralProduct(@RequestBody @ApiParam BuyIntegralProductParam param){
        return  bizIntegralProductBiz.cashIntegralProduct(param);
    }



}