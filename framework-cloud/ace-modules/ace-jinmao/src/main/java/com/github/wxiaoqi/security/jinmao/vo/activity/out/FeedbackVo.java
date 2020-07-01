package com.github.wxiaoqi.security.jinmao.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class FeedbackVo implements Serializable {

    @ApiModelProperty(value = "反馈id")
    private String id;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户电话")
    private String userTel;
    @ApiModelProperty(value = "反馈内容")
    private String content;
    @ApiModelProperty(value = "来源系统(1-IOS客户端APP,2-IOS员工端APP,3-android客户端APP,4-android员工端APP)")
    private String source;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    private String userId;

    private String userType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
