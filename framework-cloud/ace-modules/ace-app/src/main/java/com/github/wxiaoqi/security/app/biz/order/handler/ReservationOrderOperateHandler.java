package com.github.wxiaoqi.security.app.biz.order.handler;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.ApplyRefundParam;
import com.github.wxiaoqi.security.api.vo.pns.out.AXBResult;
import com.github.wxiaoqi.security.api.vo.store.reservation.ReservationStore;
import com.github.wxiaoqi.security.app.axb.service.AXBService;
import com.github.wxiaoqi.security.app.biz.*;
import com.github.wxiaoqi.security.app.entity.*;
import com.github.wxiaoqi.security.app.fegin.StoreReservationFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.reservation.dto.ReservationCommentVO;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 处理商品订单支付成功业务逻辑
 *
 * @author: guohao
 * @create: 2020-04-20 11:32
 **/
@Slf4j
@Component
@Transactional
public class ReservationOrderOperateHandler {

    @Resource
    private BizProductMapper bizProductMapper;
    @Resource
    private BizReservationOrderDiscountMapper bizReservationOrderDiscountMapper;
    @Resource
    private BizReservationOrderMapper bizReservationOrderMapper;
    @Resource
    private BizCouponUseMapper bizCouponUseMapper;
    @Resource
    private BizReservationOrderBiz bizReservationOrderBiz;
    @Resource
    private BizReservationOrderDetailBiz bizReservationOrderDetailBiz;
    @Resource
    private BizRefundAuditNewBiz bizRefundAuditNewBiz;
    @Resource
    private BizReservationOrderOperationRecordBiz bizReservationOrderOperationRecordBiz;
    @Resource
    private BizReservationOrderCommentBiz bizReservationOrderCommentBiz;
    @Resource
    private StoreReservationFegin storeReservationFegin;

    @Resource
    private BizAccountBookMapper bizAccountBookMapper;

    @Resource
    private AXBService axbService;

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    public ObjectRestResponse cancel(String orderId){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        // 查询订单
        BizReservationOrder bizReservationOrder = bizReservationOrderBiz.selectById(orderId);
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            log.error("服务订单取消，订单不存在，订单ID：{}",orderId);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("不存在该订单ID!");
            return objectRestResponse;
        }
        // 订单状态
        Integer orderStatus = bizReservationOrder.getOrderStatus();
        // 校验订单状态
        boolean flag = AceDictionary.ORDER_STATUS_W_PAY.equals(orderStatus)
                || AceDictionary.RESERVATION_ORDER_STATUS_ACCEPT.equals(orderStatus)
                || AceDictionary.RESERVATION_ORDER_STATUS_TO.equals(orderStatus);
        if(!flag){
            log.error("服务订单取消，订单状态异常，订单ID：{}",orderId);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单状态异常!");
            return objectRestResponse;
        }

