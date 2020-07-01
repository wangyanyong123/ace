package com.github.wxiaoqi.security.app.vo.evaluate.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveHousekeeperEvaluate implements Serializable {

    @ApiModelProperty(value = "评价等级(1-不满意,2-一般,3-满意)")
    private String evaluateType;
    @ApiModelProperty(value = "评价理由(多个用,隔开)")
    private String evaluateReason;
    @ApiModelProperty(value = "评价内容")
    private String content;
    @ApiModelProperty(value = "项目id")
    private String projectId;

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public String getEvaluateReason() {
        return evaluateReason;
    }

    public void setEvaluateReason(String evaluateReason) {
        this.evaluateReason = evaluateReason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
