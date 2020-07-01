package com.github.wxiaoqi.security.app.vo.shopping.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateShoppingCartNotLogin extends UpdateShoppingCart {

    private static final long serialVersionUID = 8746641837283542571L;

    @ApiModelProperty(value = "微信openId",required = true)
    private String openId;

}
