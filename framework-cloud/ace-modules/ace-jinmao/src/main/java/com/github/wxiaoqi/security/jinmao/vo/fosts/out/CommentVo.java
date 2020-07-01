package com.github.wxiaoqi.security.jinmao.vo.fosts.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class CommentVo implements Serializable {

    @ApiModelProperty(value = "评论id")
    private String id;
    @ApiModelProperty(value = "父级评论id")
    private String pid;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "用户名")
    private String nickName;
    @ApiModelProperty(value = "用户手机号")
    private String mobilePhone;
    @ApiModelProperty(value = "评论时间")
    private String createTime;
    @ApiModelProperty(value = "显示类型(0:已隐藏、1:已显示)")
    private String showType;
    @ApiModelProperty(value = "显示级别")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
