package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultProductInfo implements Serializable {

    @ApiModelProperty(value = "商品基本信息")
    private ResultProductInfoVo productInfo;
    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;
    @ApiModelProperty(value = "商品规格信息")
    private List<ResultSpecVo> specInfo;

    public ResultProductInfoVo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ResultProductInfoVo productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductImagetextInfo() {
        return productImagetextInfo;
    }

    public void setProductImagetextInfo(String productImagetextInfo) {
        this.productImagetextInfo = productImagetextInfo;
    }

    public List<ResultSpecVo> getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(List<ResultSpecVo> specInfo) {
        this.specInfo = specInfo;
    }
}
