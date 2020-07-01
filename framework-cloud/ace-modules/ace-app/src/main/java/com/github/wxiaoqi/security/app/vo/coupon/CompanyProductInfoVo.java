package com.github.wxiaoqi.security.app.vo.coupon;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CompanyProductInfoVo implements Serializable {

    private String companyId;

    private BigDecimal totalPrice;

    private List<ProductInfoVo> productInfo;
}
