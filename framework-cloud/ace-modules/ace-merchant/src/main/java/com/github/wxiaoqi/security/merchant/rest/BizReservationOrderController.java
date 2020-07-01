package com.github.wxiaoqi.security.merchant.rest;

import com.github.wxiaoqi.security.api.vo.waiter.OrderWaiterVO;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.merchant.biz.BizReservationOrderBiz;
import com.github.wxiaoqi.security.merchant.entity.BizReservationOrder;
import com.github.wxiaoqi.security.merchant.vo.order.OrderQueryVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 预约服务订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 13:13:42
 */
@RestController
@RequestMapping("bizReservationOrder")
@CheckClientToken
@CheckUserToken
public class BizReservationOrderController extends BaseController<BizReservationOrderBiz,BizReservationOrder,String> {

    /**
     * 我的服务订单列表
     * @param productQueryVO
     * @return
     */
    @GetMapping("queryOrderListPage")
    @ApiOperation(value = "我的服务订单", notes = "我的服务订单",httpMethod = "GET")
    public BaseResponse queryOrderListPage(@ApiParam OrderQueryVO productQueryVO){
        return this.baseBiz.queryOrderListPage(productQueryVO);
    }

    /**
     * 分配服务人员
     * @param orderWaiterVO
     * @return
     */
    @PostMapping("assignWaiter")
    @ApiOperation(value = "分配服务人员", notes = "分配服务人员",httpMethod = "POST")
    public ObjectRestResponse assignWaiter(@RequestBody @ApiParam @Valid OrderWaiterVO orderWaiterVO){
        return this.baseBiz.assignWaiter(orderWaiterVO);
    }

    /**
     * 更新分配人员
     * @param orderWaiterVO
     * @return
     */
    @PostMapping("updateWaiter")
    @ApiOperation(value = "分配服务人员", notes = "分配服务人员",httpMethod = "POST")
    public ObjectRestResponse saveOrUpdateWaiter(@RequestBody @ApiParam @Valid OrderWaiterVO orderWaiterVO){
        return this.baseBiz.saveOrUpdateWaiter(orderWaiterVO);
    }

    /**
     * 订单统计
     * @return
     */
    @GetMapping("orderTotal")
    @ApiOperation(value = "订单统计", notes = "订单统计",httpMethod = "GET")
    public ObjectRestResponse orderTotal(){
        return this.baseBiz.orderTotal();
    }

    /**
     * 服务订单详情
     * @param orderId
     * @return
     */
    @GetMapping("queryOrderDetail")
    @ApiOperation(value = "服务订单详情", notes = "服务订单详情",httpMethod = "GET")
    public ObjectRestResponse queryOrderDetail(String orderId){
        return this.baseBiz.queryOrderDetail(orderId);
    }

    /**
     * 完成
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/finish" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "取消", notes = "取消",httpMethod = "GET")
    public ObjectRestResponse doFinish(String orderId){
        return this.baseBiz.doFinish(orderId);
    }
}