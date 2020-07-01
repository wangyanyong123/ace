package com.github.wxiaoqi.security.app.vo.propertybill.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InvoiceParam implements Serializable {

    @ApiModelProperty(value = "账单id,多个用逗号隔开")
    private String ids;
    @ApiModelProperty(value = "发票类型(1-个人,2-公司)")
    private String invoiceType;
    @ApiModelProperty(value = "发票抬头")
    private String invoiceName;
    @ApiModelProperty(value = "税号")
    private String dutyNum;
    @ApiModelProperty(value = "电话号码")
    private String telphone;
    @ApiModelProperty(value = "开户银行")
    private String bankName;
    @ApiModelProperty(value = "银行账号")
    private String bankNum;
    @ApiModelProperty(value = "单位地址")
    private String unitAddr;

}
