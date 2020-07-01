package com.github.wxiaoqi.security.app.vo.posts.out;


import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostsVo implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String id;
    private String userId;
    @ApiModelProperty(value = "帖子标题")
    private String title;
    @ApiModelProperty(value = "帖子描述内容")
    private String description;
    @ApiModelProperty(value = "帖子图片")
    private String postImage;
    @ApiModelProperty(value = "帖子图片地址(从这list获取帖子图片)")
    private List<ImgInfo> postImageList;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "发帖人昵称")
    private String nickName;
    @ApiModelProperty(value = "发帖人头像")
    private String profilePhoto;
    @ApiModelProperty(value = "发帖人头像图片地址(从这list获取发帖人头像)")
    private List<ImgInfo> profilePhotoList;
    @ApiModelProperty(value = "阅读数")
    private int viewNum;
    @ApiModelProperty(value = "阅读人头像")
    private List<ImgInfo> imgList;
    @ApiModelProperty(value = "评论数")
    private int commentNum;
    @ApiModelProperty(value = "点赞数")
    private int upNum;
    @ApiModelProperty(value = "是否点赞(0-未赞,1-已点赞)")
    private String isUp;

    @ApiModelProperty(value = "小组名称")
    private String groupName;
    @ApiModelProperty(value = "小组logo")
    private String logoImage;
    @ApiModelProperty(value = "小组logo图片地址(从这list获取小组logo)")
    private List<ImgInfo> logoImageList;

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

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
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

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public List<ImgInfo> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgInfo> imgList) {
        this.imgList = imgList;
    }

    public List<ImgInfo> getPostImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(postImage)){
            String[] imArrayIds = new String[]{postImage};
            if(postImage.indexOf(",")!= -1){
                imArrayIds = postImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setPostImageList(List<ImgInfo> postImageList) {
        this.postImageList = postImageList;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getUpNum() {
        return upNum;
    }

    public void setUpNum(int upNum) {
        this.upNum = upNum;
    }

    public String getIsUp() {
        return isUp;
    }

    public void setIsUp(String isUp) {
        this.isUp = isUp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public List<ImgInfo> getLogoImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(logoImage)){
            String[] imArrayIds = new String[]{logoImage};
            if(logoImage.indexOf(",")!= -1){
                imArrayIds = logoImage.split(",");
            }
            for (String url:imArrayIds){
                ImgInfo info = new ImgInfo();
                info.setUrl(url);
                list.add(info);
            }
        }
        return list;
    }

    public void setLogoImageList(List<ImgInfo> logoImageList) {
        this.logoImageList = logoImageList;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
