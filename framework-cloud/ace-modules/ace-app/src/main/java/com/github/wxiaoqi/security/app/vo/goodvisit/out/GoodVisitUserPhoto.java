package com.github.wxiaoqi.security.app.vo.goodvisit.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class GoodVisitUserPhoto implements Serializable {

    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "用户头像")
    private String profilePhoto;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
