package com.github.wxiaoqi.security.jinmao.vo.Product.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ImgInfo implements Serializable {

    @ApiModelProperty(value = "图片路径")
    private String url;
    @ApiModelProperty(value = "图片名称")
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
