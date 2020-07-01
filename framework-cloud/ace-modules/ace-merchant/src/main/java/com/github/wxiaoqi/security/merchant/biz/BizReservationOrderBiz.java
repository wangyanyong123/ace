package com.github.wxiaoqi.security.merchant.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.pns.in.AXBBindingDTO;
import com.github.wxiaoqi.security.api.vo.pns.out.AXBResult;
import com.github.wxiaoqi.security.api.vo.waiter.OrderWaiterVO;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.merchant.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.merchant.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.merchant.entity.BizReservationOrder;
import com.github.wxiaoqi.security.merchant.entity.BizReservationOrderWaiter;
import com.github.wxiaoqi.security.merchant.fegin.BizPnsCallFeign;
import com.github.wxiaoqi.security.merchant.mapper.BaseTenantMapper;
import com.github.wxiaoqi.security.merchant.mapper.BizReservationOrderMapper;
import com.github.wxiaoqi.security.merchant.response.MerchantObjectResponse;
import com.github.wxiaoqi.security.merchant.util.RedisUtil;
import com.github.wxiaoqi.security.merchant.vo.order.OrderQueryVO;
import com.github.wxiaoqi.security.merchant.vo.order.reservation.ReservationOrderDetailVO;
import com.github.wxiaoqi.security.merchant.vo.order.reservation.ReservationOrderVO;
import com.github.wxiaoqi.security.merchant.vo.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预约服务订单表
 *
 * @author wangyanyong
 * @Date 2020-04-24 13:13:42
 */
@Slf4j
@Service
@Transactional
public class BizReservationOrderBiz extends BusinessBiz<BizReservationOrderMapper,BizReservationOrder> {


    @Autowired
    private BizReservationOrderWaiterBiz bizReservationOrderWaiterBiz;

    @Autowired
    private BizReservationOrderDetailBiz bizReservationOrderDetailBiz;

    @Autowired
    private BizReservationOrderOperationRecordBiz bizReservationOrderOperationRecordBiz;

    @Resource
    private BaseTenantMapper baseTenantMapper;

    @Resource
    private BizPnsCallFeign bizPnsCallFeign;

    @Autowired
    private BaseAppServerUserBiz baseAppServerUserBiz;

    @Value("${pns.data.type}")
    private String customer;

    /**
     * 我的服务订单列表
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

        List<ReservationOrderVO> productOrderList = this.mapper.queryOrderListPage(tenantId,orderStatus,refundStatus,commentStatus,productQueryVO.getKeyword(),startIndex,productQueryVO.getLimit());
        if(ObjectUtils.isEmpty(productOrderList)){
            productOrderList = new ArrayList<>();
        }
        int total = this.mapper.queryOrderListCount(tenantId,orderStatus,refundStatus,commentStatus,productQueryVO.getKeyword());
        return new TableResultResponse<>(total,productOrderList);
    }

    /**
     * 分配人员
     * @param orderWaiterVO
     * @return
     */
    public ObjectRestResponse assignWaiter(OrderWaiterVO orderWaiterVO){
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
        BaseAppServerUser baseAppServerUser = baseAppServerUserBiz.selectById(BaseContextHandler.getUserID());
        if(ObjectUtils.isEmpty(baseAppServerUser)){
            objectRestResponse.setStatus(101);
            objectRestResponse.setMessage("非法用户");
            return objectRestResponse;
        }
        // 订单是否存在
        BizReservationOrder bizReservationOrder = this.selectById(orderWaiterVO.getOrderId());
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }


