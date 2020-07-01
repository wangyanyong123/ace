package com.github.wxiaoqi.security.jinmao.vo.group.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GroupStatus implements Serializable {

    @ApiModelProperty(value = "小组id")
    private String id;
    @ApiModelProperty(value = "启用状态(1-草稿，2-已发布，3-已撤回)")
    private String enableStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}
