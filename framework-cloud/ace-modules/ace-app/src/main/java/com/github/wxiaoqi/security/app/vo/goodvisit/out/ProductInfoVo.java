package com.github.wxiaoqi.security.app.vo.goodvisit.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ProductInfoVo implements Serializable {
    private static final long serialVersionUID = 7552443230262243439L;

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品标签")
    private String labelName;
    @ApiModelProperty(value = "商品价格")
    private String productPrice;
    @ApiModelProperty(value = "商品LOGO")
    private String productImage;
    @ApiModelProperty(value = "商品单位")
    private String unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
