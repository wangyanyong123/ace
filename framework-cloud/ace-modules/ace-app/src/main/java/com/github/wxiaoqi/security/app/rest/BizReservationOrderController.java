package com.github.wxiaoqi.security.app.rest;

import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.BizReservationOrderBiz;
import com.github.wxiaoqi.security.app.entity.BizReservationOrder;
import com.github.wxiaoqi.security.app.reservation.dto.ReservationCommentVO;
import com.github.wxiaoqi.security.app.reservation.vo.ReservationOrderQueryVO;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 预约服务订单表
 *
 * @author huangxl
 * @Date 2020-04-19 17:01:24
 */
@RestController
@RequestMapping("bizReservationOrder")
@CheckClientToken
@CheckUserToken
@Slf4j
public class BizReservationOrderController extends BaseController<BizReservationOrderBiz,BizReservationOrder,String> {


    /**
     * 预约服务产品下单
     * @return
     */
    @RequestMapping(value = "/buyReservationProduct" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "预约服务产品下单", notes = "服务下单模块---预约服务产品下单",httpMethod = "POST")
    public ObjectRestResponse<BuyProductOutVo> buyCompanyProduct(@RequestBody @ApiParam BuyProductInfo buyProductInfo, HttpServletRequest request){
        buyProductInfo.setOrderType(AceDictionary.ORDER_TYPE_ORDINARY);
        buyProductInfo.setSource(RequestHeaderUtil.getPlatform(request));
        return this.baseBiz.buyCompanyProduct(buyProductInfo);
    }

    /**
     * 获取我的预约列表
     * @param reservationOrderQueryVO
     * @return
     */
    @RequestMapping(value = "/queryReservationOrderListPage" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取我的预约列表", notes = "获取我的预约列表",httpMethod = "GET")
    public ObjectRestResponse queryReservationOrderListPage(@ApiParam ReservationOrderQueryVO reservationOrderQueryVO) {
        return this.baseBiz.queryReservationOrderListPage(reservationOrderQueryVO);
    }

    /**
     * 预约订单详情
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/queryReservationOrderDetail" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "预约订单详情", notes = "预约订单详情",httpMethod = "GET")
    public ObjectRestResponse queryReservationOrderDetail(String orderId) {
        return this.baseBiz.queryReservationOrderDetail(orderId);
    }

    @RequestMapping(value = "/doComment" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "预约订单评论", notes = "预约订单评论",httpMethod = "POST")
    public ObjectRestResponse doComment(@RequestBody @ApiParam ReservationCommentVO reservationCommentVO){
        return this.baseBiz.doComment(reservationCommentVO);
    }

    /**
     * 支付成功回调
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/paySuccess" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "支付回调", notes = "支付回调",httpMethod = "GET")
    public ObjectRestResponse doPayOrderFinish(String orderId) {
        return this.baseBiz.paySuccess(orderId);
    }

    /**
     * 退款成功回调
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/returnSuccess" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "退款成功", notes = "退款成功",httpMethod = "GET")
    public ObjectRestResponse returnSuccess(String orderId) {
        return this.baseBiz.returnSuccess(orderId);
    }

    /**
     * 取消
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/concel" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "取消", notes = "取消",httpMethod = "GET")
    public ObjectRestResponse cancel(String orderId){
        return this.baseBiz.cancel(orderId);
    }


    /**
     * 获取评论信息
     * @param productId
     * @return
     */
    @RequestMapping(value = "/comment" ,method = RequestMethod.GET)
    @ResponseBody
    @IgnoreUserToken
    @IgnoreClientToken
    @ApiOperation(value = "获取评论", notes = "获取评论",httpMethod = "GET")
    public ObjectRestResponse getReservationComment(String productId){
        return this.baseBiz.getReservationComment(productId);
    }

    /**
     * 获取操作记录
     * @return
     */
    @RequestMapping(value = "/queryReservationOrderOperation" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取操作记录", notes = "获取操作记录",httpMethod = "GET")
    public ObjectRestResponse queryReservationOrderOperation(String orderId){
        return this.baseBiz.getOperation(orderId);
    }

    /**
     * 完成后申请退款
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/returnAudit" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "完成后申请退款", notes = "完成后申请退款",httpMethod = "GET")
    public ObjectRestResponse returnAudit(String orderId) {
        return this.baseBiz.returnAudit(orderId);
    }
}
