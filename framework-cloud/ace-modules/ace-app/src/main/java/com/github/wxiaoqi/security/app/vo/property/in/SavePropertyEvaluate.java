package com.github.wxiaoqi.security.app.vo.property.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SavePropertyEvaluate implements Serializable {

    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "星值评价等级(1-*,2-**,3-***,4-****,5-*****)")
    private String evaluateType;
    @ApiModelProperty(value = "评价内容")
    private String content;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
