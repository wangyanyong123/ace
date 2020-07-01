package com.github.wxiaoqi.security.app.biz.order.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发票信息
 * @author: guohao
 * @create: 2020-04-18 12:28
 **/

@Data
public class InvoiceInfo implements Serializable {

    private static final long serialVersionUID = -4469809101248467213L;

    @ApiModelProperty(value = "发票类型(0-不开发票,1-个人,2-公司)")
    private int invoiceType;

    @ApiModelProperty(value = "发票名称")
    private String invoiceName;

    @ApiModelProperty(value = "税号")
    private String dutyNum;
}
