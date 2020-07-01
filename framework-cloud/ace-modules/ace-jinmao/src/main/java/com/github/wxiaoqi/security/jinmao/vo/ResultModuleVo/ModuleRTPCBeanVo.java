package com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ModuleRTPCBeanVo implements Serializable {

    @ApiModelProperty(value = "模块id")
    private String moduleId;

    @ApiModelProperty(value = "父节点id")
    private String pid;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "排序")
    private String sort;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
