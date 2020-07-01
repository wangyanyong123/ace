package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.order.GenerateOrderCodeBiz;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.order.out.OrderIdResult;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建商品订单处理器 -- 支付前不按商户拆单
 * @author: guohao
 * @create: 2020-04-19 15:25
 **/
@Component
public class ProductOrderCreateHandler {

    @Resource
    private BizProductOrderMapper bizProductOrderMapper;
    @Resource
    private BizProductOrderDetailMapper bizProductOrderDetailMapper;
    @Resource
    private BizProductOrderDiscountMapper bizProductOrderDiscountMapper;
    @Resource
    private BizOrderRemarkMapper bizOrderRemarkMapper;
    @Resource
    private BizOrderInvoiceMapper bizOrderInvoiceMapper;
    @Resource
    private BizOrderIncrementMapper bizOrderIncrementMapper;

    @Autowired
    private GenerateOrderCodeBiz generateOrderCodeBiz;

    public void createOrder(CreateProductOrderContext createOrderContext){

        BizProductOrder order = buildOrder(createOrderContext);
        createOrderContext.setZeroOrder(BigDecimal.ZERO.compareTo(order.getActualPrice()) == 0);

        bizProductOrderMapper.insertSelective(order);


        List<BizOrderInvoice> invoiceList = buildOrderInvoice(createOrderContext, order.getId());
        List<BizOrderRemark> remarkList = buildOrderRemark(createOrderContext, order.getId());
        List<BizProductOrderDetail> detailList = buildOrderDetailList(createOrderContext,order.getId());
        List<BizOrderIncrement> incrementList = buildOrderIncrement(createOrderContext, order.getId());
        List<BizProductOrderDiscount> discountList = buildOrderDiscount(createOrderContext, order.getId());

        saveOrderDetail(detailList);
        saveOrderInvoice(invoiceList);
        saveOrderRemark(remarkList);
        saveOrderIncrement(incrementList);
        saveOrderDiscount(discountList);

        String actualId = generateId();
        BuyProductOutVo buyProductOutVo = new BuyProductOutVo();
        buyProductOutVo.setActualId(actualId);
        buyProductOutVo.setSubId(order.getParentId());
        buyProductOutVo.setActualPrice(order.getActualPrice().toString());
        buyProductOutVo.setTitle(order.getTitle());
        createOrderContext.setBuyProductOutVo(buyProductOutVo);

        OrderIdResult orderIdResult = new OrderIdResult(order.getId(),order.getParentId(),order.getOrderStatus());
        createOrderContext.setOrderIdResultList(Collections.singletonList(orderIdResult));

    }

    private void saveOrderDetail(List<BizProductOrderDetail> detailList){
        //优化 批量插入
        for (BizProductOrderDetail bizProductOrderDetail : detailList) {
            bizProductOrderDetailMapper.insertSelective(bizProductOrderDetail);
        }
    }

    private void saveOrderRemark(List<BizOrderRemark> remarkList){
        //优化 批量插入
        if(CollectionUtils.isNotEmpty(remarkList)){
            for (BizOrderRemark orderRemark : remarkList) {
                bizOrderRemarkMapper.insertSelective(orderRemark);
            }
        }

    }
    private void saveOrderInvoice(List<BizOrderInvoice> invoiceList){
        //优化 批量插入
        if(CollectionUtils.isNotEmpty(invoiceList)){
            for (BizOrderInvoice orderInvoice : invoiceList) {
                bizOrderInvoiceMapper.insertSelective(orderInvoice);
            }
        }
    }
    private void saveOrderIncrement(List<BizOrderIncrement> orderIncrementList){
        //优化 批量插入
        if(CollectionUtils.isNotEmpty(orderIncrementList)){
            for (BizOrderIncrement orderIncrement : orderIncrementList) {
                bizOrderIncrementMapper.insertSelective(orderIncrement);
            }
        }
    }
    private void saveOrderDiscount(List<BizProductOrderDiscount> orderDiscountList){
        //优化 批量插入
        if(CollectionUtils.isNotEmpty(orderDiscountList)){
            for (BizProductOrderDiscount discount : orderDiscountList) {
                bizProductOrderDiscountMapper.insertSelective(discount);
            }
        }
    }


    private BizProductOrder buildOrder(CreateProductOrderContext createOrderContext){
        BizProductOrder order = new BizProductOrder();
        String orderId = generateId();
        String busId = createOrderContext.getTenantList().get(0).getSkuList().get(0).getBusId();
        String tenantId = createOrderContext.getTenantList().get(0).getTenantId();
        String orderCode = getOrderCode(createOrderContext.getProjectId(),tenantId,busId);
        order.setId(orderId);
        order.setParentId(orderId);
        order.setOrderCode(orderCode);
        order.setTenantId(tenantId);
        order.setProjectId(createOrderContext.getProjectId());
        order.setUserId(BaseContextHandler.getUserID());
        order.setOrderType(createOrderContext.getOrderType());

        order.setOrderStatus(AceDictionary.ORDER_STATUS_W_PAY);
        order.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_NONE);
        order.setCommentStatus(AceDictionary.PRODUCT_COMMENT_NONE);

