package com.github.wxiaoqi.security.app.vo.group.out;

import com.github.wxiaoqi.security.app.vo.goodvisit.out.ImgeInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultLeaderInfoVo implements Serializable {

    @ApiModelProperty(value = "组长ID")
    private String userId;
    @ApiModelProperty(value = "组长名称")
    private String leaderName;
    @ApiModelProperty(value = "组长头像")
    private String leaderPhoto;
    @ApiModelProperty(value = "头像地址")
    private List<ImgeInfo> photo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderPhoto() {
        return leaderPhoto;
    }

    public void setLeaderPhoto(String leaderPhoto) {
        this.leaderPhoto = leaderPhoto;
    }

    public List<ImgeInfo> getPhoto() {
        List<ImgeInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(leaderPhoto)){
            String[] imArrayIds = new String[]{leaderPhoto};
            if(leaderPhoto.indexOf(",")!= -1){
                imArrayIds = leaderPhoto.split(",");
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
