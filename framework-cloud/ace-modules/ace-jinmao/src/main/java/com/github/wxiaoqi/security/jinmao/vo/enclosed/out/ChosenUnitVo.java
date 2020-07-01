package com.github.wxiaoqi.security.jinmao.vo.enclosed.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ChosenUnitVo implements Serializable {

    @ApiModelProperty(value = "单元id")
    private String[] unitId;

    public String[] getUnitId() {
        return unitId;
    }

    public void setUnitId(String[] unitId) {
        this.unitId = unitId;
    }
}
