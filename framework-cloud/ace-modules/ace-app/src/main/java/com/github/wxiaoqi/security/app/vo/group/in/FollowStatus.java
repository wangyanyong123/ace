package com.github.wxiaoqi.security.app.vo.group.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class FollowStatus implements Serializable {

    @ApiModelProperty(value = "小组ID")
    private String groupId;
    @ApiModelProperty(value = "关注状态(1-关注,2-取消关注)")
    private String followStatus;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }
}
