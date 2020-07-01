package com.github.wxiaoqi.security.jinmao.vo.enclosed.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SetEnclosedParam implements Serializable {

    @ApiModelProperty(value = "单元信息")
    private String[] unitId;
    @ApiModelProperty(value = "标识(1-添加2-取消)")
    private Integer isDelete;
    @ApiModelProperty(value = "围合ID(取消围合不用传值)")
    private String enclosedId;

    public String getEnclosedId() {
        return enclosedId;
    }

    public void setEnclosedId(String enclosedId) {
        this.enclosedId = enclosedId;
    }

    public String[] getUnitId() {
        return unitId;
    }

    public void setUnitId(String[] unitId) {
        this.unitId = unitId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
