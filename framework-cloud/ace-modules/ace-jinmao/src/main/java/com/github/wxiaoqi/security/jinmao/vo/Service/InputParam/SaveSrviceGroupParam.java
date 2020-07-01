package com.github.wxiaoqi.security.jinmao.vo.Service.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveSrviceGroupParam implements Serializable {

    private String id;
    @ApiModelProperty(value = "父id")
    private String pid;
    @ApiModelProperty(value = "名称")
    private String name;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
