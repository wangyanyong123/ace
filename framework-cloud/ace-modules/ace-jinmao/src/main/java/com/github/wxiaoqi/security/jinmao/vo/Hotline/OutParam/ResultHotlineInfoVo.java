package com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResultHotlineInfoVo implements Serializable {

    @ApiModelProperty(value = "热线id")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "名称")
    private String name;
    private List<ResultHotlineInfoVo> content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResultHotlineInfoVo> getContent() {
        return content;
    }

    public void setContent(List<ResultHotlineInfoVo> content) {
        this.content = content;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
