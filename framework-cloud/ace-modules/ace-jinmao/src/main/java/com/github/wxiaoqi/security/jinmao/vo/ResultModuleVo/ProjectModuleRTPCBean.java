package com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ProjectModuleRTPCBean implements Serializable {

    @ApiModelProperty(value = "模块id")
    private String moduleId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "父节点id")
    private String pid;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "0表示不是叶子节点，1表示是叶子节点")
    //是否是叶子节点，0表示不是，1表示是
    private String isChild;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIsChild() {
        return isChild;
    }

    public void setIsChild(String isChild) {
        this.isChild = isChild;
    }
}
