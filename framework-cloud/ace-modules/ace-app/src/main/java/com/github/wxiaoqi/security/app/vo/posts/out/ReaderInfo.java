package com.github.wxiaoqi.security.app.vo.posts.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ReaderInfo implements Serializable {

    @ApiModelProperty(value = "阅读id")
    private String id;
    @ApiModelProperty(value = "查看数量")
    private int viewNum;
    @ApiModelProperty(value = "独立访问用户")
    private int userNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }
}
