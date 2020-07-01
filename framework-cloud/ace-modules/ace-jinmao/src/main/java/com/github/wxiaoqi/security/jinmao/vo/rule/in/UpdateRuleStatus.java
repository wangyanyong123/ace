package com.github.wxiaoqi.security.jinmao.vo.rule.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateRuleStatus implements Serializable {

    @ApiModelProperty(value = "积分规则id")
    private String id;
    @ApiModelProperty(value = "积分规则操作(0-删除,2-已启用,3-已停用)")
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
