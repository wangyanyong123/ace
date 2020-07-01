package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizShoppingCartBiz;
import com.github.wxiaoqi.security.app.vo.shopping.in.QueryConfirmOrderInfoIn;
import com.github.wxiaoqi.security.app.vo.shopping.in.SaveShoppingCart;
import com.github.wxiaoqi.security.app.vo.shopping.in.UpdateShoppingCart;
import com.github.wxiaoqi.security.app.vo.shopping.out.ConfirmOrderInfo;
import com.github.wxiaoqi.security.app.vo.shopping.out.ShoppingVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 商品购物车
 *
 * @author zxl
 * @Date 2018-12-12 17:43:04
 */
@RestController
@RequestMapping("bizShoppingCart")
@CheckClientToken
@CheckUserToken
@Api(tags = "APP购物车管理")
public class BizShoppingCartController{

    @Autowired
    private BizShoppingCartBiz bizShoppingCartBiz;


    /**
     * 保存购物车记录
     * @param
     * @return
     */
    @RequestMapping(value = "/saveShoppingCart" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存购物车记录---APP端", notes = "保存购物车记录---APP端",httpMethod = "POST")
    public ObjectRestResponse saveShoppingCart(SaveShoppingCart param, HttpServletRequest request){
        param.setAppType(RequestHeaderUtil.getPlatformIntValue(request));
        return  bizShoppingCartBiz.saveShoppingCart(param);
    }


    /**
     * 编辑购物车购买数量
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateShoppingCart" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑购物车购买数量---APP端", notes = "编辑购物车购买数量---APP端",httpMethod = "POST")
    public ObjectRestResponse saveShoppingCart(UpdateShoppingCart param){
        return  bizShoppingCartBiz.updateShoppingCart(param);
    }


    /**
     * 删除购物车记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/delShoppingCart", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除购物车记录---APP端", notes = "删除购物车记录---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="id",value="购物车id,多个用逗号隔开",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse delShoppingCart(String id){
        return bizShoppingCartBiz.delShoppingCart(id);
    }


    /**
     * 查询购物车信息列表
     * @return
     */
    @RequestMapping(value = "/getShoppingCartList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询购物车信息列表---APP端", notes = "查询购物车信息列表---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse<ShoppingVo> getShoppingCartList(String projectId, Integer page, Integer limit){
        return bizShoppingCartBiz.getShoppingCartList(projectId,page, limit);
    }


    /**
     * 统计购物车金额
     * @return
     */
    @RequestMapping(value = "/getShoppingStatisInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "统计购物车金额---APP端", notes = "统计购物车金额---APP端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="specIds",value="商品规格id,多个用,隔开",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="selectIsAll",value="是否全选(0;不是,1;全选)",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse getShoppingStatisInfo(String projectId,String specIds,String selectIsAll){
        return bizShoppingCartBiz.getShoppingStatisInfo(projectId,specIds, selectIsAll);
    }



    /**
     * 查询购物车数量
     * @return
     */
    @RequestMapping(value = "/getShoppingCartCount", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取订单确认页信息---APP端", notes = "查询购物车数量---APP端",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getShoppingCartCount(String projectId){
        return bizShoppingCartBiz.getShoppingCartCount(projectId);
    }

    @RequestMapping(value = "/getConfirmOrderInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取订单确认页信息", notes = "获取订单确认页信息",httpMethod = "POST")
    public ObjectRestResponse getConfirmOrderInfo(@RequestBody QueryConfirmOrderInfoIn queryConfirmOrderInfoIn){
        ConfirmOrderInfo confirmOrderInfo = bizShoppingCartBiz.getConfirmOrderInfo(queryConfirmOrderInfoIn);
        return ObjectRestResponse.ok(confirmOrderInfo);
    }



}