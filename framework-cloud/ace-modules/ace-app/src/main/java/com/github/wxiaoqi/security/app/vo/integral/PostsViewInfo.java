package com.github.wxiaoqi.security.app.vo.integral;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PostsViewInfo implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String postsId;
    @ApiModelProperty(value = "帖子浏览量")
    private int num;

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
