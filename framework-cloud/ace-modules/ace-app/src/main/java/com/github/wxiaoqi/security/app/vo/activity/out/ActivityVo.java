package com.github.wxiaoqi.security.app.vo.activity.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityVo implements Serializable {

    @ApiModelProperty(value = "活动id")
    private String id;
    @ApiModelProperty(value = "活动标题")
    private String title;
    @ApiModelProperty(value = "活动地点")
    private String address;
    @ApiModelProperty(value = "活动报名截止时间")
    private String applyEndTime;
    @ApiModelProperty(value = "活动内容")
    private String summary;
    @ApiModelProperty(value = "活动封面")
    private String activitCcover;
    @ApiModelProperty(value = "活动封面集合(从这获取图片)")
    private List<ImgInfo> activitCcoverList;
    @ApiModelProperty(value = "活动状态(1-未开始,2-进行中,3-已过期)")
    private String activityStatus;
    @ApiModelProperty(value = "活动发起人")
    private String contactorName;
    @ApiModelProperty(value = "报名人数")
    private int applyNum;
    @ApiModelProperty(value = "报名人头像")
    private List<ImgInfo> imgList;
    @ApiModelProperty(value = "1-未报名,2-已报名(待支付)," +
            "3-报名成功(支付成功),4-报名已截止,5-活动已过期,6-报名已报满" +
            "7-取消报名退款审核中，8-取消报名退款完成，9-取消报名拒绝退款")
    private String applyStatus;
    @ApiModelProperty(value = "上限人数(-1:无上限)")
    private String personNum;
    @ApiModelProperty(value = "小组名称")
    private String groupName;
    @ApiModelProperty(value = "发布对象(1-小组,2-物业)")
    private String type;
    private String status;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
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

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactorName() {
        return contactorName;
    }

    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }
}
