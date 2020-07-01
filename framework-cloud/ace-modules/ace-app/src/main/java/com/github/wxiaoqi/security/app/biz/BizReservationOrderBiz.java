package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.DoOperateVo;
import com.github.wxiaoqi.security.api.vo.order.out.*;
import com.github.wxiaoqi.security.app.biz.order.ReservationOrderCreateBiz;
import com.github.wxiaoqi.security.app.biz.order.handler.ReservationOrderOperateHandler;
import com.github.wxiaoqi.security.app.buffer.ConfigureBuffer;
import com.github.wxiaoqi.security.app.entity.BizAccountBook;
import com.github.wxiaoqi.security.app.entity.BizPnsCall;
import com.github.wxiaoqi.security.app.entity.BizReservationOrder;
import com.github.wxiaoqi.security.app.entity.BizReservationOrderDetail;
import com.github.wxiaoqi.security.app.fegin.ToolFegin;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.reservation.dto.ReservationCommentVO;
import com.github.wxiaoqi.security.app.reservation.dto.ReservationInfoDTO;
import com.github.wxiaoqi.security.app.reservation.vo.*;
import com.github.wxiaoqi.security.app.vo.product.out.UserCommentVo;
import com.github.wxiaoqi.security.app.vo.reservation.out.ReservationOrderListVO;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.JacksonJsonUtil;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 预约服务订单表
 *
 * @author huangxl
 * @Date 2020-04-19 17:01:24
 */
@Service
@Transactional
public class BizReservationOrderBiz extends BusinessBiz<BizReservationOrderMapper,BizReservationOrder> {


    @Autowired
    private ReservationOrderCreateBiz reservationOrderCreateBiz;


    @Autowired
    private ReservationOrderOperateHandler reservationOrderOperateHandler;

    @Resource
    private ToolFegin toolFegin;

    @Resource
    private BizAccountBookMapper bizAccountBookMapper;

    @Resource
    private BizReservationOrderWaiterMapper bizReservationOrderWaiterMapper;

    @Autowired
    private BizReservationOrderOperationRecordBiz bizReservationOrderOperationRecordBiz;

    @Resource
    private BizReservationOrderCommentMapper bizReservationOrderCommentMapper;

    @Autowired
    private BizReservationOrderDetailBiz bizReservationOrderDetailBiz;

    @Resource
    private BaseAppServerUserMapper baseAppServerUserMapper;

    @Resource
    private BizPnsCallBiz bizPnsCallBiz;




    /**
     * 预约服务下单
     * @param buyProductInfo
     * @return
     */
    public ObjectRestResponse<BuyProductOutVo> buyCompanyProduct(BuyProductInfo buyProductInfo) {
        ObjectRestResponse<BuyProductOutVo> restResponse = new ObjectRestResponse<>();
        BuyProductOutVo order = reservationOrderCreateBiz.createOrder(buyProductInfo);
        restResponse.setData(order);
        return restResponse;

    }

    /**
     * 查询预约服务详情
     * @param id
     * @param userId
     * @return
     */
    public ReservationInfoDTO selectReservationInfo(String id, String userId){
        return this.mapper.selectReservationInfo(id,userId);
    }

