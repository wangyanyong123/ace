package com.github.wxiaoqi.security.app.vo.product.out;

import lombok.Data;

import java.io.Serializable;

@Data
public class CouponInfoVo implements Serializable {
    private static final long serialVersionUID = 7661482213612302921L;

    private String id;

    private String value;

    private String minValue;

    private String discountNum;

    public String getValue() {
        if (value.indexOf(".") > 0) {
            // 去掉多余的0
            value = value.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            value = value.replaceAll("[.]$", "");
        }
        return value;
    }

    public String getMinValue() {
        if (minValue.indexOf(".") > 0) {
            // 去掉多余的0
            minValue = minValue.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            minValue = minValue.replaceAll("[.]$", "");
        }
        return minValue;
    }

    public String getDiscountNum() {
        if (discountNum != null) {
            if (discountNum.indexOf(".") > 0) {
                // 去掉多余的0
                discountNum = discountNum.replaceAll("0+?$", "");
                // 如最后一位是.则去掉
                discountNum = discountNum.replaceAll("[.]$", "");
            }
        }
        return discountNum;
    }
}