        // 订单状态是不是待发受理
        if(!AceDictionary.RESERVATION_ORDER_STATUS_ACCEPT.equals(bizReservationOrder.getOrderStatus())){
            return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_STATUS_ERROR);
        }

        // 保存分配人员
        BizReservationOrderWaiter reservationOrderWaiter = new BizReservationOrderWaiter();
        reservationOrderWaiter.setOrderId(orderWaiterVO.getOrderId());
        reservationOrderWaiter.setStatus(AceDictionary.DATA_STATUS_VALID);
        List<BizReservationOrderWaiter> list = bizReservationOrderWaiterBiz.selectList(reservationOrderWaiter);
        if(CollectionUtils.isNotEmpty(list)){
            return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_HANDLE);
        }
        bizReservationOrderWaiterBiz.saveWaiter(orderWaiterVO);

        // 客户、商户号码绑定
        AXBBindingDTO axbBinding  = new AXBBindingDTO();
        axbBinding.setId(orderWaiterVO.getOrderId());
        axbBinding.setTelA(bizReservationOrder.getContactTel());
        axbBinding.setTelB(baseAppServerUser.getMobilePhone());
        axbBinding.setCustomer(customer);
        AXBResult axbResult = bizPnsCallFeign.axbBindingInner(axbBinding);
        if(ObjectUtils.isEmpty(axbResult) || !axbResult.isSuccess()){
            return MerchantObjectResponse.error(ResponseCodeEnum.MOBILE_PHONE_BIND_FAIL);
        }
        bizReservationOrder.setBindId(axbResult.getData().getBindId());
        // 修改订单状态
        bizReservationOrder.setOrderStatus(AceDictionary.RESERVATION_ORDER_STATUS_TO);
        bizReservationOrder.setModifyTime(DateTimeUtil.getLocalTime());
        bizReservationOrder.setModifyBy(BaseContextHandler.getUserID());
        this.updateSelectiveById(bizReservationOrder);

        // 记录操作日志
        bizReservationOrderOperationRecordBiz.addOrderOperationRecord(orderWaiterVO.getOrderId(), OrderOperationType.Accepted,orderWaiterVO.getWaiterName(), DateUtils.formatDateTime(bizReservationOrder.getReservationTime(),DateUtils.DATETIME_HHMM_FORMAT),orderWaiterVO.getWaiterTel());
        return objectRestResponse;
    }


    /**
     * 更新分配人员
     * @param orderWaiterVO
     * @return
     */
    public ObjectRestResponse saveOrUpdateWaiter(OrderWaiterVO orderWaiterVO){
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
        BizReservationOrder bizReservationOrder = this.selectById(orderWaiterVO.getOrderId());
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }


        // 订单状态是不是待上门
        if(!AceDictionary.RESERVATION_ORDER_STATUS_TO.equals(bizReservationOrder.getOrderStatus())){
            return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_STATUS_ERROR);
        }

        // 保存分配人员
        bizReservationOrderWaiterBiz.saveOrUpdateWaiter(orderWaiterVO);

        // 记录操作日志
        bizReservationOrderOperationRecordBiz.updateOrderOperationRecord(orderWaiterVO.getOrderId(), OrderOperationType.Accepted,orderWaiterVO.getWaiterName(), DateUtils.formatDateTime(bizReservationOrder.getReservationTime(),DateUtils.DATETIME_HHMM_FORMAT),orderWaiterVO.getWaiterTel());

        return objectRestResponse;
    }

    /**
     * 工作台 统计接口
     * @return
     */
    public ObjectRestResponse orderTotal(){
        return bizReservationOrderDetailBiz.orderTotal();
    }

    /**
     * 预约服务订单详情
     * @param orderId
     * @return
     */
    public ObjectRestResponse queryOrderDetail(String orderId){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        // 获取商户登录信息
        String tenantId = BaseContextHandler.getTenantID();
        if(StringUtils.isEmpty(tenantId)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_LOGIN);
        }
        ReservationOrderDetailVO orderDetailVO = this.mapper.queryOrderDetail(orderId,tenantId);
        if(ObjectUtils.isEmpty(orderDetailVO)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }
        if(StringUtils.isEmpty(orderDetailVO.getRemark())){
            orderDetailVO.setRemark("");
        }
        objectRestResponse.setData(orderDetailVO);
        return objectRestResponse;
    }


    /**
     * 完成
     * @param orderId
     * @return
     */
    public ObjectRestResponse doFinish(String orderId){
        ObjectRestResponse objectRestResponse= new ObjectRestResponse();
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
        BizReservationOrder bizReservationOrder = this.selectById(orderId);
        if(ObjectUtils.isEmpty(bizReservationOrder)){
            return MerchantObjectResponse.error(ResponseCodeEnum.NOT_FOUND_ORDER);
        }

        if(!AceDictionary.RESERVATION_ORDER_STATUS_TO.equals(bizReservationOrder.getOrderStatus())){
            return MerchantObjectResponse.error(ResponseCodeEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        bizReservationOrder.setOrderStatus(AceDictionary.ORDER_STATUS_COM);
        bizReservationOrder.setModifyTime(DateTimeUtil.getLocalTime());
        bizReservationOrder.setConfirmTime(DateTimeUtil.getLocalTime());
        bizReservationOrder.setModifyBy(BaseContextHandler.getUserID());
        this.updateSelectiveById(bizReservationOrder);

        // 记录操作日志
        bizReservationOrderOperationRecordBiz.addOrderOperationRecord(orderId, OrderOperationType.OverService);

        // 解绑商户、客户之间的绑定关系
        AXBResult axbResult = bizPnsCallFeign.axbUnbindingInner(bizReservationOrder.getBindId());
        if(ObjectUtils.isEmpty(axbResult) || !axbResult.isSuccess()){
            bizPnsCallFeign.addBindTimeoutJob((bizReservationOrder.getBindId()));
        }
        return objectRestResponse;
    }


}