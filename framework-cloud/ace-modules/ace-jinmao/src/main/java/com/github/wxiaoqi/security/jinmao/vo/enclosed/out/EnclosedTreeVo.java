package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class EnclosedTreeVo implements Serializable {

    @ApiModelProperty(value = "围合id")
    private String id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "父id")
    private String pid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
