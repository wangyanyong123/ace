package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultBusinessInfo implements Serializable {
    @ApiModelProperty(value = "业务ID")
    private String id;
    @ApiModelProperty(value = "业务名称")
    private String busName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }
}
