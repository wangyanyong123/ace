package com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAppHotlineVo implements Serializable {

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "电话")
    private String hotline;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }
}
