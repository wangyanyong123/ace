package com.github.wxiaoqi.security.app.vo.shopping.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SpecInfo implements Serializable {

    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "单价金额")
    private String price;

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
