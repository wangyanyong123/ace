package com.github.wxiaoqi.security.app.biz.order.handler;//package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.out.BuyProductOutVo;
import com.github.wxiaoqi.security.api.vo.store.reservation.ReservationStore;
import com.github.wxiaoqi.security.app.biz.BizReservationOrderOperationRecordBiz;
import com.github.wxiaoqi.security.app.biz.ReservationOrderTimeOutJobBiz;
import com.github.wxiaoqi.security.app.biz.order.GenerateOrderCodeBiz;
import com.github.wxiaoqi.security.app.biz.order.context.*;
import com.github.wxiaoqi.security.app.fegin.StoreReservationFegin;
import com.github.wxiaoqi.security.app.mapper.BizReservationOrderDetailMapper;
import com.github.wxiaoqi.security.app.mapper.BizReservationOrderMapper;
import com.github.wxiaoqi.security.app.entity.BizReservationOrder;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderDetail;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建商品订单处理器
 * @author: guohao
 * @create: 2020-04-19 15:25
 **/
@Component
public class ReservationOrderCreateHandler {

    @Resource
    private BizReservationOrderMapper bizReservationOrderMapper;
    @Resource
    private BizReservationOrderDetailMapper bizReservationOrderDetailMapper;

    @Autowired
    private GenerateOrderCodeBiz generateOrderCodeBiz;

    @Autowired
    private BizReservationOrderOperationRecordBiz bizReservationOrderOperationRecordBiz;
    @Autowired
    private ReservationOrderTimeOutJobBiz reservationOrderTimeOutJobBiz;
    @Resource
    private StoreReservationFegin storeReservationFegin;

    public void createOrder(CreateReservationOrderContext createOrderContext){
        String projectId = createOrderContext.getProjectId();
        String orderId = createOrderContext.getOrderId();
        Date createTime = createOrderContext.getCreateTime();
        int orderType = createOrderContext.getOrderType();
        int appType = createOrderContext.getAppType();
        RecipientInfo recipientInfo = createOrderContext.getRecipientInfo();

        ReservationTenantInfo reservationTenantInfo = createOrderContext.getReservationTenantInfo();
        BizReservationOrder order = buildOrder(projectId, orderType, appType,orderId,createTime, recipientInfo, reservationTenantInfo);
        BizReservationOrderDetail bizProductOrderDetail = buildOrderDetail(orderId,createTime,reservationTenantInfo.getReservationInfo());

        bizReservationOrderMapper.insertSelective(order);
        bizReservationOrderDetailMapper.insertSelective(bizProductOrderDetail);
        bizReservationOrderOperationRecordBiz.addOrderOperationRecord(order.getId(), OrderOperationType.CreateOrder);
        // 添加超时任务
        reservationOrderTimeOutJobBiz.addOrderPayTimeoutJob(orderId);
        // 创建账单信息
        String actualId = UUIDUtils.generateUuid();
        BigDecimal actualPrice = ObjectUtils.isEmpty(reservationTenantInfo.getTotalPrice())?BigDecimal.ZERO:reservationTenantInfo.getTotalPrice();
        BuyProductOutVo buyProductOutVo = new BuyProductOutVo();
        buyProductOutVo.setActualId(actualId);
        buyProductOutVo.setSubId(orderId);
        buyProductOutVo.setActualPrice(actualPrice.toString());
        buyProductOutVo.setTitle(order.getTitle());
        createOrderContext.setBuyProductOutVo(buyProductOutVo);
    }


    private BizReservationOrder buildOrder(String projectId,Integer orderType,int appType,String orderId,Date createTime,
                                       RecipientInfo recipientInfo,ReservationTenantInfo reservationTenantInfo){
        BizReservationOrder order = new BizReservationOrder();
        // 商品信息
        ReservationInfo reservationInfo = reservationTenantInfo.getReservationInfo();
        String busId = reservationInfo.getBusId();
        String orderCode = getOrderCode(projectId,reservationTenantInfo.getTenantId(),busId);
        order.setId(orderId);
        order.setOrderCode(orderCode);
        order.setTenantId(reservationTenantInfo.getTenantId());
        order.setProjectId(projectId);
        order.setUserId(BaseContextHandler.getUserID());
        order.setOrderType(orderType);
        order.setReservationTime(reservationTenantInfo.getReservationTime());
        order.setOrderStatus(AceDictionary.ORDER_STATUS_W_PAY);
        order.setRefundStatus(AceDictionary.ORDER_REFUND_STATUS_NONE);
        String desc = reservationInfo.getProductName();
        String title = "("+reservationInfo.getBusName()+")"+desc;
        order.setTitle(title);
        order.setDescription(desc);
        order.setAppType(appType);
        order.setCommentStatus(AceDictionary.PRODUCT_COMMENT_NONE);
        order.setActualPrice(reservationTenantInfo.getTotalPrice());
        order.setProductPrice(reservationTenantInfo.getProductPrice());
        // 服务时间
        order.setReservationTime(reservationTenantInfo.getReservationTime());
        order.setDiscountPrice(reservationTenantInfo.getDiscountPrice());

        InvoiceInfo invoiceInfo = reservationTenantInfo.getInvoiceInfo();
        order.setInvoiceType(invoiceInfo.getInvoiceType());
        order.setInvoiceName(invoiceInfo.getInvoiceName());
        order.setDutyCode(invoiceInfo.getDutyNum());

        order.setContactName(recipientInfo.getContactName());
        order.setContactTel(recipientInfo.getContactTel());
        order.setDeliveryAddr(recipientInfo.getAddr());

        order.setRemark(reservationTenantInfo.getRemark());

        order.setCreateBy(BaseContextHandler.getUserID());
        order.setCreateTime(createTime);
        order.setModifyBy(order.getCreateBy());
        order.setStatus(AceDictionary.DATA_STATUS_VALID);

        DiscountInfo discountInfo = reservationTenantInfo.getDiscountInfo();
        if(!ObjectUtils.isEmpty(discountInfo)){
            discountInfo.setOrderId(orderId);
        }
        return order;
    }


    private BizReservationOrderDetail buildOrderDetail(String orderId,Date createTime,ReservationInfo reservationInfo){
        BizReservationOrderDetail orderDetail = new BizReservationOrderDetail();
        orderDetail.setId(UUIDUtils.generateUuid());
        orderDetail.setOrderId(orderId);
        orderDetail.setProductId(reservationInfo.getProductId());
        orderDetail.setProductName(reservationInfo.getProductName());
        orderDetail.setSpecId(reservationInfo.getSpecId());
        orderDetail.setSpecName(reservationInfo.getSpecName());
        orderDetail.setSpecImg(reservationInfo.getSpecImage());
        orderDetail.setQuantity(reservationInfo.getQuantity());
        orderDetail.setSalesPrice(reservationInfo.getSalesPrice());
        orderDetail.setTotalPrice(reservationInfo.getSalesPrice().multiply(BigDecimal.valueOf(reservationInfo.getQuantity())));
        orderDetail.setUnit(reservationInfo.getUnit());

        orderDetail.setCreateBy(BaseContextHandler.getUserID());
        orderDetail.setCreateTime(createTime);
        orderDetail.setModifyBy(orderDetail.getCreateBy());
        orderDetail.setStatus(AceDictionary.DATA_STATUS_VALID);
        return  orderDetail;
    }


    private String getOrderCode(String projectId,String tenantId,String busId ){
        return generateOrderCodeBiz.generateOrderCode(projectId,tenantId,busId);
    }
}
