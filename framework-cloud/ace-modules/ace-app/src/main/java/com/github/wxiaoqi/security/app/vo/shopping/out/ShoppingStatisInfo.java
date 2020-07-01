package com.github.wxiaoqi.security.app.vo.shopping.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ShoppingStatisInfo implements Serializable {

    @ApiModelProperty(value = "合计金额")
    private String total;
    @ApiModelProperty(value = "结算数量")
    private String count;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
