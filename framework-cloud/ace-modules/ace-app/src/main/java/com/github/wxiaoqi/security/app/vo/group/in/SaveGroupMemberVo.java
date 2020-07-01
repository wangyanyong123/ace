package com.github.wxiaoqi.security.app.vo.group.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveGroupMemberVo implements Serializable {

    @ApiModelProperty(value = "主键id(不用传)")
    private String id;
    @ApiModelProperty(value = "小组ID")
    private String groupId;

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
}
