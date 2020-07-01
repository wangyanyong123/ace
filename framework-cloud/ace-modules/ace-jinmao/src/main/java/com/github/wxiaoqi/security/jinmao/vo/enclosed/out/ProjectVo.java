package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ProjectVo implements Serializable {

    @ApiModelProperty(value = "树形模块")
    private List<ProjectTree> children;


    public List<ProjectTree> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectTree> children) {
        this.children = children;
    }


}
