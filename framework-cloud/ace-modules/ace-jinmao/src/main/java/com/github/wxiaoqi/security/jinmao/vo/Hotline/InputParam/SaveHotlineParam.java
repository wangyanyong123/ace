package com.github.wxiaoqi.security.jinmao.vo.Hotline.InputParam;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SaveHotlineParam implements Serializable {

    @ApiModelProperty(value = "热线id")
    private String id;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "名称与电话的对象")
    private List<Map<String,String>> content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String, String>> getContent() {
        return content;
    }

    public void setContent(List<Map<String, String>> content) {
        this.content = content;
    }
}
