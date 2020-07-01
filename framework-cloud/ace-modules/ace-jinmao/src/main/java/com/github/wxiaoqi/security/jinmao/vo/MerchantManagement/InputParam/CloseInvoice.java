package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class CloseInvoice implements Serializable {
    @ApiModelProperty(value = "设置类型，打烊‘isClose’发票‘isInvoice’")
    private String  setUpClosure;

    @ApiModelProperty(value = "1是2否")
    private String id;


    public String getSetUpClosure() {
        return setUpClosure;
    }

    public void setSetUpClosure(String setUpClosure) {
        this.setUpClosure = setUpClosure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
