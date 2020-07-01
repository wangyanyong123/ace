package com.github.wxiaoqi.security.app.vo.property.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PropertyInfo implements Serializable {

    @ApiModelProperty(value = "物业评价详情id")
    private String id;
    @ApiModelProperty(value = "星值评价等级(1-*,2-**,3-***,4-****,5-*****)")
    private String evaluateType;
    @ApiModelProperty(value = "各星值评价数量")
    private int evaluateSum;
    @ApiModelProperty(value = "各星值评价百分比")
    private String evaluateScale;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public int getEvaluateSum() {
        return evaluateSum;
    }

    public void setEvaluateSum(int evaluateSum) {
        this.evaluateSum = evaluateSum;
    }

    public String getEvaluateScale() {
        return evaluateScale;
    }

    public void setEvaluateScale(String evaluateScale) {
        this.evaluateScale = evaluateScale;
    }
}
