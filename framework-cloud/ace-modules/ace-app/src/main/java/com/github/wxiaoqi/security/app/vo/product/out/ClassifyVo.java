package com.github.wxiaoqi.security.app.vo.product.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ClassifyVo implements Serializable {

    @ApiModelProperty(value = "商品分类id")
    private String classifyId;
    @ApiModelProperty(value = "商品分类名称")
    private String classifyName;

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
