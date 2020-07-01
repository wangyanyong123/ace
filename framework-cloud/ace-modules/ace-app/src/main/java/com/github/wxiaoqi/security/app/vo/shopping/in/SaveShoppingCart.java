package com.github.wxiaoqi.security.app.vo.shopping.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveShoppingCart implements Serializable {

    private static final long serialVersionUID = -4524642266548472462L;
    @ApiModelProperty(value = "应用类型 H5:10,微信小程序：20；安卓：30. ios：40, 服务的设置",hidden = true)
    private Integer appType;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "商品id")
    private String productId;
    @ApiModelProperty(value = "规格id")
    private String specId;
    @ApiModelProperty(value = "购买数量")
    private String buyNum;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }
}
