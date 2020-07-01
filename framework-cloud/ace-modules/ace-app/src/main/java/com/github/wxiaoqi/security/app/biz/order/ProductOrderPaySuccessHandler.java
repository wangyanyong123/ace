package com.github.wxiaoqi.security.app.biz.order;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.app.biz.BizOrderOperationRecordBiz;
import com.github.wxiaoqi.security.app.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.app.biz.BizSubscribeWoBiz;
import com.github.wxiaoqi.security.app.biz.order.handler.ProductOrderStoreHandler;
import com.github.wxiaoqi.security.app.biz.order.handler.SplitOrderByTenantHandler;
import com.github.wxiaoqi.security.app.entity.BizProduct;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderDetailMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderMapper;
import com.github.wxiaoqi.security.app.vo.order.out.OrderDataForPaySuccess;
import com.github.wxiaoqi.security.app.vo.order.out.OrderDetailSalesQuantity;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 处理商品订单支付成功业务逻辑
 *
 * @author: guohao
 * @create: 2020-04-20 11:32
 **/
@Slf4j
@Component
public class ProductOrderPaySuccessHandler {

    @Resource
    private BizProductOrderMapper bizProductOrderMapper;
    @Resource
    private BizProductOrderDetailMapper bizProductOrderDetailMapper;

    @Resource
    private BizProductMapper bizProductMapper;

    @Autowired
    private SplitOrderByTenantHandler splitOrderByTenantHandler;
    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;
    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;

    @Autowired
    private ProductOrderStoreHandler productOrderStoreHandler;
    @Autowired
    private BizSubscribeWoBiz bizSubscribeWoBiz;

    public ObjectRestResponse doPayOrderFinish(PayOrderFinishIn payOrderFinishIn) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();

        OrderDataForPaySuccess orderData = bizProductOrderMapper.selectOneForPaySuccessByActualId(payOrderFinishIn.getActualId());
        if (orderData == null) {
            log.error("支付回调未找到有效的订单信息。param：{}", JSON.toJSONString(payOrderFinishIn));
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调 未找到有效的订单信息。");
            return objectRestResponse;
        }
        if(AceDictionary.PAY_STATUS_PAID.equals(orderData.getAccountBookPayStatus())){
            log.error("支付回调,交易账单支付状态有误。param：{}", JSON.toJSONString(payOrderFinishIn));
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调 订单尚未支付。");
            return objectRestResponse;
        }

        BigDecimal paidPrice = new BigDecimal(payOrderFinishIn.getTotalFee());
        if(paidPrice.compareTo(orderData.getActualPrice()) != 0){
            log.error("支付回调,支付金额有订单金额不一致。param：{}, orderPrice:{}"
                    , JSON.toJSONString(payOrderFinishIn),orderData.getActualPrice());
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调,支付金额有订单金额不一致. orderPrice:"+orderData.getActualPrice());
            return objectRestResponse;
        }
        if(!AceDictionary.ORDER_STATUS_W_PAY.equals(orderData.getOrderStatus())){
            log.error("支付回调 订单状态有误。param:{}, orderData：{}"
                    ,JSON.toJSONString(payOrderFinishIn), JSON.toJSONString(orderData) );
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调 订单状态有误。");
            return objectRestResponse;
        }


        //拆分订单
        List<OrderIdResult> orderIdResultList = splitOrderByTenantHandler.splitOrder(orderData.getOrderId());

        OrderOperationType orderOperationType = OrderOperationType.PaySuccess;
        if(orderData.getOrderType().equals(AceDictionary.ORDER_TYPE_GROUP)){
            orderOperationType = OrderOperationType.WaitingGroupComplete;
        }
        bizOrderOperationRecordBiz.addRecordByOrderIdResultList(orderIdResultList, orderOperationType,"");

        productOrderStoreHandler.reduceDbStock(orderData.getParentId(),"paySuccess");
        // 更新产品销量
        List<OrderDetailSalesQuantity> orderDetailSalesQuantities
                = bizProductOrderDetailMapper.selectSalesQuantityByParentId(orderData.getParentId());
        updateProductSalesQuantity(orderDetailSalesQuantities);
        //处理团购，业务
        groupBuyHandle(orderData.getOrderType(),orderData.getParentId(),orderDetailSalesQuantities.get(0));

