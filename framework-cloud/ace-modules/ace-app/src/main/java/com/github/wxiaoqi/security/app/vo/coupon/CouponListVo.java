package com.github.wxiaoqi.security.app.vo.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CouponListVo implements Serializable {

    @ApiModelProperty(value = "优惠券id")
    private String couponId;
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;
    @ApiModelProperty(value = "封面")
    private String coverPhoto;
    @ApiModelProperty(value = "优惠券类型(1-代价券2-折扣券")
    private String couponType;
    @ApiModelProperty(value = "折扣力度")
    private String discountNum;
    @ApiModelProperty(value = "最高满减金额")
    private String masValue;
    @ApiModelProperty(value = "最低满减金额")
    private String minValue;
    @ApiModelProperty(value = "券面值")
    private String value;
    @ApiModelProperty(value = "使用描述")
    private String description;
    @ApiModelProperty(value = "使用范围")
    private String useScope;
    @ApiModelProperty(value = "开始时间")
    private String startUseTime;
    @ApiModelProperty(value = "结束时间")
    private String endUseTime;
    @ApiModelProperty(value = "领取状态(0-已领完1-可领取2-不可领取)")
    private String getStatus;
    @ApiModelProperty(value = "商家名称")
    private String tenantName;
    @ApiModelProperty(value = "优惠券状态(1-已领取2、3-已使用-4-已过期)")
    private String useStatus;
    @ApiModelProperty(value = "券数量")
    private String amount;
    @ApiModelProperty(value = "剩余数量")
    private String total;
    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal discountPrice;

    public String getUseScope() {
        if (tenantName != null) {
            useScope = "限购[" + tenantName + "]商家商品";
        } else if (masValue != null && tenantName !=null ) {
            useScope = "限购[" + tenantName + "]商家商品"+","+"最高可抵"+masValue+"元";
        }
        return useScope;
    }

    public String getDescription() {
        if (minValue != null && "0".equals(minValue)) {
            description = "无门槛";
        } else if (minValue != null) {
            description = "满" + minValue + "使用";
        }
        return description;
    }


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


    public String getMasValue() {
        if (masValue != null) {
            if (masValue.indexOf(".") > 0) {
                // 去掉多余的0
                masValue = masValue.replaceAll("0+?$", "");
                // 如最后一位是.则去掉
                masValue = masValue.replaceAll("[.]$", "");
            }
        }
        return masValue;
    }
}
