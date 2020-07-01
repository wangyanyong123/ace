package com.github.wxiaoqi.security.jinmao.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

public class ResultGroupMemberVo {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "小组ID")
    private String groupId;
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "手机")
    private String phone;
    @ApiModelProperty(value = "组员状态(1-非组长，2-组长)")
    private String memberStatus;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }
}
