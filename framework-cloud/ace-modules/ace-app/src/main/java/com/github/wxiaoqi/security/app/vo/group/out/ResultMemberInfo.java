package com.github.wxiaoqi.security.app.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultMemberInfo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "小组ID")
    private String groupId;
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "关注状态(1-已关注,2-取消关注)")
    private String followStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }
}
