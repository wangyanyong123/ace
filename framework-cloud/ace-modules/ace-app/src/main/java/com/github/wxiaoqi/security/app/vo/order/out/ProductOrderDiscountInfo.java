package com.github.wxiaoqi.security.app.vo.order.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductOrderDiscountInfo implements Serializable {

    private static final long serialVersionUID = -4321927616073680053L;

    @ApiModelProperty(value = "优惠类型 1：优惠券")
    private Integer discountType;

    private String relationId;

    private BigDecimal discountPrice;

    public String getDiscountTypeDesc(){
        return AceDictionary.ORDER_DISCOUNT_TYPE.get(discountType);
    }
}
