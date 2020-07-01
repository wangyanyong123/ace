package com.github.wxiaoqi.pay.biz.support;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: guohao
 * @create: 2020-04-11 13:54
 **/
@Data
public class RefundParam implements Serializable {

    private static final long serialVersionUID = 5800292151796775893L;

    @ApiModelProperty(value = "支付类型")
    private String payType;

    @ApiModelProperty(value = "支付时使用的应用id")
    private String appId;

    @ApiModelProperty(value = "系统支付id， outTradeNo 与 tradeNo 二选一")
    private String outTradeNo;

    @ApiModelProperty(value = "商户平台支付流水号 ，outTradeNo 与 tradeNo 二选一")
    private String tradeNo;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    @ApiModelProperty(value = "系统退款编号")
    private String outRequestNo;

    @ApiModelProperty(value = "原支付金额")
    private BigDecimal paidMoney;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;
}
