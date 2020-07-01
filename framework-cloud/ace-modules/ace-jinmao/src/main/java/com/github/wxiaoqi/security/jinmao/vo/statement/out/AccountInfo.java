package com.github.wxiaoqi.security.jinmao.vo.statement.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountInfo implements Serializable {

    @ApiModelProperty(value = "单据号")
    private String accountNum;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建人")
    private String createPerson;
    @ApiModelProperty(value = "收款账户类型(1-支付宝，2-微信，3-银行卡)")
    private String accountType;
    @ApiModelProperty(value = "收款账号名")
    private String accountName;
    @ApiModelProperty(value = "收款账号")
    private String accountNumber;
    @ApiModelProperty(value = "开户银行名称")
    private String accountBookName;
    @ApiModelProperty(value = "结算金额")
    private String balanceMoney;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
