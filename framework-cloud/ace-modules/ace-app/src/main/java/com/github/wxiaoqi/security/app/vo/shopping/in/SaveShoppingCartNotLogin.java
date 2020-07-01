package com.github.wxiaoqi.security.app.vo.shopping.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
public class SaveShoppingCartNotLogin extends SaveShoppingCart {


    private static final long serialVersionUID = -3523905927641489398L;
    @ApiModelProperty(value = "微信openId")
    private String openId;

}
