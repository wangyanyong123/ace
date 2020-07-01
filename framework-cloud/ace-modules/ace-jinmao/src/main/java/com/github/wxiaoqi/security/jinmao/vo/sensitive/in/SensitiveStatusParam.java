package com.github.wxiaoqi.security.jinmao.vo.sensitive.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SensitiveStatusParam implements Serializable {


    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "状态操作(0-删除,1-已启用,2-禁用)")
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
