package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class FacilitiesInfoVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "围合ID")
    private String enclosedId;
    @ApiModelProperty(value = "道闸名称")
    private String name;
    @ApiModelProperty(value = "道闸方向(1-进门禁闸机，2-出门禁闸机)")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnclosedId() {
        return enclosedId;
    }

    public void setEnclosedId(String enclosedId) {
        this.enclosedId = enclosedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
