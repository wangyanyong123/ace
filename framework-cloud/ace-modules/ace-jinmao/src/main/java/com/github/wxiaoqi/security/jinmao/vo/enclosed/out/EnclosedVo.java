package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class EnclosedVo implements Serializable {

    @ApiModelProperty(value = "树形模块")
    private List<EnclosedTree> children;

    public List<EnclosedTree> getChildren() {
        return children;
    }

    public void setChildren(List<EnclosedTree> children) {
        this.children = children;
    }
}
