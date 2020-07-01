package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.api.vo.logistics.OrderLogisticsVO;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.merchant.entity.BizProductOrder;
import com.github.wxiaoqi.security.merchant.vo.order.OrderQueryVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 13:13:28
 */
@RestController
@RequestMapping("bizProductOrder")
@CheckClientToken
@CheckUserToken
public class BizProductOrderController extends BaseController<BizProductOrderBiz,BizProductOrder,String> {


    /**
     * 我的商品订单列表
     * @param productQueryVO
     * @return
     */
    @GetMapping("queryOrderListPage")
    @ApiOperation(value = "我的商品订单", notes = "我的商品订单",httpMethod = "GET")
    public BaseResponse queryProductOrderListPage(@ApiParam OrderQueryVO productQueryVO){
        return this.baseBiz.queryOrderListPage(productQueryVO);
    }

    /**
     * 商品发货
     * @param orderLogisticsVO
     * @return
     */
    @PostMapping("sendProduct")
    @ApiOperation(value = "商品发货", notes = "商品发货",httpMethod = "POST")
    public ObjectRestResponse sendProduct(@RequestBody @ApiParam @Valid OrderLogisticsVO orderLogisticsVO){
        return this.baseBiz.sendProduct(orderLogisticsVO);
    }

    /**
     * 服务订单详情
     * @param orderId
     * @return
     */
    @GetMapping("queryOrderDetail")
    @ApiOperation(value = "商品订单详情", notes = "商品订单详情",httpMethod = "GET")
    public ObjectRestResponse queryOrderDetail(String orderId){
        return this.baseBiz.queryOrderDetail(orderId);
    }

    /**
     * 确认收货
     */
    @GetMapping("confirmOrder")
    @ApiOperation(value = "商品订单确认收货", notes = "商品订单确认收货",httpMethod = "GET")
    public ObjectRestResponse confirmOrder(String orderId) {
        return this.baseBiz.confirmOrder(orderId);
    }


    /**
     * 更新物流信息
     */
    @PostMapping("updateOrderLogistics")
    @ApiOperation(value = "更新物流信息", notes = "更新物流信息",httpMethod = "POST")
    public ObjectRestResponse saveOrUpdateOrderLogistics(@RequestBody @ApiParam @Valid OrderLogisticsVO orderLogisticsVO){
        return this.baseBiz.saveOrUpdateOrderLogistics(orderLogisticsVO);
    }
}