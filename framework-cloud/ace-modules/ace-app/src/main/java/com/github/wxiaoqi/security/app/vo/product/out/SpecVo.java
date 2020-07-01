package com.github.wxiaoqi.security.app.vo.product.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SpecVo implements Serializable {

    @ApiModelProperty(value = "原价")
    private String originalPrice;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
