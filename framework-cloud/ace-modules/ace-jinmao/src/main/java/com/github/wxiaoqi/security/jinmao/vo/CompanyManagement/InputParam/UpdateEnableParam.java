package com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateEnableParam implements Serializable {

    @ApiModelProperty(value = "启用状态(0表示禁用，1表示启用)")
    private String enableStatus;

    @ApiModelProperty(value = "租户id")
    private String id;

    @ApiModelProperty(value = "修改人id，不用传")
    private String updUserId;

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }


}
