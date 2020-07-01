package com.github.wxiaoqi.security.api.vo.img;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ImgInfo implements Serializable {

    @ApiModelProperty(value = "图片路径")
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
