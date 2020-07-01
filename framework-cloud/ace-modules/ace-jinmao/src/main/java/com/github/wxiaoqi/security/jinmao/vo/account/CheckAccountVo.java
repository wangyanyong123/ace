package com.github.wxiaoqi.security.jinmao.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CheckAccountVo implements Serializable {

    @ApiModelProperty(value = "账单编号")
    private String billNumber;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;
    @ApiModelProperty(value = "结算金额")
    private String balanceMoney;
    @ApiModelProperty(value = "结算周期")
    private String settlementCycle;
    @ApiModelProperty(value = "收款账号")
    private String accountNumber;
    @ApiModelProperty(value = "账期")
    private String paymentDay;
    @ApiModelProperty(value = "结算状态,0：待确认，1：待支付，2：已结算")
    private String balanceStatus;
}