        String desc = createOrderContext.getTenantList().stream()
                .flatMap(tenantInfo -> tenantInfo.getSkuList().stream()).map(SkuInfo::getProductName)
                .collect(Collectors.joining(","));
        String title;
        if(createOrderContext.getTenantList().size() > 1){
            title = "悦邻订单("+desc+")";
        }else{
            title = createOrderContext.getTenantList().get(0).getSkuList().get(0).getBusName()+"("+desc+")";
        }
        order.setTitle(title);
        order.setDescription(desc);
        order.setAppType(createOrderContext.getAppType());

        BigDecimal actualPrice = BigDecimal.ZERO;
        BigDecimal productPrice = BigDecimal.ZERO;
        BigDecimal expressPrice = BigDecimal.ZERO;
        BigDecimal discountPrice = BigDecimal.ZERO;
        int quantity = 0;
        for (TenantInfo tenantInfo : createOrderContext.getTenantList()) {
            actualPrice = actualPrice.add(tenantInfo.getTotalPrice());
            productPrice = productPrice.add(tenantInfo.getProductPrice());
            expressPrice = expressPrice.add(tenantInfo.getExpressPrice());
            discountPrice = discountPrice.add(tenantInfo.getDiscountPrice());
            quantity= tenantInfo.getSkuList().stream().map(SkuInfo::getQuantity).reduce(quantity, Integer::sum);
        }

        order.setActualPrice(actualPrice);
        order.setProductPrice(productPrice);
        order.setExpressPrice(expressPrice);
        order.setDiscountPrice(discountPrice);
        order.setQuantity(quantity);

        RecipientInfo recipientInfo = createOrderContext.getRecipientInfo();
        order.setContactName(recipientInfo.getContactName());
        order.setContactTel(recipientInfo.getContactTel());
        order.setDeliveryAddr(recipientInfo.getAddr());


        order.setCreateBy(BaseContextHandler.getUserID());
        order.setCreateTime(new Date());
        order.setModifyBy(order.getCreateBy());
        order.setStatus(AceDictionary.DATA_STATUS_VALID);

