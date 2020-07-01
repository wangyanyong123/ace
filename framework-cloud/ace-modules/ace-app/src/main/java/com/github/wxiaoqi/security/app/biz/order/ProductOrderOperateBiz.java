package com.github.wxiaoqi.security.app.biz.order;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ApplyRefundParam;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.*;
import com.github.wxiaoqi.security.app.biz.order.handler.ProductOrderStoreHandler;
import com.github.wxiaoqi.security.app.entity.BizAccountBook;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.mapper.BizAccountBookMapper;
import com.github.wxiaoqi.security.app.mapper.BizCouponUseMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderMapper;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 商品订单操作业务
 */

@Component
public class ProductOrderOperateBiz {
    @Autowired
    private ProductOrderCreateBiz productOrderCreateBiz;
    @Autowired
    private ProductOrderPaySuccessHandler productOrderPaySuccessHandler;
    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;
    @Resource
    private BizProductOrderMapper bizProductOrderMapper;

    @Autowired
    private BizProductOrderDetailBiz bizProductOrderDetailBiz;

    @Autowired
    private BizRefundAuditNewBiz bizRefundAuditNewBiz;
    @Autowired
    private ProductOrderStoreHandler productOrderStoreHandler;

    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;

    @Resource
    private BizAccountBookMapper bizAccountBookMapper;
    @Resource
    private BizProductOrderDiscountBiz bizProductOrderDiscountBiz;

    /**
     * 创建订单
     */
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse<BuyProductOutVo> createOrder(BuyProductInfo buyProductInfo) {
        ObjectRestResponse<BuyProductOutVo> restResponse = new ObjectRestResponse<>();
        BuyProductOutVo order = productOrderCreateBiz.createOrder(buyProductInfo);
        restResponse.setData(order);
        return restResponse;
    }

