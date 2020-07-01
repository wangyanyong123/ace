package com.github.wxiaoqi.security.app.vo.order.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductOrderIncrementInfo implements Serializable {

    private static final long serialVersionUID = -6712387086234216302L;

    @ApiModelProperty(value = "增值金额类型 1：运费")
    private Integer incrementType;

    private String relationId;

    private BigDecimal price;

    public String getIncrementTypeDesc(){
        return AceDictionary.ORDER_INCREMENT.get(incrementType);
    }
}
