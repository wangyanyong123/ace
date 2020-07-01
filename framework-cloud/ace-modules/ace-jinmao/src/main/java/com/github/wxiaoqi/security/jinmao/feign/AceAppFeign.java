package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-app", configuration = FeignApplyConfiguration.class)
public interface AceAppFeign {

    @RequestMapping(value = "/bizProductOrder/cancelOrder" ,method = RequestMethod.GET)
    @ApiOperation(value = "取消订单", notes = "取消订单",httpMethod = "GET")
    ObjectRestResponse cancelOrder(@RequestParam("orderId") String orderId);

    @RequestMapping(value = "/bizProductOrder/applyRefund" ,method = RequestMethod.GET)
    @ApiOperation(value = "申请退款，使用场景 未发货取消订单，发货后申请售后",
            notes = "申请退款，使用场景 未发货取消订单，发货后申请售后",httpMethod = "GET")
    ObjectRestResponse applyRefund(@RequestParam("orderId") String orderId,@RequestParam("remark") String remark);

    @RequestMapping(value = "/bizProductOrder/signOrder" ,method = RequestMethod.GET)
    @ApiOperation(value = "订单签收", notes = "订单签收",httpMethod = "GET")
    ObjectRestResponse signOrder(@RequestParam("orderId") String orderId);

    /**
     * 服务订单取消
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/bizReservationOrder/concel" ,method = RequestMethod.GET)
    @ApiOperation(value = "取消", notes = "取消",httpMethod = "GET")
    ObjectRestResponse cancel(@RequestParam("orderId") String orderId);


    /**
     * 服务订单完成后申请退款
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/bizReservationOrder/returnAudit" ,method = RequestMethod.GET)
    @ApiOperation(value = "服务订单完成后申请退款", notes = "服务订单完成后申请退款",httpMethod = "GET")
    ObjectRestResponse reservationOrderReturnAudit(@RequestParam("orderId") String orderId);


    /**
     * 商品订单完成申请退款
     */
    @RequestMapping(value = "/bizProductOrder/refundAudit" ,method = RequestMethod.GET)
    @ApiOperation(value = "商品订单完成申请退款", notes = "商品订单完成申请退款",httpMethod = "GET")
    ObjectRestResponse productOrderRefundAudit(@RequestParam("orderId") String orderId);

}
