package com.github.wxiaoqi.security.app.vo.coupon;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CouponPriceVo implements Serializable {

    private static final long serialVersionUID = 5345072464427239914L;
    private String id;

    private BigDecimal value;

    private String discountNum;

    private BigDecimal masValue;

    private String couponType;

    private BigDecimal discountPrice;
}
