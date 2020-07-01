package com.github.wxiaoqi.security.app.vo.shopping.out;

import com.github.wxiaoqi.security.app.vo.invoice.InvoiceParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 结算页商品信息
 */
@Data
public class ConfirmOrderInfo implements Serializable {
    private static final long serialVersionUID = 6511734713120334157L;

    @ApiModelProperty(value = "商户信息")
    private List<CartTenantInfoVo> tenantInfoList;

    @ApiModelProperty(value = "用户默认发票信息")
    private InvoiceParams defaultInvoice;
}