        return order;
    }

    private List<BizOrderIncrement> buildOrderIncrement(CreateProductOrderContext createOrderContext,String orderId){

       return createOrderContext.getTenantList().stream()
                .filter(tenantInfo -> tenantInfo.getExpressPrice() != null
                        && tenantInfo.getExpressPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(item-> buildOrderExpressIncrement(item.getTenantId(),orderId,item.getExpressPrice()))
                .collect(Collectors.toList());
    }

    private BizOrderIncrement buildOrderExpressIncrement(String tenantId,String orderId,BigDecimal expressPrice){
        BizOrderIncrement orderIncrement = new BizOrderIncrement();
        String id = generateId();
        orderIncrement.setId(id);
        orderIncrement.setOrderId(orderId);
        orderIncrement.setParentId(orderId);
        orderIncrement.setIncrementType(AceDictionary.ORDER_INCREMENT_EXPRESS);
        orderIncrement.setTenantId(tenantId);
        orderIncrement.setPrice(expressPrice);
        orderIncrement.setCreateBy(BaseContextHandler.getUserID());
        orderIncrement.setCreateTime(DateTimeUtil.getLocalTime());
        orderIncrement.setModifyBy(orderIncrement.getCreateBy());
        orderIncrement.setStatus(AceDictionary.DATA_STATUS_VALID);
        return orderIncrement;
    }
    private List<BizOrderRemark> buildOrderRemark(CreateProductOrderContext createOrderContext,String orderId){

       return createOrderContext.getTenantList().stream()
                .filter(item-> StringUtils.isNotEmpty(item.getRemark()))
                .map(item -> doBuildRemark(orderId,orderId,item.getTenantId(),item.getRemark()))
                .collect(Collectors.toList());
    }

    private BizOrderRemark doBuildRemark(String orderId,String parentId,
                                           String tenantId,String remark) {
        BizOrderRemark orderRemark = new BizOrderRemark();
        String id = generateId();
        orderRemark.setId(id);
        orderRemark.setOrderId(orderId);
        orderRemark.setParentId(parentId);
        orderRemark.setTenantId(tenantId);
        orderRemark.setCreateBy(BaseContextHandler.getUserID());
        orderRemark.setCreateTime(DateTimeUtil.getLocalTime());
        orderRemark.setModifyBy(orderRemark.getCreateBy());
        orderRemark.setStatus(AceDictionary.DATA_STATUS_VALID);
        orderRemark.setRemark(remark);
        return orderRemark;
    }

    private List<BizOrderInvoice> buildOrderInvoice(CreateProductOrderContext createOrderContext,String orderId){
       return createOrderContext.getTenantList().stream().map(item -> doBuildInvoice(orderId,orderId,item.getTenantId(),item.getInvoiceInfo())).collect(Collectors.toList());
    }

    private BizOrderInvoice doBuildInvoice(String orderId,String parentId,
            String tenantId,InvoiceInfo invoiceInfo){
        BizOrderInvoice orderInvoice = new BizOrderInvoice();
        String id = generateId();
        orderInvoice.setId(id);
        orderInvoice.setOrderId(orderId);
        orderInvoice.setParentId(parentId);
        orderInvoice.setTenantId(tenantId);
        orderInvoice.setInvoiceType(invoiceInfo.getInvoiceType());
        orderInvoice.setInvoiceName(invoiceInfo.getInvoiceName());
        orderInvoice.setDutyCode(invoiceInfo.getDutyNum());
        orderInvoice.setCreateBy(BaseContextHandler.getUserID());
        orderInvoice.setCreateTime(DateTimeUtil.getLocalTime());
        orderInvoice.setModifyBy(orderInvoice.getCreateBy());
        orderInvoice.setStatus(AceDictionary.DATA_STATUS_VALID);
        return orderInvoice;
    }

    private List<BizProductOrderDiscount> buildOrderDiscount(CreateProductOrderContext createOrderContext,String orderId){
       return createOrderContext.getTenantList().stream()
               .flatMap(item -> buildOrderDiscount(item,orderId).stream())
               .collect(Collectors.toList());
    }
    private List<BizProductOrderDiscount> buildOrderDiscount(TenantInfo tenantInfo,String orderId){
       return tenantInfo.getDiscountList().stream()
               .map(item -> doBuildOrderDiscount(orderId,orderId,tenantInfo.getTenantId(),item))
               .collect(Collectors.toList());
    }

    private BizProductOrderDiscount doBuildOrderDiscount(String orderId,String parentId,
            String tenantId,DiscountInfo discountInfo){
        BizProductOrderDiscount discount = new BizProductOrderDiscount();
        discount.setId(UUIDUtils.generateUuid());
        discount.setOrderId(orderId);
        discount.setParentId(parentId);
        discount.setTenantId(tenantId);
        discount.setRelationId(discountInfo.getRelationId());
        discount.setDiscountType(discountInfo.getDiscountType());
        discount.setDiscountPrice(discountInfo.getDiscountPrice());
        discount.setOrderRelationType(AceDictionary.DISCOUNT_RELATION_TYPE_ORDER);
        discount.setCreateBy(BaseContextHandler.getUserID());
        discount.setCreateTime(DateTimeUtil.getLocalTime());
        discount.setModifyBy(discount.getCreateBy());
        discount.setStatus(AceDictionary.DATA_STATUS_VALID);
        return discount;
    }


    private List<BizProductOrderDetail> buildOrderDetailList(CreateProductOrderContext createOrderContext,String orderId){
       return createOrderContext.getTenantList().stream()
                .flatMap(item->buildOrderDetailList(orderId,item).stream())
                .collect(Collectors.toList());
    }

    private List<BizProductOrderDetail> buildOrderDetailList(String orderId,TenantInfo tenantInfo){
        return tenantInfo.getSkuList().stream()
                .map(skuInfo -> doBuildOrderDetail(orderId,tenantInfo.getTenantId(),skuInfo))
                .collect(Collectors.toList());

    }
    private BizProductOrderDetail doBuildOrderDetail(String orderId,String tenantId,SkuInfo skuInfo){
        BizProductOrderDetail orderDetail = new BizProductOrderDetail();

        orderDetail.setId(generateId());
        orderDetail.setOrderId(orderId);
        orderDetail.setParentId(orderId);
        orderDetail.setDetailStatus(AceDictionary.ORDER_STATUS_W_PAY);
        orderDetail.setDetailRefundStatus(AceDictionary.ORDER_REFUND_STATUS_NONE);
        orderDetail.setTenantId(tenantId);
        orderDetail.setProductId(skuInfo.getProductId());
        orderDetail.setProductName(skuInfo.getProductName());
        orderDetail.setSpecId(skuInfo.getSpecId());
        orderDetail.setSpecName(skuInfo.getSpecName());
        orderDetail.setSpecImg(skuInfo.getSpecImage());
        orderDetail.setQuantity(skuInfo.getQuantity());
        orderDetail.setSalesPrice(skuInfo.getSalesPrice());
        orderDetail.setTotalPrice(skuInfo.getSalesPrice().multiply(BigDecimal.valueOf(skuInfo.getQuantity())));
        orderDetail.setUnit(skuInfo.getUnit());
        orderDetail.setCommentStatus(AceDictionary.PRODUCT_COMMENT_NONE);

        orderDetail.setCreateBy(BaseContextHandler.getUserID());
        orderDetail.setCreateTime(new Date());
        orderDetail.setModifyBy(orderDetail.getCreateBy());
        orderDetail.setStatus(AceDictionary.DATA_STATUS_VALID);
        return  orderDetail;
    }

    private String generateId(){
        return UUIDUtils.generateUuid();
    }

    private String getOrderCode(String projectId,String tenantId,String busId ){
        return generateOrderCodeBiz.generateOrderCode(projectId,tenantId,busId);
    }
}
