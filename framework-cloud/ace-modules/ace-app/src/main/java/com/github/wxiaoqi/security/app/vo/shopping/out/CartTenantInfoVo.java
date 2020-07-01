package com.github.wxiaoqi.security.app.vo.shopping.out;

import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderDiscountInfo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderIncrementInfo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderInvoiceInfo;
import com.github.wxiaoqi.security.app.vo.order.out.ProductOrderTenantProductInfo;
import com.github.wxiaoqi.security.app.vo.postage.PostageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户信息
 */
@Data
public class CartTenantInfoVo {

    @ApiModelProperty(value = "商户id")
    private String tenantId;

    @ApiModelProperty(value = "商户名称")
    private String tenantName;

    @ApiModelProperty(value = "运费")
    private BigDecimal expressPrice;

    @ApiModelProperty(value = "商品金额")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "邮费信息")
    private PostageInfo postageInfo;

    @ApiModelProperty(value = "是否可开票，默认2，1、可开票，2、不可")
    private String isInvoice;

    @ApiModelProperty(value = "商品信息")
    private List<CartProductInfo> productList;



}
