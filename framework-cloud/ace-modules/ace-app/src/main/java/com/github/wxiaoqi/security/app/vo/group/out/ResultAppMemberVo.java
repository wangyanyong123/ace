package com.github.wxiaoqi.security.app.vo.group.out;

import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultAppMemberVo implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "用户昵称")
    private String userName;
    @ApiModelProperty(value = "用户头像")
    private String userPhoto;
    @ApiModelProperty(value = "头像地址")
    private List<ImgeInfo> photo;
    @ApiModelProperty(value = "是否关注(1-关注，2-取消关注)")
    private String followStatus;

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public List<ImgeInfo> getPhoto() {
        List<ImgeInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(userPhoto)){
            String[] imArrayIds = new String[]{userPhoto};
            if(userPhoto.indexOf(",")!= -1){
                imArrayIds = userPhoto.split(",");
            }
            for (String url:imArrayIds){
                ImgeInfo info = new ImgeInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setPhoto(List<ImgeInfo> photo) {
        this.photo = photo;
    }
}
