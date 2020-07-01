package com.github.wxiaoqi.security.jinmao.vo.nodelivery.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultRegionList implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "区域编码")
    private String regionCode;
    @ApiModelProperty(value = "区域名称")
    private String regionName;
    @ApiModelProperty(value = "区域全称")
    private String regionFullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionFullName() {
        return regionFullName;
    }

    public void setRegionFullName(String regionFullName) {
        this.regionFullName = regionFullName;
    }
}
