package com.github.wxiaoqi.security.jinmao.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ProjectVo implements Serializable {

    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