    /**
     * 我的服务订单列表
     * @param searchSubOrderIn
     * @return
     */
    public ObjectRestResponse queryReservationOrderListPage(ReservationOrderQueryVO searchSubOrderIn){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String userId = BaseContextHandler.getUserID();
        Assert.hasLength(userId,"用户未登陆");

        // 订单状态
        Integer orderStatus = null;
        // 退款状态
        Integer refundStatus = null;
        // 评论状态
        Integer commentStatus = null;
        if(!ObjectUtils.isEmpty(searchSubOrderIn.getOrderStatus())){
            if(AceDictionary.ORDER_STATUS_APPLY_REFUND.equals(searchSubOrderIn.getOrderStatus())){
                refundStatus = AceDictionary.ORDER_REFUND_STATUS_APPLY;
            }else if (AceDictionary.ORDER_STATUS_W_COMMENT.equals(searchSubOrderIn.getOrderStatus())){
                commentStatus = AceDictionary.PRODUCT_COMMENT_NONE;
                refundStatus = AceDictionary.ORDER_REFUND_STATUS_NONE;
                orderStatus = AceDictionary.ORDER_STATUS_COM;
            }else {
                orderStatus = searchSubOrderIn.getOrderStatus();
                refundStatus = AceDictionary.ORDER_REFUND_STATUS_NONE;
            }
        }
        if (searchSubOrderIn.getPage() < 1) {
            searchSubOrderIn.setPage(1);
        }
        if (searchSubOrderIn.getLimit() < 1) {
            searchSubOrderIn.setLimit(10);
        }
        //分页
        int startIndex = (searchSubOrderIn.getPage() - 1) * searchSubOrderIn.getLimit();

        List<ReservationOrderListVO> reservationOrderListVOS = this.mapper.queryReservationOrderListPage(userId,orderStatus,refundStatus,commentStatus,startIndex,searchSubOrderIn.getLimit());
        if(ObjectUtils.isEmpty(reservationOrderListVOS)){
            reservationOrderListVOS = new ArrayList<>();
        }
        objectRestResponse.setData(reservationOrderListVOS);
        return objectRestResponse;
    }

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    public ObjectRestResponse queryReservationOrderDetail(String orderId) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        BizReservationOrder reservationOrder = this.selectById(orderId);
        String username = BaseContextHandler.getName();
        ReservationOrderInfoVo reservationOrderInfoVo = new ReservationOrderInfoVo();
        BeanUtils.copyProperties(reservationOrder,reservationOrderInfoVo);
        reservationOrderInfoVo.setUserName(username);
        if(!ObjectUtils.isEmpty(reservationOrder)){
            BizReservationOrderDetail orderDetail = new BizReservationOrderDetail();
            orderDetail.setOrderId(reservationOrder.getId());
            orderDetail =  bizReservationOrderDetailBiz.selectOne(orderDetail);
            reservationOrderInfoVo.setImgId(orderDetail.getSpecImg());
            reservationOrderInfoVo.setProductId(orderDetail.getProductId());
            reservationOrderInfoVo.setQuantity(orderDetail.getQuantity());
            reservationOrderInfoVo.setUnit(orderDetail.getUnit());

            if(StringUtils.isNotEmpty(reservationOrder.getBindId())){
                BizPnsCall bizPnsCall = new BizPnsCall();
                bizPnsCall.setBindId(reservationOrder.getBindId());
                bizPnsCall = bizPnsCallBiz.selectOne(bizPnsCall);
                if(!ObjectUtils.isEmpty(bizPnsCall)){
                    reservationOrderInfoVo.setTenantTel(bizPnsCall.getTelB());
                    reservationOrderInfoVo.setBindTel(bizPnsCall.getTelX());
                    reservationOrderInfoVo.setBindFlag(bizPnsCall.getBindingFlag());
                }
            }

        }

        BizAccountBook accountBook = bizAccountBookMapper.getPayAndRefundStatusBySubId(orderId);
        if(!ObjectUtils.isEmpty(accountBook)){
            reservationOrderInfoVo.setActualId(accountBook.getActualId());
        }

        ReservationOrderWaiterVO reservationOrderWaiterVO = bizReservationOrderWaiterMapper.queryWaiter(orderId);
        reservationOrderInfoVo.setReservationOrderWaiterVO(reservationOrderWaiterVO);

