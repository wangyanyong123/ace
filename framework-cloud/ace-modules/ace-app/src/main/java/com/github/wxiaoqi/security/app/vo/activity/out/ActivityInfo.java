package com.github.wxiaoqi.security.app.vo.activity.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityInfo implements Serializable {


    @ApiModelProperty(value = "活动id")
    private String id;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "小组id")
    private String groupId;
    @ApiModelProperty(value = "小组名称")
    private String groupName;
    @ApiModelProperty(value = "活动标题")
    private String title;
    @ApiModelProperty(value = "封面")
    private String activitCcover;
    @ApiModelProperty(value = "封面集合(从这获取)")
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
    @ApiModelProperty(value = "活动费用")
    private String actCost;
    @ApiModelProperty(value = "活动联系人名字")
    private String contactorName;
    @ApiModelProperty(value = "活动联系人手机号码")
    private String contactTel;
    @ApiModelProperty(value = "上限人数(-1:无上限)")
    private String personNum;
    @ApiModelProperty(value = "报名参与人数")
    private int applyNum;
    @ApiModelProperty(value = "报名人头像")
    private List<ImgInfo> imgList;
    @ApiModelProperty(value = "帖子评论数量")
    private int commentNum;
    @ApiModelProperty(value = "1-未报名,2-已报名(待支付),3-报名成功(支付成功),4-报名已截止,5-活动已过期,6-报名已报满")
    private String applyStatus;
    @ApiModelProperty(value = "是否可取消报名(0-不可以；1-可以。)")
    private String isCancel;
    private String cancelTime;
    @ApiModelProperty(value = "是否收费(0-收费；1-免费)")
    private String isFree;
    @ApiModelProperty(value = "是否禁止发帖/评论(0-禁止，1-允许)")
    private String isForbid;
    @ApiModelProperty(value = "身份类型(0-不是游客,1-游客,2-运营人员)")
    private String identityType;
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

    public String getActivitCcover() {
        return activitCcover;
    }

    public void setActivitCcover(String activitCcover) {
        this.activitCcover = activitCcover;
    }

    public List<ImgInfo> getActivitCcoverList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(activitCcover)){
            String[] imArrayIds = new String[]{activitCcover};
            if(activitCcover.indexOf(",")!= -1){
                imArrayIds = activitCcover.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
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

    public String getActCost() {
        return actCost;
    }

    public void setActCost(String actCost) {
        this.actCost = actCost;
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

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public List<ImgInfo> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgInfo> imgList) {
        this.imgList = imgList;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getIsForbid() {
        return isForbid;
    }

    public void setIsForbid(String isForbid) {
        this.isForbid = isForbid;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }
}
