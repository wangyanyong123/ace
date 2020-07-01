package com.github.wxiaoqi.security.jinmao.vo.communityTopic.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateTopicStatus implements Serializable {

    @ApiModelProperty(value = "社区话题id")
    private String id;
    @ApiModelProperty(value = "帖子操作(0-隐藏,1-显示,3-置顶,4-取消置顶)")
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
