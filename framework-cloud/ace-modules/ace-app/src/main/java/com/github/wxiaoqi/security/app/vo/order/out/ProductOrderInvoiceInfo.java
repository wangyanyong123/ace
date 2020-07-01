package com.github.wxiaoqi.security.app.vo.order.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单发票信息
 */
@Data
public class ProductOrderInvoiceInfo implements Serializable {
    private static final long serialVersionUID = 7984413297739812066L;

    @ApiModelProperty(value = "发票类型(0-不开发票,1-个人,2-公司)")
    private Integer invoiceType;
    @ApiModelProperty(value = "发票抬头")
    private String invoiceName;
    @ApiModelProperty(value = "税号")
    private String dutyCode;

    public String getInvoiceTypeDesc(){
        return AceDictionary.INVOICE_TYPE.get(invoiceType);
    }
}
