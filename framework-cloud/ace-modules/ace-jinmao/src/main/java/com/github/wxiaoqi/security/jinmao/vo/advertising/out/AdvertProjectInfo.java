package com.github.wxiaoqi.security.jinmao.vo.advertising.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AdvertProjectInfo implements Serializable {

    @ApiModelProperty(value = "项目ID")
    private String id;
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
