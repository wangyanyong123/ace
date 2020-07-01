package com.github.wxiaoqi.security.jinmao.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultClassify implements Serializable {

    @ApiModelProperty(value = "分类编码")
    private String code;
    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
