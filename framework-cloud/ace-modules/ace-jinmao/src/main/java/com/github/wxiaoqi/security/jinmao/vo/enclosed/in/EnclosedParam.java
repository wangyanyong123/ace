package com.github.wxiaoqi.security.jinmao.vo.enclosed.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class EnclosedParam implements Serializable {

    @ApiModelProperty(value = "单元ID")
    private String unitId;


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }


}
