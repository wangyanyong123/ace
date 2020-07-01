package com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultHotlineVo implements Serializable {

    @ApiModelProperty(value = "热线id")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
