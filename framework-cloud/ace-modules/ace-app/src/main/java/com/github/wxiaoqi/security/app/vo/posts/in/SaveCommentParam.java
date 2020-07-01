package com.github.wxiaoqi.security.app.vo.posts.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveCommentParam implements Serializable {

    @ApiModelProperty(value = "评论id")
    private String commentId;
    @ApiModelProperty(value = "对象(帖子、活动、家里人帖子、议事厅话题、社区话题)id")
    private String objectId;
    @ApiModelProperty(value = "评论对象(1-帖子,2-活动,3-家里人帖子，4-议事厅话题，5-社区话题)")
    private String type;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "(1-评论帖子[不传评论id],2-回复评论用户[传评论id])")
    private String commentType;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }
}
