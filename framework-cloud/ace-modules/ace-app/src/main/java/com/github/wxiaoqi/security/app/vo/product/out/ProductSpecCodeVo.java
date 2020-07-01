package com.github.wxiaoqi.security.app.vo.product.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductSpecCodeVo {
    @ApiModelProperty(value = "规格id")
    private String id;
    @ApiModelProperty(value = "商品id")
    private String productId;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "规格单价")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "规格编码")
    private String specTypeCode;
    @ApiModelProperty(value = "规格编码说明")
    private String specTypeVal;
}
