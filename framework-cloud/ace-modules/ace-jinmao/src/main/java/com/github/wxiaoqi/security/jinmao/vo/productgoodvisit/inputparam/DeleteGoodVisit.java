package com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.inputparam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class DeleteGoodVisit implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "状态(0-删除，1-正常)")
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
