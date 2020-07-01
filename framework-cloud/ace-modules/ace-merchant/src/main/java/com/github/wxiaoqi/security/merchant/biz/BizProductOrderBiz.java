package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.logistics.OrderLogisticsVO;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.merchant.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.merchant.entity.*;
import com.github.wxiaoqi.security.merchant.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.merchant.mapper.BizProductOrderMapper;
import com.github.wxiaoqi.security.merchant.response.MerchantObjectResponse;
import com.github.wxiaoqi.security.merchant.vo.order.OrderQueryVO;
import com.github.wxiaoqi.security.merchant.vo.order.product.ProductOrderDetail;
import com.github.wxiaoqi.security.merchant.vo.order.product.ProductOrderDetailVO;
import com.github.wxiaoqi.security.merchant.vo.order.product.ProductOrderVO;
import com.github.wxiaoqi.security.merchant.vo.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 13:13:28
 */
@Slf4j
@Service
@Transactional
public class BizProductOrderBiz extends BusinessBiz<BizProductOrderMapper,BizProductOrder> {

    @Autowired
    private BizExpressCompanyBiz bizExpressCompanyBiz;

    @Autowired
    private BizOrderLogisticsBiz bizOrderLogisticsBiz;

    @Autowired
    private BizProductOrderDetailBiz bizProductOrderDetailBiz;

    @Autowired
    private BizOrderInvoiceBiz bizOrderInvoiceBiz;

    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;

    @Autowired
    private ProductOrderConfirmJobBiz productOrderConfirmJobBiz;

    @Resource
    private BaseTenantMapper baseTenantMapper;

    /**
     * 我的商品订单列表
     * @param productQueryVO
     * @return
     */
    public BaseResponse queryOrderListPage(OrderQueryVO productQueryVO){
        String tenantId = BaseContextHandler.getTenantID();
        if(StringUtils.isEmpty(tenantId)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_LOGIN);
        }
        // 订单状态
        Integer orderStatus = null;
        // 退款状态
        Integer refundStatus = null;
        // 评论状态
        Integer commentStatus = null;
        if(!ObjectUtils.isEmpty(productQueryVO.getOrderStatus())){
            if(AceDictionary.ORDER_STATUS_APPLY_REFUND.equals(productQueryVO.getOrderStatus())){
                refundStatus = AceDictionary.ORDER_REFUND_STATUS_APPLY;
            }else if (AceDictionary.ORDER_STATUS_W_COMMENT.equals(productQueryVO.getOrderStatus())){
                commentStatus = AceDictionary.PRODUCT_COMMENT_NONE;
                orderStatus = AceDictionary.ORDER_STATUS_COM;
                refundStatus = AceDictionary.ORDER_REFUND_STATUS_NONE;
            }else {
                orderStatus = productQueryVO.getOrderStatus();
                refundStatus = AceDictionary.ORDER_REFUND_STATUS_NONE;
            }
        }

