package com.github.wxiaoqi.security.app.vo.property.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class PropertyEvaluateInfo implements Serializable {


    @ApiModelProperty(value = "是否评价(0-没有,1-有)")
    private String isEvaluate;
    @ApiModelProperty(value = "总平均分")
    private String average;
    @ApiModelProperty(value = "用户当前星值评价等级(1-*,2-**,3-***,4-****,5-*****)")
    private String evaluateType;
    @ApiModelProperty(value = "各分值百分比")
    private List<PropertyInfo> propertyInfoList;
    @ApiModelProperty(value = "当前评价")
    private String evaluate;
    @ApiModelProperty(value = "当前月份")
    private int currentDate;
    @ApiModelProperty(value = "当前月评价人数")
    private int currentUserNum;

    public String getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(String isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public List<PropertyInfo> getPropertyInfoList() {
        return propertyInfoList;
    }

    public void setPropertyInfoList(List<PropertyInfo> propertyInfoList) {
        this.propertyInfoList = propertyInfoList;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public int getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(int currentDate) {
        this.currentDate = currentDate;
    }

    public int getCurrentUserNum() {
        return currentUserNum;
    }

    public void setCurrentUserNum(int currentUserNum) {
        this.currentUserNum = currentUserNum;
    }
}
