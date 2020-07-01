package com.github.wxiaoqi.security.jinmao.vo.Classify.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class StatusParam implements Serializable {

    @ApiModelProperty(value = "商品分类id")
    private String id;
    @ApiModelProperty(value = "分类状态(0：未启用  1：启用）")
    private String busStatus;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(String busStatus) {
        this.busStatus = busStatus;
    }
}
