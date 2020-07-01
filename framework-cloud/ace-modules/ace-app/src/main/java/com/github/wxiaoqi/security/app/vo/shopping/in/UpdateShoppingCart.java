package com.github.wxiaoqi.security.app.vo.shopping.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateShoppingCart implements Serializable {

    private static final long serialVersionUID = 8516807233087487943L;
    @ApiModelProperty(value = "购物车id")
    private String id;
    @ApiModelProperty(value = "购物数量")
    private String buyNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }
}