        return objectRestResponse;
    }

    //更新产品销量
    private void updateProductSalesQuantity(List<OrderDetailSalesQuantity>  detailSalesQuantities){
        for (OrderDetailSalesQuantity detailSalesQuantity : detailSalesQuantities) {
            bizProductMapper.udpateProductSalesById(detailSalesQuantity.getProductId(),detailSalesQuantity.getQuantity());
        }
    }

    /**
     * 团购情况处理
     */
    private void groupBuyHandle (Integer orderType,String parentId,OrderDetailSalesQuantity salesQuantity){
        if(!AceDictionary.ORDER_TYPE_GROUP.equals(orderType)){
            return;
        }
        int groupStatusComplete = productGroupStatusComplete(salesQuantity);
        if(1==groupStatusComplete){
            //商品已成团
            OrderIdResult orderIdResult = new OrderIdResult(salesQuantity.getOrderId(), parentId, AceDictionary.ORDER_STATUS_W_SEND);
            bizProductOrderBiz.finishGroupProductByOrderId(orderIdResult);
        }else  if(2==groupStatusComplete){
            //该商品的其他拼团
            // 小程序成团处理 后期订单统一后使用
//             bizProductOrderBiz.finishGroupProduct(salesQuantity.getProductId());
            // APP和小程序成团处理
            bizSubscribeWoBiz.finishGroupProduct(salesQuantity.getProductId());
        }
    }


    //商品团购是否已成团  0：未成团  1：商品已成团 2：满足团购数量成团
    private int productGroupStatusComplete( OrderDetailSalesQuantity salesQuantity){
        int subNum = salesQuantity.getQuantity();
        String productId = salesQuantity.getProductId();
        BizProduct bizProduct = bizProductMapper.selectByPrimaryKey(productId);
        String isGroupFlag = bizProduct.getIsGroupFlag();
        if("1".equals(isGroupFlag) ){
            return  1;
            // 前面已经更新销售量，所以这里不需要再加了
//        }else if((subNum+bizProduct.getSales()) >= bizProduct.getGroupbuyNum()){
        }else if(bizProduct.getSales() >= bizProduct.getGroupbuyNum()){
            return 2;
        }
        return 0;
    }



    /**
     * 处理支付前拆单的逻辑
     */
    public ObjectRestResponse doPayOrderFinishForSplitBeforePay(PayOrderFinishIn payOrderFinishIn) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();

        List<OrderDataForPaySuccess> subOrderList = bizProductOrderMapper.selectListForPaySuccessByActualId(payOrderFinishIn.getActualId());
        if (CollectionUtils.isEmpty(subOrderList)) {
            log.error("支付回调未找到有效的订单信息。param：{}", JSON.toJSONString(payOrderFinishIn));
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调 未找到有效的订单信息。");
            return objectRestResponse;
        }

        BigDecimal totalPrice = subOrderList.stream()
                .map(OrderDataForPaySuccess::getActualPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal paidPrice = new BigDecimal(payOrderFinishIn.getTotalFee());
        if(paidPrice.compareTo(totalPrice) != 0){
            log.error("支付回调,支付金额有订单金额不一致。param：{}, orderPrice:{}"
                    , JSON.toJSONString(payOrderFinishIn),totalPrice);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("支付回调,支付金额有订单金额不一致. orderPrice:"+totalPrice);
            return objectRestResponse;
        }
        for (OrderDataForPaySuccess orderData : subOrderList) {
            if(AceDictionary.PAY_STATUS_PAID.equals(orderData.getAccountBookPayStatus())){
                log.error("支付回调,交易账单支付状态有误。param：{}", JSON.toJSONString(payOrderFinishIn));
                objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
                objectRestResponse.setMessage("支付回调 订单尚未支付。");
                return objectRestResponse;
            }

            if(AceDictionary.ORDER_STATUS_W_PAY.equals(orderData.getOrderStatus())){
                log.error("支付回调 订单状态有误。param:{}, orderData：{}"
                        ,JSON.toJSONString(payOrderFinishIn), JSON.toJSONString(orderData) );
                objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
                objectRestResponse.setMessage("支付回调 订单状态有误。");
                return objectRestResponse;
            }

            //更新订单状态
            BizProductOrder updateOrder = new BizProductOrder();
            updateOrder.setId(orderData.getOrderId());
            updateOrder.setOrderStatus(AceDictionary.ORDER_STATUS_W_SEND);
            if(AceDictionary.ORDER_TYPE_GROUP.equals(orderData.getOrderType())){
                updateOrder.setOrderStatus(AceDictionary.ORDER_STATUS_GROUPING);
            }
            updateOrder.setModifyBy("pay notify");

            bizProductOrderMapper.updateByPrimaryKeySelective(updateOrder);

            bizProductOrderDetailMapper.updateOrderDetailStatusByOrderId(orderData.getOrderId(),
                    null,AceDictionary.ORDER_STATUS_W_PAY,updateOrder.getOrderStatus(),
                    updateOrder.getModifyBy());

            // 更新产品销量
            List<OrderDetailSalesQuantity> orderDetailSalesQuantities
                    = bizProductOrderDetailMapper.selectSalesQuantityByParentId(orderData.getOrderId());
            updateProductSalesQuantity(orderDetailSalesQuantities);

            //处理团购，业务
            groupBuyHandle(orderData.getOrderType(),orderData.getParentId(),orderDetailSalesQuantities.get(0));

        }

        return objectRestResponse;
    }


}
