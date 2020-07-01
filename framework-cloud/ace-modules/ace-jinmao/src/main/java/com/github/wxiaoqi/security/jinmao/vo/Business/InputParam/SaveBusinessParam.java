package com.github.wxiaoqi.security.jinmao.vo.Business.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveBusinessParam implements Serializable {

    @ApiModelProperty(value = "业务id")
    private String id;
    @ApiModelProperty(value = "业务编码")
    private String busCode;
    @ApiModelProperty(value = "业务名称")
    private String busName;
    @ApiModelProperty(value = "创建类型(1-工单,2-订单)")
    private String createType;
    @ApiModelProperty(value = "业务说明")
    private String description;
    @ApiModelProperty(value = "排序（隔5设置，1，5，10...）")
    private String viewSort;
    @ApiModelProperty(value = "业务类型(1-普通类型，2-团购，3-好物探访，4-疯抢)")
    private String type;

    @ApiModelProperty(value = "业务类型(1-普通类型，2-团购，3-好物探访，4-疯抢)")
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

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewSort() {
        return viewSort;
    }

    public void setViewSort(String viewSort) {
        this.viewSort = viewSort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
