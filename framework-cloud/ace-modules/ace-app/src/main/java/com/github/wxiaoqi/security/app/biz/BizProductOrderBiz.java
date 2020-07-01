package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.order.in.BuyProductInfo;
import com.github.wxiaoqi.security.api.vo.order.in.PayOrderFinishIn;
import com.github.wxiaoqi.security.api.vo.order.out.*;
import com.github.wxiaoqi.security.app.biz.order.ProductOrderOperateBiz;
import com.github.wxiaoqi.security.app.buffer.ConfigureBuffer;
import com.github.wxiaoqi.security.app.entity.BizAccountBook;
import com.github.wxiaoqi.security.app.entity.BizProduct;
import com.github.wxiaoqi.security.app.entity.BizProductOrder;
import com.github.wxiaoqi.security.app.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.app.mapper.BizAccountBookMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductOrderMapper;
import com.github.wxiaoqi.security.app.vo.order.in.QueryOrderListIn;
import com.github.wxiaoqi.security.app.vo.order.out.*;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.OrderOperationType;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.DateUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品订单表
 *
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@Service
public class BizProductOrderBiz extends BusinessBiz<BizProductOrderMapper,BizProductOrder> {

    @Autowired
    private BizOrderOperationRecordBiz bizOrderOperationRecordBiz;
    @Resource
    private BizProductOrderMapper bizProductOrderMapper;
    @Resource
    private BizProductMapper bizProductMapper;
    @Resource
    private BizAccountBookMapper bizAccountBookMapper;
    @Autowired
    private BizProductOrderDetailBiz bizProductOrderDetailBiz;
    @Autowired
    private BizOrderLogisticsBiz bizOrderLogisticsBiz;
    @Autowired
    private ProductOrderOperateBiz productOrderOperateBiz;
    @Resource
    private BaseAppServerUserMapper baseAppServerUserMapper;


    public List<ProductOrderListVo> findOrderList(QueryOrderListIn queryOrderListIn) {
        queryOrderListIn.check();
        return bizProductOrderMapper.selectOrderList(queryOrderListIn);
    }

    public ProductOrderInfoVo findOrderInfo(String orderId) {
        BizProductOrder productOrder = this.selectById(orderId);
        String username = BaseContextHandler.getName();
        ProductOrderInfoVo productOrderInfoVo = new ProductOrderInfoVo();
        BeanUtils.copyProperties(productOrder,productOrderInfoVo);
        productOrderInfoVo.setUserName(username);

        if(AceDictionary.ORDER_STATUS_W_PAY.equals(productOrder.getOrderStatus())){
            BizAccountBook accountBook = bizAccountBookMapper.getPayAndRefundStatusBySubId(orderId);
            productOrderInfoVo.setActualId(accountBook.getActualId());
        }

        List<ProductOrderTenantInfo> tenantInfoList = bizProductOrderDetailBiz.findProductOrderTenantInfoList(orderId);
        productOrderInfoVo.setTenantList(tenantInfoList);

        BigDecimal expressPrice = BigDecimal.ZERO;
        BigDecimal discountPrice = BigDecimal.ZERO;
        BigDecimal productPrice = BigDecimal.ZERO;
        int quantity = 0;
        for (ProductOrderTenantInfo productOrderTenantInfo : tenantInfoList) {
            expressPrice= expressPrice.add(productOrderTenantInfo.getExpressPrice());
            discountPrice= discountPrice.add(productOrderTenantInfo.getDiscountPrice());
            productPrice= productPrice.add(productOrderTenantInfo.getProductPrice());
            quantity +=productOrderTenantInfo.getQuantity();
        }
        productOrderInfoVo.setExpressPrice(expressPrice);
        productOrderInfoVo.setDiscountPrice(discountPrice);
        productOrderInfoVo.setProductPrice(productPrice);
        productOrderInfoVo.setQuantity(quantity);
        productOrderInfoVo.setActualCost(productPrice.add(expressPrice).subtract(discountPrice));
        if(productOrder.getSendTime() != null){
            List<OrderLogisticsInfo> logisticsInfoList = bizOrderLogisticsBiz.findLogisticsInfoByOrderId(orderId);
            productOrderInfoVo.setLogisticsList(logisticsInfoList);
        }

        return productOrderInfoVo;
    }

    public ObjectRestResponse<BuyProductOutVo> buyCompanyProduct(BuyProductInfo buyProductInfo) {
        return productOrderOperateBiz.createOrder(buyProductInfo);
    }

    public ObjectRestResponse doPayOrderFinish(PayOrderFinishIn payOrderFinishIn) {
        return productOrderOperateBiz.paySuccess(payOrderFinishIn);
    }

    public ObjectRestResponse finishGroupProduct(String productId) {

        BizProduct bizProduct = new BizProduct();
        //更新成团标识为已成团
        bizProduct.setId(productId);
        bizProduct.setIsGroupFlag("1");
        bizProduct.setModifyTime(new Date());
        bizProduct.setModifyBy(BaseContextHandler.getUserID());
        bizProductMapper.updateByPrimaryKeySelective(bizProduct);
        List<OrderIdResult> waitingCompleteList = bizProductOrderMapper.selectOrderIdListGroupWaitingComplete(bizProduct.getId());
        waitingCompleteList.parallelStream().forEach(this::finishGroupProductByOrderId);
        return  ObjectRestResponse.ok();

    }

    public void finishGroupProductByOrderId(OrderIdResult orderIdResult){
        int groupFinish = bizProductOrderMapper.updateOrderStatusByGroupComplete(orderIdResult.getOrderId(), "groupFinish");
        if(groupFinish > 0){
            bizOrderOperationRecordBiz.addRecordByOrderIdResult(orderIdResult, OrderOperationType.GroupComplete,"");
        }
    }

    public int updateInValid(String orderId, String modifyBy) {
      return  this.mapper.updateInValid(orderId,modifyBy, DateTimeUtil.getLocalTime());
    }

    public ObjectRestResponse cancelOrder(String orderId) {
        return productOrderOperateBiz.cancelOrder(orderId);
    }


    public ObjectRestResponse signOrder(String orderId) {
        return productOrderOperateBiz.signOrder(orderId);
    }

    public ObjectRestResponse applyRefund(String orderId, String remark) {
        return productOrderOperateBiz.applyRefund(orderId,remark);
    }

    public int getPurchasedCount(String productId, String userId) {

        return bizProductOrderMapper.getPurchasedCount(productId,userId);
    }

    /**
     * 订单完成申请退款
     */
    public ObjectRestResponse refundAudit(String orderId, String remark) {
        ObjectRestResponse response = new ObjectRestResponse();
        String tenantType = baseAppServerUserMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(StringUtils.isEmpty(tenantType) ||(!"3".equals(tenantType))){
            response.setStatus(505);
            response.setMessage("该用户没有权限！");
            return response;
        }
        return productOrderOperateBiz.refundAudit(orderId,remark);
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
        if (StringUtils.isNotEmpty(woStatusStr) && "03".equals(woStatusStr) && userId.equals(detail.getHandleByUserId())) {
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