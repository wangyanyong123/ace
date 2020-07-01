package com.github.wxiaoqi.security.jinmao.vo.activity.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateStatus implements Serializable {

    @ApiModelProperty(value = "活动id")
    private String id;
    @ApiModelProperty(value = "活动操作(0-删除,2-发布,3-撤回)")
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
