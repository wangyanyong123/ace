package com.github.wxiaoqi.security.jinmao.vo.integralproduct.out;

import com.github.wxiaoqi.security.jinmao.vo.integralproduct.in.SaveSpecInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qs
 * @date 2019/8/28
 */
@Data
public class IntegralProductInfo implements Serializable {

    @ApiModelProperty(value = "商品基本信息")
    private ProductInfo productInfo;

    @ApiModelProperty(value = "商品图文信息")
    private String productImagetextInfo;

    @ApiModelProperty(value = "商品积分规格信息")
    private SaveSpecInfo specInfo;










}
