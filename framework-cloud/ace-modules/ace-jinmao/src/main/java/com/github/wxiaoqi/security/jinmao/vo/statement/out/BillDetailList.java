package com.github.wxiaoqi.security.jinmao.vo.statement.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BillDetailList implements Serializable {

    @ApiModelProperty(value = "订单号")
    private String subCode;
    @ApiModelProperty(value = "订单名称")
    private String title;
    @ApiModelProperty(value = "客户")
    private String contactName;
    @ApiModelProperty(value = "联系电话")
    private String contactTel;
    @ApiModelProperty(value = "支付类型(1-支付宝,2-微信)")
    private String payType;
    @ApiModelProperty(value = "商品数量")
    private String subNum;
    @ApiModelProperty(value = "商品单价")
    private String price;
    @ApiModelProperty(value = "订单完成时间")
    private String finishWoTime;
    @ApiModelProperty(value = "订单金额")
    private String cost;
    @ApiModelProperty(value = "订单状态(4-已完成、7-退款完成)")
    private String subscribeStatus;
    @ApiModelProperty(value = "工单id")
    private String woId;
}
