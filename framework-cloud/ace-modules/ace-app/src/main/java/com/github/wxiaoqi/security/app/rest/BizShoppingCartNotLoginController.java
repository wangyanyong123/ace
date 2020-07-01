package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.app.biz.BizShoppingCartNotLoginBiz;
import com.github.wxiaoqi.security.app.vo.shopping.in.SaveShoppingCartNotLogin;
import com.github.wxiaoqi.security.app.vo.shopping.in.UpdateShoppingCart;
import com.github.wxiaoqi.security.app.vo.shopping.in.UpdateShoppingCartNotLogin;
import com.github.wxiaoqi.security.app.vo.shopping.out.ShoppingVo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品购物车 -- 无需登录
 *
 * @author zxl
 * @Date 2018-12-12 17:43:04
 */
@IgnoreClientToken
@IgnoreUserToken
@RestController
@RequestMapping("bizShoppingCart/notLogin")
@Api(tags = "购物车管理 -- 无需登录")
public class BizShoppingCartNotLoginController {

    @Autowired
    private BizShoppingCartNotLoginBiz bizShoppingCartNotLoginBiz;


    /**
     * 保存购物车记录
     */
    @RequestMapping(value = "/saveShoppingCart" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存购物车记录", notes = "保存购物车记录",httpMethod = "POST")
    public ObjectRestResponse saveShoppingCart(SaveShoppingCartNotLogin param, HttpServletRequest request){
        param.setAppType(RequestHeaderUtil.getPlatformIntValue(request));
        return  bizShoppingCartNotLoginBiz.saveShoppingCart(param);
    }

    /**
     * 编辑购物车购买数量
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateShoppingCart" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑购物车购买数量", notes = "编辑购物车购买数量",httpMethod = "POST")
    public ObjectRestResponse updateShoppingCart(UpdateShoppingCartNotLogin param){
        return  bizShoppingCartNotLoginBiz.updateShoppingCart(param);
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
    public ObjectRestResponse delShoppingCart(String id,String openId){
        return bizShoppingCartNotLoginBiz.delShoppingCart(id,openId);
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
    public ObjectRestResponse<ShoppingVo> getShoppingCartList(String projectId,String openId){
        return bizShoppingCartNotLoginBiz.getShoppingCartList(projectId,openId);
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
    public ObjectRestResponse getShoppingStatisInfo(String projectId,String openId,String specIds,boolean selectIsAll){
        return bizShoppingCartNotLoginBiz.getShoppingStatisInfo(projectId,specIds,openId, selectIsAll);
    }



    /**
     * 查询购物车数量
     * @return
     */
    @RequestMapping(value = "/getShoppingCartCount", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询购物车数量", notes = "查询购物车数量",httpMethod = "GET")
    @ApiImplicitParam(name="projectId",value="项目id",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse getShoppingCartCount(String projectId,String openId){
        return bizShoppingCartNotLoginBiz.getShoppingCartCount(projectId,openId);
    }



}