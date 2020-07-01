package com.github.wxiaoqi.security.app.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ProjectIntegralVo implements Serializable {

    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "积分值")
    private int creditsValueTotal;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCreditsValueTotal() {
        return creditsValueTotal;
    }

    public void setCreditsValueTotal(int creditsValueTotal) {
        this.creditsValueTotal = creditsValueTotal;
    }
}
