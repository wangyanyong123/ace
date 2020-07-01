package com.github.wxiaoqi.security.jinmao.vo.nodelivery.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SaveNoDelivery implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "区域ID")
    private List<Map<String,String>> regionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public List<Map<String, String>> getRegionId() {
        return regionId;
    }

    public void setRegionId(List<Map<String, String>> regionId) {
        this.regionId = regionId;
    }
}
