package com.github.wxiaoqi.security.app.vo.feedback;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveFeedback implements Serializable {


    @ApiModelProperty(value = "项目id(客户端APP,就传)")
    private String projectId;
    @ApiModelProperty(value = "房屋id(客户端APP认证房屋了,就传)")
    private String hourseId;
    @ApiModelProperty(value = "反馈内容")
    private String content;
    @ApiModelProperty(value = "来源系统(1-IOS客户端APP,2-IOS员工端APP,3-android客户端APP,4-android员工端APP)")
    private String source;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getHourseId() {
        return hourseId;
    }

    public void setHourseId(String hourseId) {
        this.hourseId = hourseId;
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
}
