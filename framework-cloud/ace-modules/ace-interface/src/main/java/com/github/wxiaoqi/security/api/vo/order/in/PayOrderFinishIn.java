package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 支付宝、微信回调通知成功后业务处理参数
 *
 * @author huangxl
 * @date 2018-12-25
 */
@Data
public class PayOrderFinishIn implements Serializable {


    private static final long serialVersionUID = -2179712162616133779L;
    @ApiModelProperty(value = "支付凭证ID")
    private String payId;

    @ApiModelProperty(value = "实际支付金额ID")
    private String actualId;

    @ApiModelProperty(value = "实际支付金额")
    private String totalFee;

    @ApiModelProperty(value = "支付类型( 1，支付宝；2，微信)")
    private String payType;

    @ApiModelProperty(value = "支付商户号")
    private String mchId;

    @ApiModelProperty(value = "支付宝，微信 应用appId")
    private String appId;

}