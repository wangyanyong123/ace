package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultOrderList implements Serializable {

    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "客户姓名")
    private String customerName;
    @ApiModelProperty(value = "客户联系方式")
    private String customerTel;
    @ApiModelProperty(value = "客户地址")
    private String customerAddress;
    @ApiModelProperty(value = "支付类型(1-支付宝,2-微信)")
    private String payMent;
    @ApiModelProperty(value = "支付价格")
    private String actualPrice;
    @ApiModelProperty(value = "购买数量")
    private String buyNum;
    @ApiModelProperty(value = "订单状态(5-待支付、6-拼团中、30-待评价、45-已关闭、50-已完成、110-待受理、115-待上门)")
    private String orderStatus;
    @ApiModelProperty(value = "退款状态(0-无退款、10-已退款、15-部分退款、20-退款完成)")
    private String refundStatus;

    //1-旧订单；2-新订单 订单状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成)
    private String orderType;

    public String getOrderType() {
        if(orderType.equals("1")){
            return "APP";
        }
        return "小程序";
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRefundStatus() {
        if(orderType.equals("1")){
            return OLD_ORDER_STATUS.get(orderStatus);
        }
        return AceDictionary.ORDER_REFUND_STATUS.get(Integer.parseInt(refundStatus));
    }

    Map<String, String> OLD_ORDER_STATUS = new HashMap<String, String>() {

        private static final long serialVersionUID = 8918707984767183827L;
        {
            put("0", "已下单");
            put("1", "处理中");
            put("2", "待支付");
            put("3", "已取消");
            put("4", "已完成");
            put("5", "待确认");
            put("6", "退款中");
            put("7", "退款完成");
        }
    };

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPayMent() {
        return payMent;
    }

    public void setPayMent(String payMent) {
        this.payMent = payMent;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getOrderStatus() {
        if(orderType.equals("1")){
            return OLD_ORDER_STATUS.get(orderStatus);
        }
        return AceDictionary.ORDER_STATUS.get(Integer.parseInt(orderStatus));
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
