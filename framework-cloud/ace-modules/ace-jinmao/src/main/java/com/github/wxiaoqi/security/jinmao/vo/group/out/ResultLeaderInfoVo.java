package com.github.wxiaoqi.security.jinmao.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultLeaderInfoVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "小组Id")
    private String groupId;
    @ApiModelProperty(value = "组长ID")
    private String userId;
    @ApiModelProperty(value = "组长名称")
    private String leaderName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
}
