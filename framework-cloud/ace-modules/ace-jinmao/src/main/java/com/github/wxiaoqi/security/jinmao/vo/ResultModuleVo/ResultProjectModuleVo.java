package com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultProjectModuleVo implements Serializable {

    @ApiModelProperty(value = "模块的排序位置")
    private String moduleSort;

    @ApiModelProperty(value = "模块名称")
    private String moduleFullName;

    @ApiModelProperty(value = "子模块集合")
    private List<ProjectModuleRTPCBean> children;


    public String getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(String moduleSort) {
        this.moduleSort = moduleSort;
    }

    public String getModuleFullName() {
        return moduleFullName;
    }

    public void setModuleFullName(String moduleFullName) {
        this.moduleFullName = moduleFullName;
    }

    public List<ProjectModuleRTPCBean> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectModuleRTPCBean> children) {
        this.children = children;
    }
}
