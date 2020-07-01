package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.app.biz.order.GenerateOrderCodeBiz;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.entity.BizOrderInvoice;
import com.github.wxiaoqi.security.app.entity.BizOrderRemark;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.entity.BizProductOrderDetail;
import com.github.wxiaoqi.security.app.mapper.BizOrderInvoiceMapper;
import com.github.wxiaoqi.security.app.mapper.BizOrderRemarkMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderDetailMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderMapper;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建商品订单处理器 -- 支付前按商户拆单
 * @author: guohao
 * @create: 2020-04-19 15:25
 **/
@Component
public class ProductOrderCreateByTentantHandler {

    @Resource
    private BizProductOrderMapper bizProductOrderMapper;
    @Resource
    private BizProductOrderDetailMapper bizProductOrderDetailMapper;
    @Resource
    private BizOrderRemarkMapper bizOrderRemarkMapper;
    @Resource
    private BizOrderInvoiceMapper bizOrderInvoiceMapper;
    @Autowired
    private GenerateOrderCodeBiz generateOrderCodeBiz;

    public void createOrder(CreateProductOrderContext createOrderContext){
        List<TenantInfo> tenantList = createOrderContext.getTenantList();

        String projectId = createOrderContext.getProjectId();
        int orderType = createOrderContext.getOrderType();
        int appType = createOrderContext.getAppType();
        RecipientInfo recipientInfo = createOrderContext.getRecipientInfo();
        String parentId = generateId();
        StringBuilder titleBuff = new StringBuilder();
        for (TenantInfo tenantInfo : tenantList) {
            BizProductOrder order = buildOrder(projectId, orderType, appType, parentId, recipientInfo, tenantInfo);

            BizOrderInvoice orderInvoice = doBuildInvoice(order.getId(), parentId, tenantInfo.getTenantId(), tenantInfo.getInvoiceInfo());
            BizOrderRemark orderRemark = doBuildRemark(order.getId(), parentId, tenantInfo.getTenantId(), tenantInfo.getRemark());

            List<BizProductOrderDetail> bizProductOrderDetails = buildOrderDetailList(order.getId(),parentId, tenantInfo);
            bizProductOrderMapper.insertSelective(order);
            //优化 批量插入
            for (BizProductOrderDetail bizProductOrderDetail : bizProductOrderDetails) {
                bizProductOrderDetailMapper.insertSelective(bizProductOrderDetail);
            }
            bizOrderInvoiceMapper.insertSelective(orderInvoice);
            bizOrderRemarkMapper.insertSelective(orderRemark);
            titleBuff.append(",").append(order.getTitle());
        }

        String actualId = generateId();
        BigDecimal actualPrice = tenantList.stream().map(TenantInfo::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BuyProductOutVo buyProductOutVo = new BuyProductOutVo();
        buyProductOutVo.setActualId(actualId);
        buyProductOutVo.setSubId(parentId);
        buyProductOutVo.setActualPrice(actualPrice.toString());
        buyProductOutVo.setTitle(titleBuff.substring(1));
        createOrderContext.setBuyProductOutVo(buyProductOutVo);

    }


    private BizProductOrder buildOrder(String projectId,Integer orderType,int appType,String parentId,
                                       RecipientInfo recipientInfo,TenantInfo tenantInfo){
        BizProductOrder order = new BizProductOrder();
        String orderId = generateId();
        String busId = tenantInfo.getSkuList().get(0).getBusId();
        String orderCode = getOrderCode(projectId,tenantInfo.getTenantId(),busId);
        order.setId(orderId);
        order.setParentId(parentId);
        order.setOrderCode(orderCode);
        order.setTenantId(tenantInfo.getTenantId());
        order.setProjectId(projectId);
        order.setUserId(BaseContextHandler.getUserID());
        order.setOrderType(orderType);

        order.setOrderStatus(AceDictionary.ORDER_STATUS_W_PAY);
        order.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_NONE);
        String desc = tenantInfo.getSkuList().stream().map(SkuInfo::getProductName)
                .collect(Collectors.joining(","));
        String title = "("+tenantInfo.getSkuList().get(0).getBusName()+")"+desc;
        order.setTitle(title);
        order.setDescription(desc);
        order.setAppType(appType);
        order.setActualPrice(tenantInfo.getTotalPrice());
        order.setProductPrice(tenantInfo.getProductPrice());
        order.setExpressPrice(tenantInfo.getExpressPrice());
        order.setDiscountPrice(tenantInfo.getDiscountPrice());

        Integer quantity = tenantInfo.getSkuList().stream().map(SkuInfo::getQuantity).reduce(0, (a, b) -> (a + b));
        order.setQuantity(quantity);

        order.setContactName(recipientInfo.getContactName());
        order.setContactTel(recipientInfo.getContactTel());
        order.setDeliveryAddr(recipientInfo.getAddr());


        order.setCreateBy(BaseContextHandler.getUserID());
        order.setCreateTime(new Date());
        order.setModifyBy(order.getCreateBy());
        order.setStatus(AceDictionary.DATA_STATUS_VALID);

        return order;
    }


    private BizOrderRemark doBuildRemark(String orderId, String parentId,
                                         String tenantId, String remark) {
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
    private BizOrderInvoice doBuildInvoice(String orderId, String parentId,
                                           String tenantId, InvoiceInfo invoiceInfo){
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

    private List<BizProductOrderDetail> buildOrderDetailList(String orderId,String parentId,TenantInfo tenantInfo){
        return tenantInfo.getSkuList().stream()
                .map(skuInfo -> doBuildOrderDetail(orderId,parentId,tenantInfo.getTenantId(),skuInfo))
                .collect(Collectors.toList());

    }
    private BizProductOrderDetail doBuildOrderDetail(String orderId,String parentId,String tenantId,SkuInfo skuInfo){
        BizProductOrderDetail orderDetail = new BizProductOrderDetail();

        orderDetail.setId(generateId());
        orderDetail.setOrderId(orderId);
        orderDetail.setParentId(parentId);
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
