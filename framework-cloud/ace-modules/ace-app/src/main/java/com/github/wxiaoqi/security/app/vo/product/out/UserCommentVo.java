package com.github.wxiaoqi.security.app.vo.product.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserCommentVo implements Serializable {

      @ApiModelProperty(value = "用户昵称")
      private String nickName;
      @ApiModelProperty(value = "用户头像")
      private String profilePhoto;
      @ApiModelProperty(value = "用户头像集合(从这获取)")
      private List<ImgInfo> profilePhotoList;
      @ApiModelProperty(value = "评价时间")
      private String createTime;
      @ApiModelProperty(value = "评价内容")
      private String description;
      @ApiModelProperty(value = "综合评价")
      private Integer appraisalVal;
      @ApiModelProperty(value = "评价图片")
      private String imgUrl;
      @ApiModelProperty(value = "评价图片集合(从这获取)")
      private List<ImgInfo> imgUrlList;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<ImgInfo> getProfilePhotoList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(profilePhoto)){
            String[] imArrayIds = new String[]{profilePhoto};
            if(profilePhoto.indexOf(",")!= -1){
                imArrayIds = profilePhoto.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setProfilePhotoList(List<ImgInfo> profilePhotoList) {
        this.profilePhotoList = profilePhotoList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAppraisalVal() {
        return appraisalVal;
    }

    public void setAppraisalVal(Integer appraisalVal) {
        this.appraisalVal = appraisalVal;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<ImgInfo> getImgUrlList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(imgUrl)){
            String[] imArrayIds = new String[]{imgUrl};
            if(imgUrl.indexOf(",")!= -1){
                imArrayIds = imgUrl.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setImgUrlList(List<ImgInfo> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }
}
