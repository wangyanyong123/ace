package com.github.wxiaoqi.security.jinmao.vo.enclosed.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveEnclosedParam implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "上级围合")
    private String enclosedPid;
    @ApiModelProperty(value = "围合名称")
    private String enclosedName;
    @ApiModelProperty(value = "项目ID")
    private String projectId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnclosedPid() {
        return enclosedPid;
    }

    public void setEnclosedPid(String enclosedPid) {
        this.enclosedPid = enclosedPid;
    }

    public String getEnclosedName() {
        return enclosedName;
    }

    public void setEnclosedName(String enclosedName) {
        this.enclosedName = enclosedName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
