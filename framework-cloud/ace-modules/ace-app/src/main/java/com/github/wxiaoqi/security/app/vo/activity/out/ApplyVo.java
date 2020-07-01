package com.github.wxiaoqi.security.app.vo.activity.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplyVo implements Serializable {

    @ApiModelProperty(value = "用户昵称")
    private String userName;
    @ApiModelProperty(value = "用户头像")
    private String photo;
    @ApiModelProperty(value = "用户头像(从这获取)")
    private List<ImgInfo> photoList;
    @ApiModelProperty(value = "报名时间")
    private String applyTime;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<ImgInfo> getPhotoList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(photo)){
            String[] imArrayIds = new String[]{photo};
            if(photo.indexOf(",")!= -1){
                imArrayIds = photo.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setPhotoList(List<ImgInfo> photoList) {
        this.photoList = photoList;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}
