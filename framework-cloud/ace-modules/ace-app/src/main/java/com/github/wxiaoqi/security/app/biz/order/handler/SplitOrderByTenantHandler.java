package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.wxiaoqi.security.app.biz.BizProductBiz;
import com.github.wxiaoqi.security.app.biz.BizProductNewBiz;
import com.github.wxiaoqi.security.app.biz.BizProductOrderBiz;
import com.github.wxiaoqi.security.app.biz.BizProductOrderDetailBiz;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.app.entity.BizOrderIncrement;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDetail;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDiscount;
import com.github.wxiaoqi.security.app.mapper.BizOrderIncrementMapper;
import com.github.wxiaoqi.security.app.mapper.BizOrderInvoiceMapper;
import com.github.wxiaoqi.security.app.mapper.BizOrderRemarkMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderDiscountMapper;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.google.inject.internal.cglib.core.$MethodInfoTransformer;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 支付成功后商户拆分订单
 */
@Slf4j
@Component
public class SplitOrderByTenantHandler {

    @Autowired
    private BizProductOrderBiz bizProductOrderBiz;
    @Autowired
    private BizProductOrderDetailBiz bizProductOrderDetailBiz;

    @Resource
    private BizOrderRemarkMapper bizOrderRemarkMapper;
    @Resource
    private BizOrderInvoiceMapper bizOrderInvoiceMapper;
    @Resource
    private BizOrderIncrementMapper bizOrderIncrementMapper;
    @Resource
    private BizProductOrderDiscountMapper bizProductOrderDiscountMapper;
    @Resource
    private BizProductNewBiz bizProductNewBiz;

    @Transactional(rollbackFor = Exception.class)
    public List<OrderIdResult> splitOrder(String orderId){

        BizProductOrder sourceOrder = bizProductOrderBiz.selectById(orderId);
        Assert.isTrue(AceDictionary.ORDER_STATUS_W_PAY.equals(sourceOrder.getOrderStatus())
                ,"请确认订单状态。"+AceDictionary.ORDER_STATUS.get(sourceOrder.getOrderStatus()));

        List<BizProductOrderDetail> orderDetailList = bizProductOrderDetailBiz.findByOrderId(orderId);

        long count = orderDetailList.stream().map(BizProductOrderDetail::getTenantId).distinct().count();

        if(count > 1){
           return doSplit(sourceOrder,orderDetailList);
        }else{
            BizProductOrder update = new BizProductOrder();
            if(AceDictionary.ORDER_TYPE_GROUP.equals(sourceOrder.getOrderType())){
                update.setOrderStatus(AceDictionary.ORDER_STATUS_GROUPING);
            }else{
                update.setOrderStatus(AceDictionary.ORDER_STATUS_W_SEND);
            }
            update.setId(orderId);
            update.setPaidTime(DateTimeUtil.getLocalTime());
            update.setModifyBy("paySuccess");
            bizProductOrderBiz.updateSelectiveById(update);
            bizProductOrderDetailBiz.updateOrderDetailStatusByOrderId(orderId,update.getOrderStatus(),
                    AceDictionary.ORDER_STATUS_W_PAY,"paySuccess");
            OrderIdResult orderIdResult = new OrderIdResult(sourceOrder.getId(),sourceOrder.getParentId(),update.getOrderStatus());
            return Collections.singletonList(orderIdResult);
        }


    }


    private List<OrderIdResult> doSplit(BizProductOrder sourceOrder,List<BizProductOrderDetail> orderDetailList ){
        String sourceOrderId = sourceOrder.getId();
        List<BizOrderIncrement> orderIncrementList = bizOrderIncrementMapper.selectByOrderId(sourceOrderId);
        List<BizProductOrderDiscount> orderDiscountList = bizProductOrderDiscountMapper
                .selectByOrderId(sourceOrder.getId(), AceDictionary.ORDER_RELATION_TYPE_O);

        Map<String, List<BizProductOrderDetail>> tenantMap =
                orderDetailList.stream().collect(Collectors.groupingBy(BizProductOrderDetail::getTenantId));
        Map<String, List<BizOrderIncrement>> incrementMap =
                orderIncrementList.stream().collect(Collectors.groupingBy(BizOrderIncrement::getTenantId));

        Map<String, List<BizProductOrderDiscount>> discountMap =
                orderDiscountList.stream().collect(Collectors.groupingBy(BizProductOrderDiscount::getTenantId));

        List<OrderIdResult> orderIdResultList = new ArrayList<>();
        for (Map.Entry<String, List<BizProductOrderDetail>> entry : tenantMap.entrySet()) {
            List<BizProductOrderDetail> subOrderDetailList = entry.getValue();
            List<BizProductOrderDiscount> subDiscountList = discountMap.getOrDefault(entry.getKey(),Collections.emptyList());
            List<BizOrderIncrement> subIncrementList = incrementMap.getOrDefault(entry.getKey(),Collections.emptyList());
            BizProductOrder subOrder = buildOrderTenantProduct(sourceOrder, subOrderDetailList, subIncrementList, subDiscountList);

            //设置子订单 金额
            setPrices(subOrder,subDiscountList,subIncrementList);
            bizProductOrderBiz.insertSelective(subOrder);
            bizProductOrderDetailBiz.updateOrderDetailBySplitOrder(sourceOrderId,subOrder.getId(),entry.getKey()
                    ,subOrder.getOrderStatus());
            bizProductOrderDiscountMapper.updateBySplitOrder(sourceOrderId,entry.getKey(),subOrder.getId());
            bizOrderIncrementMapper.updateBySplitOrder(sourceOrderId,entry.getKey(),subOrder.getId());
            bizOrderRemarkMapper.updateBySplitOrder(sourceOrderId,entry.getKey(),subOrder.getId());
            bizOrderInvoiceMapper.updateBySplitOrder(sourceOrderId,entry.getKey(),subOrder.getId());

            OrderIdResult orderIdResult = new OrderIdResult(subOrder.getId(),subOrder.getParentId(),subOrder.getOrderStatus());
            orderIdResultList.add(orderIdResult);
        }
        //将原定单设定为无效状态
        bizProductOrderBiz.updateInValid(sourceOrderId,"splitOrder");
        return orderIdResultList;
    }

