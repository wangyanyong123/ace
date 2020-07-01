package com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultProjectInfo implements Serializable {

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