    //支付成功
    // 团购订单时会修改两个订单状态，会锁数据，暂时去掉事务
//    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse paySuccess(PayOrderFinishIn payOrderFinishIn) {
        return productOrderPaySuccessHandler.doPayOrderFinish(payOrderFinishIn);
    }

    /**
     * 取消订单
     */
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse cancelOrder(String orderId) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        BizProductOrder order = bizProductOrderBiz.selectById(orderId);
        Assert.isTrue(AceDictionary.ORDER_STATUS_W_PAY.equals(order.getOrderStatus())
                ,"请确认订单状态。");
        String handler = BaseContextHandler.getUserID();
        if (StringUtils.isEmpty(handler)) {
            handler = "system";
        }
        //未支付取消
        bizProductOrderMapper.updateOrderStatusById(orderId,AceDictionary.ORDER_STATUS_CAN,
                AceDictionary.ORDER_STATUS_W_PAY,handler);
        bizProductOrderDetailBiz.updateOrderDetailStatusByOrderId(orderId,AceDictionary.ORDER_STATUS_CAN,
                AceDictionary.ORDER_STATUS_W_PAY,handler);
        // 还库存
        productOrderStoreHandler.backRedisStock(orderId,handler);

        bizProductOrderDiscountBiz.backByOrderCancel(orderId);

        OrderIdResult orderIdResult = new OrderIdResult(order.getId(),order.getParentId(),order.getOrderStatus());
        bizOrderOperationRecordBiz.addRecordByOrderIdResult(orderIdResult,OrderOperationType.CancelOrderUnPay,"");
        return restResponse;
    }

    /**
     * 签收订单
     */
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse signOrder(String orderId) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        BizProductOrder order = bizProductOrderBiz.selectById(orderId);
        if(!AceDictionary.ORDER_STATUS_W_SIGN.equals(order.getOrderStatus())){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("请确认订单状态");
            return  restResponse;
        }
        BizProductOrder update = new BizProductOrder();
        update.setId(orderId);
        update.setOrderStatus(AceDictionary.ORDER_STATUS_COM);
        update.setModifyBy(BaseContextHandler.getUserID());
        update.setConfirmTime(DateTimeUtil.getLocalTime());
        bizProductOrderMapper.updateByPrimaryKeySelective(update);

        bizProductOrderDetailBiz.updateOrderDetailStatusByOrderId(orderId,AceDictionary.ORDER_STATUS_COM,
                AceDictionary.ORDER_STATUS_W_SIGN,BaseContextHandler.getUserID());

        OrderIdResult orderIdResult = new OrderIdResult(order.getId(),order.getParentId(),AceDictionary.ORDER_STATUS_COM);
        bizOrderOperationRecordBiz.addRecordByOrderIdResult(orderIdResult,OrderOperationType.SignOrder,"");
        return restResponse;
    }

    /**
     * 申请退款
     */
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse applyRefund(String orderId, String remark) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        BizProductOrder order = bizProductOrderBiz.selectById(orderId);

        //订单未支付不能申请
        if(order.getPaidTime() == null){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("订单尚未支付。");
            return  restResponse;
        }
        //订单已退款/退款进行中 不能申请
        if(!AceDictionary.ORDER_REFUND_STATUS_NONE.equals(order.getRefundStatus())){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("请确认订单退款状态");
            return  restResponse;
        }

        //订单已签收/取消 不能申请
        if(AceDictionary.ORDER_STATUS_COM.equals(order.getOrderStatus())
                || AceDictionary.ORDER_STATUS_CAN.equals(order.getOrderStatus())){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("请确认订单状态");
            return  restResponse;
        }
        boolean isSend = order.getSendTime() != null;
        // 不使用小程序中传的值，使用枚举中的值
        doUpdateOrderStatusByApplyRefund(order,null);
        doApplyRefund(order,isSend);

        return restResponse;
    }

    private ObjectRestResponse doUpdateOrderStatusByApplyRefund(BizProductOrder order,String remark) {

        BizProductOrder update = new BizProductOrder();
        update.setId(order.getId());
        update.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_APPLY);
        update.setModifyBy(BaseContextHandler.getUserID());
        bizProductOrderBiz.updateSelectiveById(update);
        bizProductOrderDetailBiz.updateRefundStatusByOrderId(order.getId(),null,
                AceDictionary.ORDER_REFUND_STATUS_APPLY,BaseContextHandler.getUserID());

        OrderIdResult orderIdResult = new OrderIdResult(order.getId(),order.getParentId(),order.getOrderStatus());
        bizOrderOperationRecordBiz.addRecordByOrderIdResult(orderIdResult,OrderOperationType.CancelOrderUnSend,remark);

        return ObjectRestResponse.ok();
    }

    /**
     * 申请退款
     */
    private ObjectRestResponse doApplyRefund(BizProductOrder order,boolean isSend){
        ApplyRefundParam applyRefundParam = new ApplyRefundParam();
        applyRefundParam.setSubId(order.getId());
        applyRefundParam.setBusType(AceDictionary.BUS_TYPE_PRODUCT_ORDER);
        applyRefundParam.setUserType(AceDictionary.USER_TYPE_BUYER);
        applyRefundParam.setSend(isSend);
        applyRefundParam.setUserId(order.getUserId());
        applyRefundParam.setSubCode(order.getOrderCode());
        applyRefundParam.setSubTitle(order.getTitle());
        applyRefundParam.setTenantId(order.getTenantId());
        applyRefundParam.setApplyPrice(order.getActualPrice());
        applyRefundParam.setSubCreateTime(order.getCreateTime());
        applyRefundParam.setProjectId(order.getProjectId());
        BizAccountBook accountBook = bizAccountBookMapper.getPayAndRefundStatusBySubId(order.getParentId());
        applyRefundParam.setActualId(accountBook.getActualId());
        return bizRefundAuditNewBiz.applyRefund(applyRefundParam);
    }

    /**
     * 订单退款成功
     * @param orderId 订单id
     * @return  ObjectRestResponse
     */
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse refundSuccess(String orderId,String handler){
        ObjectRestResponse objectRestResponse = ObjectRestResponse.ok();
        BizProductOrder productOrder = bizProductOrderBiz.selectById(orderId);
        if(productOrder == null){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("商品订单不存在，orderId:"+orderId);
            return objectRestResponse;
        }
        if(!AceDictionary.ORDER_REFUND_STATUS_APPLY.equals(productOrder.getRefundStatus())){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单退款状态有误，orderId:"+orderId+"; refundStatus:"+productOrder.getRefundStatus());
            return objectRestResponse;
        }
        if(AceDictionary.ORDER_STATUS_W_PAY.equals(productOrder.getOrderStatus())
                ||AceDictionary.ORDER_STATUS_CAN.equals(productOrder.getOrderStatus())
         ){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单状态有误，orderId:"+orderId+"; orderStatus:"+productOrder.getRefundStatus());
            return objectRestResponse;
        }
        BizProductOrder update = new BizProductOrder();
        update.setId(orderId);
        if(!AceDictionary.ORDER_STATUS_COM.equals(productOrder.getOrderStatus())){
            //未完成 直接取消订单
            update.setOrderStatus(AceDictionary.ORDER_STATUS_CAN);
        }
        update.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_COM);
        update.setModifyBy(handler);
        bizProductOrderBiz.updateSelectiveById(update);
        bizProductOrderDetailBiz.updateByRefundSuccess(orderId,null,update.getOrderStatus()
                ,AceDictionary.ORDER_REFUND_STATUS_COM,handler);
        bizOrderOperationRecordBiz.addOrderOperationRecord(orderId,productOrder.getParentId(),
                OrderOperationType.RefundSuccess,null);
        return objectRestResponse;
    }
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse refundReject(String orderId,String handler){
        ObjectRestResponse objectRestResponse = ObjectRestResponse.ok();
        BizProductOrder productOrder = bizProductOrderBiz.selectById(orderId);
        if(productOrder == null){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("商品订单不存在，orderId:"+orderId);
            return objectRestResponse;
        }
        if(!AceDictionary.ORDER_REFUND_STATUS_APPLY.equals(productOrder.getRefundStatus())){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单退款状态有误，orderId:"+orderId+"; refundStatus:"+productOrder.getRefundStatus());
            return objectRestResponse;
        }

        BizProductOrder update = new BizProductOrder();
        update.setId(orderId);
        update.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_NONE);
        update.setModifyBy(handler);
        bizProductOrderBiz.updateSelectiveById(update);
        bizProductOrderDetailBiz.updateRefundStatusByOrderId(orderId,null
                ,AceDictionary.ORDER_REFUND_STATUS_NONE,handler);
        bizOrderOperationRecordBiz.addOrderOperationRecord(orderId,productOrder.getParentId(),
                OrderOperationType.RefundReject,null);
        return objectRestResponse;
    }


    /**
     * 订单完成申请退款
     */
    @Transactional(rollbackFor = Exception.class)
    public ObjectRestResponse refundAudit(String orderId, String remark) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        BizProductOrder order = bizProductOrderBiz.selectById(orderId);

        //订单已退款/退款进行中 不能申请
        if(!AceDictionary.ORDER_REFUND_STATUS_NONE.equals(order.getRefundStatus())){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("请确认订单退款状态");
            return  restResponse;
        }

        //订单已签收/取消 不能申请
        if(!AceDictionary.ORDER_STATUS_COM.equals(order.getOrderStatus())){
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("请确认订单状态");
            return  restResponse;
        }
        doUpdateOrderStatusByApplyRefund(order,remark);
        doApplyRefund(order,Boolean.TRUE);
        return restResponse;
    }

}
