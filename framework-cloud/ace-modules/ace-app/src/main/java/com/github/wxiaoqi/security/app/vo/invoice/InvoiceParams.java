package com.github.wxiaoqi.security.app.vo.invoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class InvoiceParams implements Serializable {
    private static final long serialVersionUID = -4583034992871938695L;

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "发票名称")
    private String invoiceName;
    @ApiModelProperty(value = "发票类型(1-个人-2公司)")
    private String invoiceType;
    @ApiModelProperty(value = "税号")
    private String dutyNum;
    @ApiModelProperty(value = "是否默认(1-是0-否)")
    private String isDefault;
    @ApiModelProperty(value = "单位地址")
    private String unitAddr;
    @ApiModelProperty(value = "电话号码")
    private String telphone;
    @ApiModelProperty(value = "开户银行")
    private String bankName;
    @ApiModelProperty(value = "银行账户")
    private String bankNum;

}
