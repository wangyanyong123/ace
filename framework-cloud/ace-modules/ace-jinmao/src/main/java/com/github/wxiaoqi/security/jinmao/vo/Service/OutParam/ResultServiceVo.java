package com.github.wxiaoqi.security.jinmao.vo.Service.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultServiceVo implements Serializable {

    @ApiModelProperty(value = "树形模块")
    private List<ServiceTree> children;

    public List<ServiceTree> getChildren() {
        return children;
    }

    public void setChildren(List<ServiceTree> children) {
        this.children = children;
    }
}
