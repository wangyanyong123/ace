package com.github.wxiaoqi.security.app.biz.order.context;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 优惠信息
 * @author: guohao
 * @create: 2020-04-19 11:27
 **/
@Data
public class DiscountInfo {

    private int discountType;

    private String relationId;

    private BigDecimal discountPrice;

    private String orderId;

}
