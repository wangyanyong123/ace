package com.github.wxiaoqi.security.jinmao.vo.statement.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatementVo implements Serializable {

    @ApiModelProperty(value = "账单id")
    private String id;
    @ApiModelProperty(value = "账单编号")
    private String billNumber;
    @ApiModelProperty(value = "结算周期")
    private String settlementCycle;
    @ApiModelProperty(value = "营收金额")
    private String revenueMoney;
    @ApiModelProperty(value = "结算金额")
    private String balanceMoney;
    @ApiModelProperty(value = "结算状态(0：未结算，1：结算中，2：已结算)")
    private String balanceStatus;
    @ApiModelProperty(value = "商户账号")
    private String account;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;
    @ApiModelProperty(value = "商户id")
    private String tenantId;
    @ApiModelProperty(value = "服务范围")
    private String projectName;
}
