package com.github.wxiaoqi.security.app.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单商户信息
 */
@Data
public class ProductOrderTenantInfo {

    @ApiModelProperty(value = "商户id")
    private String tenantId;

    @ApiModelProperty(value = "商户名称")
    private String tenantName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "商品总金额")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "运费")
    private BigDecimal expressPrice;

    @ApiModelProperty(value = "总优惠金额")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualCost;

    @ApiModelProperty(value = "购买总数量")
    private int quantity;

    @ApiModelProperty(value = "商品信息")
    private List<ProductOrderTenantProductInfo> productList;

    @ApiModelProperty(value = "发票信息")
    private ProductOrderInvoiceInfo orderInvoice;

    @ApiModelProperty(value = "优惠信息")
    private List<ProductOrderDiscountInfo> discountList;

    @ApiModelProperty(value = "增值金额列表")
    private List<ProductOrderIncrementInfo> incrementList;

    public BigDecimal getActualCost() {
        return productPrice.add(expressPrice).subtract(discountPrice);
    }
}
