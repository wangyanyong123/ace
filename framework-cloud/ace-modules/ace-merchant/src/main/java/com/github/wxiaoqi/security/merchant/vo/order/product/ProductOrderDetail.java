package com.github.wxiaoqi.security.merchant.vo.order.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 我的商品订单
 */
@Data
public class ProductOrderDetail implements Serializable {

    //订单详情id
    private String id;

    //产品名称
    private String productName;

    //规格名称
    private String specName;

    //图片id,多张图片逗号分隔
    private String specImg;

    //数量
    private Integer quantity;

    //单价
    private BigDecimal salesPrice;

    //总价
    private BigDecimal totalPrice;

    //单位
    private String unit;

//    // 运费
//    private BigDecimal freightPrice;
//
//    // 优惠金额
//    private BigDecimal discountPrice;

}
