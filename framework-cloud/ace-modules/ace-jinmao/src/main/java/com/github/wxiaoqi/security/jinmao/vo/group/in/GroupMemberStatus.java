package com.github.wxiaoqi.security.jinmao.vo.group.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GroupMemberStatus implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "状态(1-取消，2-加入)")
    private String status;
    @ApiModelProperty(value = "小组ID")
    private String groupId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