        objectRestResponse.setData(reservationOrderInfoVo);
        return objectRestResponse;
    }

    /**
     * 获取操作记录
     * @return
     */
    public ObjectRestResponse getOperation(String orderId){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<ReservationOrderOperationRecordVO> orderOperationRecordVOS = bizReservationOrderOperationRecordBiz.queryReservationOrderOperationRecord(orderId);
        if(CollectionUtils.isEmpty(orderOperationRecordVOS)){
            orderOperationRecordVOS = new ArrayList<>();
        }
        objectRestResponse.setData(orderOperationRecordVOS);
        return objectRestResponse;
    }

    /**
     * 支付成功回调
     * @param orderId
     * @return
     */
    public ObjectRestResponse paySuccess(String orderId) {
        return reservationOrderOperateHandler.paySuccess(orderId);
    }

    /**
     * 退款成功回调
     * @param orderId
     * @return
     */
    public ObjectRestResponse returnSuccess(String orderId) {
        return reservationOrderOperateHandler.returnSuccess(orderId);
    }

    /**
     * 订单完成后申请退款
     * @param orderId
     * @return
     */
    public ObjectRestResponse returnAudit(String orderId) {
        ObjectRestResponse response = new ObjectRestResponse();
        String tenantType = baseAppServerUserMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(tenantType) ||(!"3".equals(tenantType))){
            response.setStatus(505);
            response.setMessage("该用户没有权限！");
            return response;
        }
        return reservationOrderOperateHandler.returnAudit(orderId);
    }


    /**
     * 取消
     * @param orderId
     * @return
     */
    public ObjectRestResponse cancel(String orderId){
        return reservationOrderOperateHandler.cancel(orderId);
    }

    /**
     * 评论
     * @param reservationCommentVO
     * @return
     */
    public ObjectRestResponse doComment(ReservationCommentVO reservationCommentVO){
        if(StringUtils.isNotEmpty(reservationCommentVO.getImgIds())){
            ObjectRestResponse restResponse = toolFegin.moveAppUploadUrlPaths(reservationCommentVO.getImgIds(), DocPathConstant.ORDERWO);
            if(restResponse.getStatus()==200){
                reservationCommentVO.setImgIds(restResponse.getData()==null ? "" : (String)restResponse.getData());
            }
        }
        return reservationOrderOperateHandler.doComment(reservationCommentVO);
    }


    public ObjectRestResponse getReservationComment(String productId){
        List<UserCommentVo> commentVoList = new ArrayList<>();
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<ReservationOrderCommentVO> reservationOrderCommentVOS = bizReservationOrderCommentMapper.queryComment(productId);
        reservationOrderCommentVOS.forEach(reservationOrderCommentVO -> {
            UserCommentVo userCommentVo = new UserCommentVo();
            userCommentVo.setImgUrl(reservationOrderCommentVO.getImgIds());
            userCommentVo.setProfilePhoto(reservationOrderCommentVO.getProfilePhoto());
            userCommentVo.setAppraisalVal(reservationOrderCommentVO.getAppraisalVal());
            userCommentVo.setCreateTime(DateUtils.formatDateTime(reservationOrderCommentVO.getCreateTime()));
            userCommentVo.setDescription(reservationOrderCommentVO.getDescription());
            userCommentVo.setNickName(reservationOrderCommentVO.getName());
            commentVoList.add(userCommentVo);

        });
        objectRestResponse.setData(commentVoList);
        return objectRestResponse;
    }


    public ObjectRestResponse<SubDetailOutForWebVo> getSubDetailByWeb(String id) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        SubDetailOutForWebVo subDetailOutVo = new SubDetailOutForWebVo();
        //1.获取订单详情
        SubVo detail = this.mapper.queryRefundProductOrder(id);
        if (detail == null) {
            restResponse.setStatus(101);
            restResponse.setMessage("该ID获取不到详情");
            return restResponse;
        }
        //2.获取工单操作按钮
        List<OperateButton> operateButtonList = null;
        operateButtonList = ConfigureBuffer.getInstance().getServiceButtonByProcessId(detail.getProcessId());

        //判断是否可以转单条件(1.当前处理人和接单人为当前用户，2.工单状态为已接受处理中)
        String woStatusStr = detail.getWoStatus() == null ? "" : (String) detail.getWoStatus();
        String userId = BaseContextHandler.getUserID();
        if (com.github.wxiaoqi.security.common.util.StringUtils.isNotEmpty(woStatusStr) && "03".equals(woStatusStr) && userId.equals(detail.getHandleByUserId())) {
            detail.setIsTurn("1");
        } else {
            detail.setIsTurn("0");
        }
        if (operateButtonList == null || operateButtonList.size() == 0) {
            operateButtonList = new ArrayList<>();
        }
        subDetailOutVo.setOperateButtonList(operateButtonList);

        //2.获取订单产品信息
        List<SubProductInfo> subProductInfoList = this.mapper.queryRefundProductOrderInfo(id);
        if (subProductInfoList == null || subProductInfoList.size() == 0) {
            subProductInfoList = new ArrayList<>();
        }
        detail.setSubProductInfoList(subProductInfoList);
        subDetailOutVo.setDetail(detail);

        //4.获取操作流水日志
        List<TransactionLogVo> transactionLogList = this.mapper.queryRefundProductOrderOperation(id);
        if (transactionLogList == null && transactionLogList.size() == 0) {
            transactionLogList = new ArrayList<>();
        }
        subDetailOutVo.setTransactionLogList(transactionLogList);

        //判断能否修改订单 PRD201911260008
        boolean canModify = false;

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(subDetailOutVo);
        JSONObject detailJsonObject = jsonObject.getJSONObject("detail");
        detailJsonObject.put("canModify", 0);

        restResponse.data(jsonObject);
        return restResponse;
    }
}