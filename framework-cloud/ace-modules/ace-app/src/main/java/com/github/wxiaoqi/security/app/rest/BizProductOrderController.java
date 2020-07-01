package com.github.wxiaoqi.security.app.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.BizOrderOperationRecordBiz;
import com.github.wxiaoqi.security.app.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.vo.order.in.QueryOrderListIn;
import com.github.wxiaoqi.security.app.vo.order.out.OrderOperationRecordInfo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderInfoVo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderListVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.RequestHeaderUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品订单表
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@Slf4j
@RestController
@RequestMapping("bizProductOrder")
@CheckClientToken
@CheckUserToken
@Api(tags = "商品订单接口")
public class BizProductOrderController extends BaseController<BizProductOrderBiz,BizProductOrder,String> {

    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;
    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;

    /**
     * 商品下单
     */
    @RequestMapping(value = "/buyCompanyProduct" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "商品下单", notes = "商品下单",httpMethod = "POST")
    public ObjectRestResponse<BuyProductOutVo> buyCompanyProduct(@RequestBody @ApiParam BuyProductInfo buyProductInfo,HttpServletRequest request){
        buyProductInfo.setSource(RequestHeaderUtil.getPlatform(request));
        System.out.println(BaseContextHandler.getUserID());
        return  bizProductOrderBiz.buyCompanyProduct(buyProductInfo);
    }

    /**
     * 查询我的订单列表
     */
    @RequestMapping(value = "/findOrderList" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询我的订单列表", notes = "查询我的订单列表",httpMethod = "POST")
    public ObjectRestResponse<List<ProductOrderListVo>> findOrderList(@ApiParam QueryOrderListIn queryOrderListIn) {
        ObjectRestResponse<List<ProductOrderListVo>> objectRestResponse = new ObjectRestResponse<>();
        queryOrderListIn.setUserId(BaseContextHandler.getUserID());
        if(AceDictionary.ORDER_STATUS_W_COMMENT.equals(queryOrderListIn.getOrderStatus())){
            queryOrderListIn.setCommentStatus(AceDictionary.PRODUCT_COMMENT_NONE);
            queryOrderListIn.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_NONE);
            queryOrderListIn.setOrderStatus(AceDictionary.ORDER_STATUS_COM);
        }
        if(AceDictionary.ORDER_STATUS_W_SEND.equals(queryOrderListIn.getOrderStatus())
        ||AceDictionary.ORDER_STATUS_W_SIGN.equals(queryOrderListIn.getOrderStatus())){
            queryOrderListIn.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_NONE);
        }
        List<ProductOrderListVo> orderList = bizProductOrderBiz.findOrderList(queryOrderListIn);
        objectRestResponse.setData(orderList);
        return objectRestResponse;
    }

    /**
     * 查询订单详情
     */
    @RequestMapping(value = "/findOrderInfo" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询订单详情", notes = "查询订单详情",httpMethod = "GET")
    public ObjectRestResponse<ProductOrderInfoVo> findOrderInfo(String orderId) {
        ProductOrderInfoVo productOrderInfoVo = bizProductOrderBiz.findOrderInfo(orderId);
        return ObjectRestResponse.ok(productOrderInfoVo);
    }

    /**
     * 支付宝、微信回调通知成功后业务处理
     * @param payOrderFinishIn 参数
     */
    @RequestMapping(value = "/doPayOrderFinish" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "支付宝、微信回调通知成功后业务处理", notes = "支付宝、微信回调通知成功后业务处理",httpMethod = "POST")
    @IgnoreUserToken
    @IgnoreClientToken
    public ObjectRestResponse doPayOrderFinish(@RequestBody @ApiParam PayOrderFinishIn payOrderFinishIn) {
        return bizProductOrderBiz.doPayOrderFinish(payOrderFinishIn);
    }


    @RequestMapping(value = "/finishGroupProduct" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "团购商品手动成团", notes = "团购商品手动成团",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "商品id")
    public ObjectRestResponse finishGroupProduct(String id) {
        return bizProductOrderBiz.finishGroupProduct(id);
    }


    @RequestMapping(value = "/cancelOrder" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "取消订单", notes = "取消订单",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId",value = "订单id")
    })
    public ObjectRestResponse cancelOrder(String orderId) {
        return bizProductOrderBiz.cancelOrder(orderId);
    }

    @RequestMapping(value = "/applyRefund" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "申请退款，使用场景 未发货取消订单，发货后申请售后",
            notes = "申请退款，使用场景 未发货取消订单，发货后申请售后",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId",value = "订单id")
    })
    public ObjectRestResponse applyRefund(String orderId,String remark) {
        return bizProductOrderBiz.applyRefund(orderId,remark);
    }
    @RequestMapping(value = "/signOrder" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单签收", notes = "订单签收",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId",value = "订单id")
    })
    public ObjectRestResponse signOrder(String orderId) {
        return bizProductOrderBiz.signOrder(orderId);
    }

    @RequestMapping(value = "/findOrderOperationList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取订单步骤列表", notes = "取消订单",httpMethod = "GET")
    @ApiImplicitParam(name = "findOrderOperationList",value = "订单id")
    public ObjectRestResponse findOrderOperationList(String orderId) {
        List<OrderOperationRecordInfo> list = bizOrderOperationRecordBiz.findInfoVoByOrderId(orderId);
        return ObjectRestResponse.ok(list);
    }

    /**
     * 订单完成申请退款
     */
    @RequestMapping(value = "/refundAudit" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单完成申请退款", notes = "订单完成申请退款",httpMethod = "GET")
    @ApiImplicitParam(name = "refundAudit",value = "订单id")
    public ObjectRestResponse refundAudit(String orderId) {
        return this.baseBiz.refundAudit(orderId,null);
    }


}