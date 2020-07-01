package com.github.wxiaoqi.security.jinmao.vo.coupon.out;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductInfoVo implements Serializable {

    private static final long serialVersionUID = 7697697504084852081L;
    private String productId;

    private String productName;

    private String price;
}
