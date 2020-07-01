package com.github.wxiaoqi.security.jinmao.vo.ResultModuleVo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultModuleVo implements Serializable {

    @ApiModelProperty(value = "树形模块")
    private List<ModuleTree> children;

    @ApiModelProperty(value = "选中的模块id")
    private String[] projectModulesIds;

    public List<ModuleTree> getChildren() {
        return children;
    }

    public void setChildren(List<ModuleTree> children) {
        this.children = children;
    }

    public String[] getProjectModulesIds() {
        return projectModulesIds;
    }

    public void setProjectModulesIds(String[] projectModulesIds) {
        this.projectModulesIds = projectModulesIds;
    }
}
