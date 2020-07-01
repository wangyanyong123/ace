package com.github.wxiaoqi.security.jinmao.vo.fosts.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class FostsVo implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String description;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "发帖人")
    private String nickName;
    @ApiModelProperty(value = "点赞数")
    private int upNum;
    @ApiModelProperty(value = "显示类型(0=隐藏，1=显示)")
    private String showType;
    @ApiModelProperty(value = "是否置顶(0-未置顶，1-已置顶)")
    private String isTop;
    @ApiModelProperty(value = "帖子类型(1-普通帖子,2-精华帖)")
    private String postsType;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "小组名称")
    private String groupName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUpNum() {
        return upNum;
    }

    public void setUpNum(int upNum) {
        this.upNum = upNum;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getPostsType() {
        return postsType;
    }

    public void setPostsType(String postsType) {
        this.postsType = postsType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
}