        if (productQueryVO.getPage() < 1) {
            productQueryVO.setPage(1);
        }
        if (productQueryVO.getLimit() < 1) {
            productQueryVO.setLimit(10);
        }
        //分页
        int startIndex = (productQueryVO.getPage() - 1) * productQueryVO.getLimit();
        List<ProductOrderVO> productOrderList = this.mapper.queryOrderListPage(tenantId,orderStatus,refundStatus,commentStatus,productQueryVO.getKeyword(),startIndex,productQueryVO.getLimit());
        if(ObjectUtils.isEmpty(productOrderList)){
            productOrderList = new ArrayList<>();
        }
        int total = this.mapper.queryOrderListCount(tenantId,orderStatus,refundStatus,commentStatus,productQueryVO.getKeyword());
        return new TableResultResponse<>(total,productOrderList);
    }

    /**
     * 商品订单发货
     * @param orderLogisticsVO
     * @return
     */
    public ObjectRestResponse sendProduct(OrderLogisticsVO orderLogisticsVO){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();

        String tenantId = null;
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = baseTenantMapper.selectRoleTypeByUserId(BaseContextHandler.getUserID());
        }
        if(userInfo!=null){
            if("1".equals(userInfo.getTenantType()) || "2".equals(userInfo.getTenantType())) {
                //非平台用户需要传入公司Id
                tenantId = BaseContextHandler.getTenantID();
            }
        }else{
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("非法用户");
            return objectRestResponse;
        }

        // 订单是否存在
        BizProductOrder bizProductOrder = this.selectById(orderLogisticsVO.getOrderId());
        if(ObjectUtils.isEmpty(bizProductOrder)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }

        // 订单商品信息是否存在
        BizProductOrderDetail productOrderDetailUpdateStatus = new BizProductOrderDetail();
        productOrderDetailUpdateStatus.setOrderId(bizProductOrder.getId());
        productOrderDetailUpdateStatus.setTenantId(tenantId);
        productOrderDetailUpdateStatus.setStatus(AceDictionary.DATA_STATUS_VALID);
        List<BizProductOrderDetail> bizProductOrderDetails = bizProductOrderDetailBiz.selectList(productOrderDetailUpdateStatus);
        if(CollectionUtils.isEmpty(bizProductOrderDetails)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER_DETAIL);
        }


        for (BizProductOrderDetail bizProductOrderDetail: bizProductOrderDetails) {
            // 订单状态是不是待发货
            if(!AceDictionary.ORDER_STATUS_W_SEND.equals(bizProductOrderDetail.getDetailStatus())){
                return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_STATUS_ERROR);
            }

        }
         // 获取快递公司信息
        BizExpressCompany bizExpressCompany = bizExpressCompanyBiz.selectById(orderLogisticsVO.getExpressCompanyId());
        if(ObjectUtils.isEmpty(bizExpressCompany)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_EXPRESS_COMPANY);
        }
        BizOrderLogistics orderLogistics = new BizOrderLogistics();
        orderLogistics.setStatus(AceDictionary.DATA_STATUS_VALID);
        orderLogistics.setOrderId(orderLogisticsVO.getOrderId());
        List<BizOrderLogistics> list = bizOrderLogisticsBiz.selectList(orderLogistics);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(list)){
            return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_DELIVERY);
        }
        // 保存物流信息
        bizOrderLogisticsBiz.saveOrderLogistics(orderLogisticsVO,bizExpressCompany,bizProductOrderDetails);
        // 记录操作日志
        bizOrderOperationRecordBiz.addOrderOperationRecord(bizProductOrder.getId(),bizProductOrder.getParentId(), OrderOperationType.SendOrder,bizExpressCompany.getCompanyName(),orderLogisticsVO.getLogisticsNo());

        // 修改订单状态
        bizProductOrder.setOrderStatus(AceDictionary.ORDER_STATUS_W_SIGN);
        bizProductOrder.setModifyTime(DateTimeUtil.getLocalTime());
        bizProductOrder.setModifyBy(BaseContextHandler.getUserID());
        bizProductOrder.setSendTime(DateTimeUtil.getLocalTime());
        this.updateSelectiveById(bizProductOrder);
        // 修改订单详情状态
        BizProductOrderDetail updateProductOrderDetail = new BizProductOrderDetail();
        updateProductOrderDetail.setDetailStatus(AceDictionary.ORDER_STATUS_W_SIGN);
        updateProductOrderDetail.setOrderId(orderLogisticsVO.getOrderId());
        updateProductOrderDetail.setModifyBy(BaseContextHandler.getUserID());
        updateProductOrderDetail.setTenantId(tenantId);
        bizProductOrderDetailBiz.updateStatus(updateProductOrderDetail);
        // 添加确认收货任务
        productOrderConfirmJobBiz.addOrderConfirmJob(orderLogisticsVO.getOrderId());
        return objectRestResponse;
    }

    public ObjectRestResponse saveOrUpdateOrderLogistics(OrderLogisticsVO orderLogisticsVO){
        // 订单是否存在
        BizProductOrder bizProductOrder = this.selectById(orderLogisticsVO.getOrderId());
        if(ObjectUtils.isEmpty(bizProductOrder)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }
        // 订单商品信息是否存在
        BizProductOrderDetail productOrderDetailUpdateStatus = new BizProductOrderDetail();
        productOrderDetailUpdateStatus.setOrderId(bizProductOrder.getId());
        productOrderDetailUpdateStatus.setStatus(AceDictionary.DATA_STATUS_VALID);
        List<BizProductOrderDetail> bizProductOrderDetails = bizProductOrderDetailBiz.selectList(productOrderDetailUpdateStatus);
        if(CollectionUtils.isEmpty(bizProductOrderDetails)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER_DETAIL);
        }

        for (BizProductOrderDetail bizProductOrderDetail: bizProductOrderDetails) {
            // 订单状态是不是待签收
            if(!AceDictionary.ORDER_STATUS_W_SIGN.equals(bizProductOrderDetail.getDetailStatus())){
                return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_STATUS_ERROR);
            }

        }

        // 获取快递公司信息
        BizExpressCompany bizExpressCompany = bizExpressCompanyBiz.selectById(orderLogisticsVO.getExpressCompanyId());
        if(ObjectUtils.isEmpty(bizExpressCompany)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_EXPRESS_COMPANY);
        }
        // 保存物流信息
        bizOrderLogisticsBiz.saveOrUpdateOrderLogistics(orderLogisticsVO,bizExpressCompany,bizProductOrderDetails);
        // 记录操作日志
        bizOrderOperationRecordBiz.updateOrderOperationRecord(bizProductOrder.getId(),bizProductOrder.getParentId(), OrderOperationType.SendOrder,bizExpressCompany.getCompanyName(),orderLogisticsVO.getLogisticsNo());
        return ObjectRestResponse.ok();
    }

    /**
     * 查询商品订单详情
     * @param orderId
     * @return
     */
    public ObjectRestResponse queryOrderDetail(String orderId){
        String tenantId = BaseContextHandler.getTenantID();
        if(StringUtils.isEmpty(tenantId)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_LOGIN);
        }
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        ProductOrderDetailVO orderDetailVO = this.mapper.queryOrderDetail(orderId,tenantId);
        if(ObjectUtils.isEmpty(orderDetailVO)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }
        // 获取订单商品信息
        List<ProductOrderDetail> orderDetailList = this.mapper.queryOrderDetailList(orderId,tenantId);
        if(ObjectUtils.isEmpty(orderDetailList)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER_DETAIL);
        }
        orderDetailVO.setProductList(orderDetailList);
        BizOrderInvoice bizOrderInvoice = new BizOrderInvoice();
        bizOrderInvoice.setOrderId(orderId);
        bizOrderInvoice.setTenantId(tenantId);
        // 查询发票信息
        bizOrderInvoice = bizOrderInvoiceBiz.selectOne(bizOrderInvoice);
        if(!ObjectUtils.isEmpty(bizOrderInvoice)){
            orderDetailVO.setInvoiceType(bizOrderInvoice.getInvoiceType());
            orderDetailVO.setInvoiceName(bizOrderInvoice.getInvoiceName());
            orderDetailVO.setDutyCode(bizOrderInvoice.getDutyCode());
        }
        objectRestResponse.setData(orderDetailVO);
        return objectRestResponse;
    }

    /**
     * 确认收货
     */
    public ObjectRestResponse confirmOrder(String orderId) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        BizProductOrder bizProductOrder = this.selectById(orderId);
        if(ObjectUtils.isEmpty(bizProductOrder)){
            log.error("订单ID:{},超时确认收货失败，未找到订单信息",orderId);
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }
        if(AceDictionary.ORDER_STATUS_W_SIGN.equals(bizProductOrder.getOrderStatus()) && AceDictionary.ORDER_REFUND_STATUS_NONE.equals(bizProductOrder.getRefundStatus())){
            // 记录操作日志
            bizOrderOperationRecordBiz.addOrderOperationRecord(bizProductOrder.getId(),bizProductOrder.getParentId(), OrderOperationType.AutoSignOrder);
            // 修改订单详情状态
            // 修改订单详情状态
            BizProductOrderDetail updateProductOrderDetail = new BizProductOrderDetail();
            updateProductOrderDetail.setDetailStatus(AceDictionary.ORDER_STATUS_COM);
            updateProductOrderDetail.setOrderId(orderId);
            updateProductOrderDetail.setModifyBy("system");
            updateProductOrderDetail.setTenantId(bizProductOrder.getTenantId());
            bizProductOrderDetailBiz.updateStatus(updateProductOrderDetail);
            // 修改订单状态
            bizProductOrder.setOrderStatus(AceDictionary.ORDER_STATUS_COM);
            bizProductOrder.setModifyBy("system");
            bizProductOrder.setConfirmTime(DateTimeUtil.getLocalTime());
            this.mapper.updateByPrimaryKey(bizProductOrder);
            return objectRestResponse;
        }
        return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_STATUS_ERROR);
    }

}