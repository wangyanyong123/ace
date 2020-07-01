package com.github.wxiaoqi.security.app.vo.posts.in;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SavePostsParam implements Serializable {

    @ApiModelProperty(value = "帖子标题")
    private String title;
    @ApiModelProperty(value = "帖子描述内容")
    private String description;
    @ApiModelProperty(value = "帖子图片")
    private String postImages;
    @ApiModelProperty(value = "小组id")
    private String groupId;
    @ApiModelProperty(value = "项目id")
    private String projectId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPostImages() {
        return postImages;
    }

    public void setPostImages(String postImages) {
        this.postImages = postImages;
    }
}
