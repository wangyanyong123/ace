package com.github.wxiaoqi.security.jinmao.vo.Classify.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultClassifyVo implements Serializable {

    @ApiModelProperty(value = "商品分类id")
    private String id;
    @ApiModelProperty(value = "业务id")
    private String busId;
    @ApiModelProperty(value = "业务名称")
    private String busName;
    @ApiModelProperty(value = "业务编码")
    private String classifyCode;
    @ApiModelProperty(value = "业务名称")
    private String classifyName;
    @ApiModelProperty(value = "分类状态(0：未启用  1：启用）")
    private String busStatus;
    @ApiModelProperty(value = "排序（隔5设置，1，5，10...）")
    private String viewSort;

    @ApiModelProperty(value = "展示图片")
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(String busStatus) {
        this.busStatus = busStatus;
    }

    public String getViewSort() {
        return viewSort;
    }

    public void setViewSort(String viewSort) {
        this.viewSort = viewSort;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }
}
