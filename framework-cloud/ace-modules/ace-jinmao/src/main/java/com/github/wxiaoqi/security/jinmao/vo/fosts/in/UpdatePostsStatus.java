package com.github.wxiaoqi.security.jinmao.vo.fosts.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdatePostsStatus implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String id;
    @ApiModelProperty(value = "帖子操作(0-隐藏,1-显示,2-精华,3-置顶,4-取消置顶,5-取消精华)")
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
