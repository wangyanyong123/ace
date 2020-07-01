package com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam;

import io.swagger.annotations.ApiModelProperty;

public class GVUpdateStatus {

    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "启用状态(1-草稿，2-已发布，3-已撤回,4-全部)")
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
