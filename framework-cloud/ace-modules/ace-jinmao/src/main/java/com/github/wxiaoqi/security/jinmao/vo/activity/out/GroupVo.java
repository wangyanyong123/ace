package com.github.wxiaoqi.security.jinmao.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GroupVo implements Serializable {

    @ApiModelProperty(value = "小组id")
    private String groupId;
    @ApiModelProperty(value = "小组名称")
    private String groupName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
