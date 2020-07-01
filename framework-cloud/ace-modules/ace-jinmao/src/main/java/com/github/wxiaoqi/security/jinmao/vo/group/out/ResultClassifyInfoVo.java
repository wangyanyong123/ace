package com.github.wxiaoqi.security.jinmao.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultClassifyInfoVo implements Serializable {

    @ApiModelProperty(value = "分类编码")
    private String classifyCode;
    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
