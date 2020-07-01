package com.github.wxiaoqi.security.app.vo.order.out;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户商品信息
 */
@Data
public class ProductOrderTenantProductInfo {

    private String productId;

    private String productName;

    private String specId;

    private String specName;

    private BigDecimal salesPrice;

    private int quantity;

    private String specImg;

    private String unit;
}
