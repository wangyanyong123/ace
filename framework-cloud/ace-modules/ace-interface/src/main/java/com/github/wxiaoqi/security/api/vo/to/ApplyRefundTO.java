package com.github.wxiaoqi.security.api.vo.to;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author guohao
 * @Date 2020/4/10 11:26
 */
@Data
public class ApplyRefundTO implements Serializable {
    private static final long serialVersionUID = -1947629282960750144L;

    @ApiModelProperty(value = "支付时使用的应用id")
    private String appId;

    @ApiModelProperty(value = "系统支付id， outTradeNo 与 tradeNo 二选一")
    private String outTradeNo;

    @ApiModelProperty(value = "商户平台支付流水号 ，outTradeNo 与 tradeNo 二选一")
    private String tradeNo;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    @ApiModelProperty(hidden = true)
    private String outRequestNo;

    @ApiModelProperty(value = "退款金额，部分退款时需要，未填写为全部退款")
    private BigDecimal refundMoney;
}