        // 待付款 直接取消
        if(bizReservationOrder.getOrderStatus().equals(AceDictionary.ORDER_STATUS_W_PAY)){
            objectRestResponse = this.concelHandle(bizReservationOrder,Boolean.FALSE);
            if(objectRestResponse.success()){
                // 更新订单状态 添加操作日志
                updateStatus(orderId,OrderOperationType.CancelOrderUnPay,AceDictionary.ORDER_STATUS_CAN,null,null);
            }
        }else{
            // 预约服务时间距离当前大于12小时
            if(bizReservationOrder.getReservationTime().getTime() > DateUtils.addHours(new Date(),12).getTime()){
                // 更新订单状态 添加操作日志
                updateStatus(orderId,OrderOperationType.CancelOrderUnSend,AceDictionary.ORDER_STATUS_CAN,AceDictionary.ORDER_REFUND_STATUS_APPLY,null);
                // 调取退款申请
                objectRestResponse = this.doApplyRefund(bizReservationOrder,Boolean.FALSE);
            }else{
                // 更新订单状态 添加操作日志
                updateStatus(orderId,OrderOperationType.CancelOrderUnSend,null,AceDictionary.ORDER_REFUND_STATUS_APPLY,null);
                // 调取退款审批
                objectRestResponse = this.doApplyRefund(bizReservationOrder,Boolean.TRUE);
            }
        }
        return objectRestResponse;
    }

    /**
     * 退款成功回调
     * @param orderId
     * @return
     */
    public ObjectRestResponse returnSuccess(String orderId){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        // 查询订单
        BizReservationOrder bizReservationOrder = bizReservationOrderBiz.selectById(orderId);
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            log.error("服务订单退款成功回调，订单不存在，订单ID：{}",orderId);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("不存在该订单ID!");
            return objectRestResponse;
        }
        // 退款状态
        Integer returnStatus = bizReservationOrder.getRefundStatus();

        // 校验订单状态
        if(!AceDictionary.ORDER_REFUND_STATUS_APPLY.equals(returnStatus)){
            log.error("服务订单退款成功回调：状态：{}",returnStatus);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单状态有误");
            return objectRestResponse;
        }
        // 归还库存
        objectRestResponse = this.concelHandle(bizReservationOrder,Boolean.TRUE);
        if(objectRestResponse.success()){
            // 更新订单状态 添加操作日志
            updateStatus(orderId,OrderOperationType.RefundSuccess,AceDictionary.ORDER_STATUS_CAN,AceDictionary.ORDER_REFUND_STATUS_COM,null);
        }
        return objectRestResponse;
    }


    /**
     * 退款失败回调（驳回）
     * @param orderId
     * @return
     */
    public ObjectRestResponse returnFail(String orderId){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        // 查询订单
        BizReservationOrder bizReservationOrder = bizReservationOrderBiz.selectById(orderId);
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            log.error("服务订单退款失败回调，订单不存在，订单ID：{}",orderId);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("不存在该订单ID!");
            return objectRestResponse;
        }
        // 退款状态
        Integer returnStatus = bizReservationOrder.getRefundStatus();

        // 校验订单状态
        if(!AceDictionary.ORDER_REFUND_STATUS_APPLY.equals(returnStatus)){
            log.error("服务订单退款成功回调：状态：{}",returnStatus);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单状态有误");
            return objectRestResponse;
        }
        if(objectRestResponse.success()){
            // 更新订单状态 添加操作日志
            updateStatus(orderId,OrderOperationType.CancelReject,null,AceDictionary.ORDER_REFUND_STATUS_NONE,null);
        }
        return objectRestResponse;
    }

    /**
     * 付款成功回调
     * @param orderId
     * @return
     */
    public ObjectRestResponse paySuccess(String orderId){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        // 查询订单
        BizReservationOrder bizReservationOrder = bizReservationOrderBiz.selectById(orderId);
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("不存在该订单ID!");
            return objectRestResponse;
        }
        // 订单状态
        Integer orderStatus = bizReservationOrder.getOrderStatus();
        // 校验订单状态
        boolean flag = AceDictionary.ORDER_STATUS_W_PAY.equals(orderStatus);
        if(!flag){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单状态有误");
            return objectRestResponse;
        }

        BizReservationOrderDetail reservationOrderDetail = new BizReservationOrderDetail();
        reservationOrderDetail.setOrderId(orderId);
        reservationOrderDetail.setStatus(AceDictionary.DATA_STATUS_VALID);
        reservationOrderDetail = bizReservationOrderDetailBiz.selectOne(reservationOrderDetail);
        // 支付成功更新销售量
        bizProductMapper.udpateReservationSalesById(reservationOrderDetail.getProductId(),reservationOrderDetail.getQuantity());

        // 更新订单状态 添加操作日志
        updateStatus(orderId,OrderOperationType.PaySuccess,AceDictionary.RESERVATION_ORDER_STATUS_ACCEPT,null,null);
        return objectRestResponse;
    }

    /**
     * 添加评论
     * @param reservationCommentVO
     * @return
     */
    public ObjectRestResponse doComment(ReservationCommentVO reservationCommentVO){

        ObjectRestResponse objectRestResponse= new ObjectRestResponse();

        BizReservationOrder order = bizReservationOrderBiz.selectById(reservationCommentVO.getOrderId());
        if(ObjectUtils.isEmpty(order)){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单信息有误");
            return objectRestResponse;
        }
        if(!AceDictionary.ORDER_STATUS_COM.equals(order.getOrderStatus())){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单状态有误");
            return objectRestResponse;
        }
        BizReservationOrderComment bizReservationOrderComment = new BizReservationOrderComment();
        BeanUtils.copyProperties(reservationCommentVO,bizReservationOrderComment);
        bizReservationOrderComment.setCreateBy(BaseContextHandler.getUserID());
        bizReservationOrderComment.setId(UUIDUtils.generateUuid());
        bizReservationOrderComment.setCreateName(BaseContextHandler.getName());
        bizReservationOrderComment.setCreateTime(new Date());
        bizReservationOrderComment.setOrderId(reservationCommentVO.getOrderId());
        bizReservationOrderComment.setStatus(Boolean.TRUE);
        bizReservationOrderCommentBiz.insertSelective(bizReservationOrderComment);
        this.updateStatus(reservationCommentVO.getOrderId(), OrderOperationType.CommentOrder,null,null,AceDictionary.PRODUCT_COMMENT_COM,reservationCommentVO.getAppraisalVal().toString(),reservationCommentVO.getDescription());
        return objectRestResponse;
    }


    /**
     * 订单完成申请退款
     * @param orderId
     * @return
     */
    public ObjectRestResponse returnAudit(String orderId){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        // 查询订单
        BizReservationOrder bizReservationOrder = bizReservationOrderBiz.selectById(orderId);
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            log.error("服务订单取消，订单不存在，订单ID：{}",orderId);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("不存在该订单ID!");
            return objectRestResponse;
        }

        //订单已退款/退款进行中 不能申请
        if(!AceDictionary.ORDER_REFUND_STATUS_NONE.equals(bizReservationOrder.getRefundStatus())){
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单退款状态异常!");
            return objectRestResponse;
        }
        // 订单状态
        Integer orderStatus = bizReservationOrder.getOrderStatus();
        // 校验订单状态
        if(!AceDictionary.ORDER_STATUS_COM.equals(orderStatus)){
            log.error("服务订单取消，订单状态异常，订单ID：{}",orderId);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("订单状态异常!");
            return objectRestResponse;
        }

        // 更新订单状态 添加操作日志
        updateStatus(orderId,OrderOperationType.CancelOrderUnSend,null,AceDictionary.ORDER_REFUND_STATUS_APPLY,null);
        // 调取退款审批
        objectRestResponse = this.doApplyRefund(bizReservationOrder,Boolean.TRUE);

        return objectRestResponse;
    }





    // 取消时  增加库存 归还优惠券
    private ObjectRestResponse concelHandle(BizReservationOrder bizReservationOrder,boolean isSales){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        //查询订单查询销售新
        BizReservationOrderDetail reservationOrderDetail = new BizReservationOrderDetail();
        reservationOrderDetail.setOrderId(bizReservationOrder.getId());
        reservationOrderDetail = bizReservationOrderDetailBiz.selectOne(reservationOrderDetail);

        // 解绑商户、客户之间的绑定关系
        if(StringUtils.isNotEmpty(bizReservationOrder.getBindId())){
            AXBResult axbResult = axbService.axbUnbindingInner(bizReservationOrder.getBindId());
            if(ObjectUtils.isEmpty(axbResult) || !axbResult.isSuccess()){
                axbService.addBindTimeoutJob((bizReservationOrder.getBindId()));
            }
        }

        // 服务订单预约服务时间大于等于当天则归还库存
        int timeSlot  = DateUtils.judgeTheDate(DateUtils.formatDateTime(bizReservationOrder.getReservationTime(),DateUtils.NORMAL_DATE_FORMAT));
        if(timeSlot > 0){
            ReservationStore reservationStore = new ReservationStore();
            reservationStore.setAccessNum(reservationOrderDetail.getQuantity());
            reservationStore.setCreateBy(BaseContextHandler.getUserID());
            reservationStore.setOrderId(reservationOrderDetail.getOrderId());
            reservationStore.setSpecId(reservationOrderDetail.getSpecId());
            reservationStore.setReservationTime(bizReservationOrder.getReservationTime());
            objectRestResponse = storeReservationFegin.cancel(reservationStore);

        }

        // 扣减销售 和归还优惠券
        if(objectRestResponse.success()){
            // 减销售  产品确认销售只加不减
//            if(isSales){
//                bizProductMapper.updateResSalesNum(reservationOrderDetail.getProductId(),reservationOrderDetail.getQuantity()+"");
//            }

            if(!isSales){
                //取消订单，回撤优惠券状态
                String couponId = bizReservationOrderDiscountMapper.querRalationIdByOrderId(bizReservationOrder.getId());
                if(StringUtils.isNotEmpty(couponId)){
                    BizCouponUse bizCouponUse = new BizCouponUse();
                    bizCouponUse.setId(couponId);
                    bizCouponUse.setUseStatus("1");
                    bizCouponUse.setOrderId("");
                    bizCouponUseMapper.updateByPrimaryKeySelective(bizCouponUse);
                }
            }
        }
        return objectRestResponse;
    }


    // 申请退款 isTimeOut：false-直接退款，true-后台审核；
    private ObjectRestResponse doApplyRefund(BizReservationOrder order,Boolean isTimeOut){
        ApplyRefundParam applyRefundParam = new ApplyRefundParam();
        applyRefundParam.setSubId(order.getId());
        applyRefundParam.setBusType(AceDictionary.BUS_TYPE_RESERVE_ORDER);
        applyRefundParam.setUserType(AceDictionary.USER_TYPE_BUYER);
        applyRefundParam.setSend(isTimeOut);
        applyRefundParam.setUserId(order.getUserId());
        applyRefundParam.setSubCode(order.getOrderCode());
        applyRefundParam.setSubTitle(order.getTitle());
        applyRefundParam.setTenantId(order.getTenantId());
        applyRefundParam.setApplyPrice(order.getActualPrice());
        applyRefundParam.setSubCreateTime(order.getCreateTime());
        applyRefundParam.setProjectId(order.getProjectId());
        BizAccountBook accountBook = bizAccountBookMapper.getPayAndRefundStatusBySubId(order.getId());
        Assert.notNull(accountBook,"未找到该单据的支付记录");
        applyRefundParam.setActualId(accountBook.getActualId());
        return bizRefundAuditNewBiz.applyRefund(applyRefundParam);
    }

    // 更新订单状态 添加操作日志
    private void updateStatus(String orderId,OrderOperationType orderOperationType,Integer orderStatus,Integer RefundStatus,Integer commentStatus,String...remark){
        // 更新订单状态
        bizReservationOrderMapper.updateStatus(orderId,orderStatus,RefundStatus,commentStatus,BaseContextHandler.getUserID());

        // 修改操作日志
        bizReservationOrderOperationRecordBiz.addOrderOperationRecord(orderId,orderOperationType,remark);
    }
}
