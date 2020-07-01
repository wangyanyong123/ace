package com.github.wxiaoqi.security.jinmao.vo.Product.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateStatusParam implements Serializable {

    @ApiModelProperty(value = "商户id")
    private String id;
    @ApiModelProperty(value = "操作(1.申请上架,2上架,3下架,4驳回)")
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
