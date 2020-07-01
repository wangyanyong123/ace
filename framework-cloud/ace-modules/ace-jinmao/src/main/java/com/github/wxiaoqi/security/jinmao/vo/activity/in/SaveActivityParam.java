package com.github.wxiaoqi.security.jinmao.vo.activity.in;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.GroupVo;
import com.github.wxiaoqi.security.jinmao.vo.activity.out.ProjectVo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class SaveActivityParam implements Serializable {

    @ApiModelProperty(value = "活动id")
    private String id;
    @ApiModelProperty(value = "项目id")
    private List<ProjectVo> projectList;
    @ApiModelProperty(value = "小组id")
    private List<GroupVo> groupList;
    @ApiModelProperty(value = "活动标题")
    private String title;
    @ApiModelProperty(value = "封面")
    private List<ImgInfo> activitCcoverList;
    @ApiModelProperty(value = "活动内容")
    private String summary;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "活动开始时间")
    private String begTime;
    @ApiModelProperty(value = "活动结束时间")
    private String endTime;
    @ApiModelProperty(value = "报名截止时间")
    private String applyEndTime;
    @ApiModelProperty(value = "是否收费")
    private String isFree;
    @ApiModelProperty(value = "活动费用")
    private String actCost;
    @ApiModelProperty(value = "是否可取消")
    private String isCancel;
    @ApiModelProperty(value = "开始前可取消时间")
    private String cancelTime;
    @ApiModelProperty(value = "活动联系人名字")
    private String contactorName;
    @ApiModelProperty(value = "活动联系人手机号码")
    private String contactTel;
    @ApiModelProperty(value = "人数上限")
    private String personNum;
    @ApiModelProperty(value = "发布对象(1-小组,2-物业)")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProjectVo> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectVo> projectList) {
        this.projectList = projectList;
    }

    public List<GroupVo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupVo> groupList) {
        this.groupList = groupList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImgInfo> getActivitCcoverList() {
        return activitCcoverList;
    }

    public void setActivitCcoverList(List<ImgInfo> activitCcoverList) {
        this.activitCcoverList = activitCcoverList;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getActCost() {
        return actCost;
    }

    public void setActCost(String actCost) {
        this.actCost = actCost;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getContactorName() {
        return contactorName;
    }

    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