    private void setPrices(BizProductOrder subOrder,
                           List<BizProductOrderDiscount> subDiscountList,
                           List<BizOrderIncrement> subIncrementList){

        BigDecimal discountPrice = subDiscountList.stream()
                .map(BizProductOrderDiscount::getDiscountPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expressPrice = BigDecimal.ZERO;
        BigDecimal incrementPrice = BigDecimal.ZERO;
        for (BizOrderIncrement orderIncrement : subIncrementList) {
            if(AceDictionary.ORDER_INCREMENT_EXPRESS.equals(orderIncrement.getIncrementType())){
                expressPrice = orderIncrement.getPrice();
            }
            incrementPrice = incrementPrice.add(orderIncrement.getPrice());
        }
        BigDecimal actualPrice = subOrder.getProductPrice().subtract(discountPrice).add(incrementPrice);
        subOrder.setActualPrice(actualPrice);
        subOrder.setExpressPrice(expressPrice);

    }
    private BizProductOrder buildOrderTenantProduct(BizProductOrder sourceOrder,List<BizProductOrderDetail> detailList,
                                         List<BizOrderIncrement> incrementList
            ,List<BizProductOrderDiscount> discountList ){

        String subOrderId = UUIDUtils.generateUuid();

        String tenantId = detailList.get(0).getTenantId();
        BizProductOrder subOrder = new BizProductOrder();
        BeanUtils.copyProperties(sourceOrder,subOrder);
        subOrder.setId(subOrderId);
        subOrder.setTenantId(tenantId);
        subOrder.setPaidTime(DateTimeUtil.getLocalTime());
        if(AceDictionary.ORDER_TYPE_GROUP.equals(sourceOrder.getOrderType())){
            subOrder.setOrderStatus(AceDictionary.ORDER_STATUS_GROUPING);
        }else{
            subOrder.setOrderStatus(AceDictionary.ORDER_STATUS_W_SEND);
        }

        BigDecimal actualPrice = BigDecimal.ZERO;
        BigDecimal productPrice = BigDecimal.ZERO;
        int quantity = 0;
        StringBuilder titleBuf = new StringBuilder();
        for (BizProductOrderDetail orderDetail : detailList) {
            actualPrice = actualPrice.add(orderDetail.getTotalPrice());
            BigDecimal subProductPrice = orderDetail.getSalesPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
            productPrice = productPrice.add(subProductPrice);
            quantity += orderDetail.getQuantity();
            titleBuf.append(",").append(orderDetail.getProductName());
        }

        String busName = bizProductNewBiz.getBusNameByPid(detailList.get(0).getProductId());
        subOrder.setDescription(titleBuf.substring(1));
        String title = busName+"("+subOrder.getDescription()+")";
        subOrder.setTitle(title);
        BigDecimal expressPrice = incrementList.stream()
                .filter(item -> AceDictionary.ORDER_INCREMENT_EXPRESS.equals(item.getIncrementType()))
                .map(BizOrderIncrement::getPrice).findFirst().orElse(BigDecimal.ZERO);
        BigDecimal discountPrice = discountList.stream()
                .map(BizProductOrderDiscount::getDiscountPrice).reduce(BigDecimal.ZERO,BigDecimal::add);

        subOrder.setActualPrice(actualPrice);
        subOrder.setProductPrice(productPrice);
        subOrder.setQuantity(quantity);
        subOrder.setExpressPrice(expressPrice);
        subOrder.setDiscountPrice(discountPrice);
        return subOrder;
    }
}
