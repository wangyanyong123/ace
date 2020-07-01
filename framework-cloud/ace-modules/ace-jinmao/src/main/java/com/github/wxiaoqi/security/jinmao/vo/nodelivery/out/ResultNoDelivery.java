package com.github.wxiaoqi.security.jinmao.vo.nodelivery.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultNoDelivery implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "区域名称")
    private String regionName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}