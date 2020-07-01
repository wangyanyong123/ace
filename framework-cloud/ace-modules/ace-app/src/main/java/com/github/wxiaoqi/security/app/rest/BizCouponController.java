package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.app.biz.BizCouponBiz;
import com.github.wxiaoqi.security.app.vo.coupon.CouponListVo;
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
import springfox.documentation.annotations.ApiIgnore;

/**
 * 优惠券表
 *
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
@RestController
@RequestMapping("bizCoupon")
@CheckClientToken
@CheckUserToken
@Api(tags = "优惠券接口")
public class BizCouponController {

    @Autowired
    private BizCouponBiz bizCouponBiz;

    @GetMapping(value = "/getCoupon")
    @ApiOperation(value = "领取优惠券", notes = "领取优惠券",httpMethod = "GET")
    @ApiImplicitParam(name="couponId",value="优惠券ID",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
    public ObjectRestResponse<CouponListVo> getCoupon(String couponId) {
        return bizCouponBiz.getCoupon(couponId);
    }

    @GetMapping(value = "/getMyCoupon")
    @ApiOperation(value = "获取我的优惠券", notes = "获取我的优惠券",httpMethod = "GET")
    @ApiImplicitParam(name="couponStatus",value="优惠券状态 1-未使用,2-已使用，4-已过期",dataType="String",required = true ,paramType = "query",example="1")
    public ObjectRestResponse<CouponListVo> getMyCoupon(String couponStatus) {
        return bizCouponBiz.getMyCoupon(couponStatus);
    }

    @GetMapping(value = "/getCouponList")
    @ApiOperation(value = "获取商品优惠券列表", notes = "获取商品优惠券列表",httpMethod = "GET")
    @ApiImplicitParam(name="productId",value="商品ID",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
    public ObjectRestResponse<CouponListVo> getCouponList(String productId) {
        return bizCouponBiz.getCouponList(productId);
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @GetMapping(value = "/getCouponListUnLogin")
    @ApiOperation(value = "获取商品优惠券列表-未登录", notes = "获取商品优惠券列表-未登录",httpMethod = "GET")
    @ApiImplicitParam(name="productId",value="商品ID",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
    public ObjectRestResponse<CouponListVo> getCouponListUnLogin(String productId) {
        return bizCouponBiz.getCouponList(productId);
    }

    @PostMapping(value = "/getViableCouponList")
    @ResponseBody
    @ApiOperation(value = "获取商品可选择优惠券列表", notes = "获取商品已选择优惠券列表",httpMethod = "POST")
    public ObjectRestResponse<CouponListVo> getViableCouponList(@RequestBody @ApiParam BuyProductInfo productInfo) {
        return bizCouponBiz.getViableCouponList(productInfo);
    }

    @GetMapping(value = "/getOptimalCoupon")
    @ApiOperation(value = "获取商品最优优惠券", notes = "获取商品已选择优惠券",httpMethod = "GET")
    @ApiIgnore
    @ApiImplicitParam(name="productId",value="商品ID",dataType="String",required = true ,paramType = "query",example="dfsdfdsfafas")
    public ObjectRestResponse getOptimalCoupon(String productId) {
        return new ObjectRestResponse();
    }

    @PostMapping(value = "/getPostageInfo")
    @ResponseBody
    @ApiOperation(value = "获取商家的邮费金额", notes = "获取商家的邮费金额",httpMethod = "POST")
    public ObjectRestResponse getPostageInfo(@RequestBody @ApiParam BuyProductInfo productInfo) {
        return bizCouponBiz.getPostageInfo(productInfo);
    }
}