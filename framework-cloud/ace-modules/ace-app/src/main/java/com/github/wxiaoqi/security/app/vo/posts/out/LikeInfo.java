package com.github.wxiaoqi.security.app.vo.posts.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class LikeInfo implements Serializable {

    @ApiModelProperty(value = "点赞统计id")
    private String id;
    @ApiModelProperty(value = "点赞统计数")
    private int likeNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}
