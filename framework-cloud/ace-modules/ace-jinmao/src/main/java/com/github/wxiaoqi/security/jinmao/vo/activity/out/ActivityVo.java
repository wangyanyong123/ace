package com.github.wxiaoqi.security.jinmao.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ActivityVo implements Serializable {

    @ApiModelProperty(value = "活动id")
    private String id;
    @ApiModelProperty(value = "活动标题")
    private String title;
    @ApiModelProperty(value = "开始时间")
    private String begTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "活动费用")
    private Double actCost;
    @ApiModelProperty(value = "活动地点")
    private String address;
    @ApiModelProperty(value = "人数上限")
    private int personNum;
    @ApiModelProperty(value = "启用状态(1-草稿，2-已发布，3-已撤回)")
    private String enableStatus;
    @ApiModelProperty(value = "小组名称")
    private String groupName;
    @ApiModelProperty(value = "发布对象(1-小组,2-物业)")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Double getActCost() {
        return actCost;
    }

    public void setActCost(Double actCost) {
        this.actCost = actCost;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
