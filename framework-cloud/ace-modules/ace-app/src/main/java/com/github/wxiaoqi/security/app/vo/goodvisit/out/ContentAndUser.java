package com.github.wxiaoqi.security.app.vo.goodvisit.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ContentAndUser implements Serializable {


    @ApiModelProperty(value = "内容ID")
    private String contentId;


    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

}
