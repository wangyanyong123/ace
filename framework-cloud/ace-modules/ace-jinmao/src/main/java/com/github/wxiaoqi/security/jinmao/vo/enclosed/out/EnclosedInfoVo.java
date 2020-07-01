package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class EnclosedInfoVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "上级围合")
    private String enclosedPid;
    @ApiModelProperty(value = "名称")
    private String enclosedName;
    @ApiModelProperty(value = "项目ID")
    private String projectId;
    @ApiModelProperty(value = "项目信息")
    private List<ProjectInfoVo> projectInfo;

    public String getEnclosedPid() {
        return enclosedPid;
    }

    public void setEnclosedPid(String enclosedPid) {
        this.enclosedPid = enclosedPid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<ProjectInfoVo> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<ProjectInfoVo> projectInfo) {
        this.projectInfo = projectInfo;
    }
}
