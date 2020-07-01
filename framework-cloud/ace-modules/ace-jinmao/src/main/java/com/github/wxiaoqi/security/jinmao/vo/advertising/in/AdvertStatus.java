package com.github.wxiaoqi.security.jinmao.vo.advertising.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AdvertStatus implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "状态(0-删除)")
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
