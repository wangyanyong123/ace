package com.github.wxiaoqi.security.jinmao.vo.fosts.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateCommentStatus implements Serializable {

    @ApiModelProperty(value = "评论id")
    private String id;
    @ApiModelProperty(value = "评论操作(0-隐藏,1-显示)")
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
