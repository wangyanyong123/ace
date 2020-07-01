package com.github.wxiaoqi.security.app.vo.group.out;

import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultAppGroupInfoVo implements Serializable {

    @ApiModelProperty(value = "小组ID")
    private String groupId;
    @ApiModelProperty(value = "小组名称")
    private String name;
    @ApiModelProperty(value = "logo")
    private String logoImage;
    @ApiModelProperty(value = "logo地址")
    private List<ImgeInfo> logo;
    @ApiModelProperty(value = "等级")
    private String grade;
    @ApiModelProperty(value = "小组组长")
    private List<ResultLeaderInfoVo> leader;
    @ApiModelProperty(value = "成员数")
    private Integer memberCount;
    @ApiModelProperty(value = "打卡率")
    private Integer signCount;
    @ApiModelProperty(value = "组长人数")
    private Integer leaderCount;
    @ApiModelProperty(value = "简介")
    private String summary;
    @ApiModelProperty(value = "0-未加入1-已加入")
    private String status;
    @ApiModelProperty(value = "0-打卡1-已打卡")
    private String signStatus;
    @ApiModelProperty(value = "小组排名")
    private Integer rank;
    @ApiModelProperty(value = "小组积分")
    private String creditsValue;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getLeaderCount() {
        return leaderCount;
    }

    public void setLeaderCount(Integer leaderCount) {
        this.leaderCount = leaderCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public List<ImgeInfo> getLogo() {
        return logo;
    }

    public void setLogo(List<ImgeInfo> logo) {
        this.logo = logo;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public List<ResultLeaderInfoVo> getLeader() {
        return leader;
    }

    public void setLeader(List<ResultLeaderInfoVo> leader) {
        this.leader = leader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getCreditsValue() {
        return creditsValue;
    }

    public void setCreditsValue(String creditsValue) {
        this.creditsValue = creditsValue;
    }
}
