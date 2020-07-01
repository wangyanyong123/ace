package com.github.wxiaoqi.security.app.vo.posts.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostsInfo implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String id;
    private String userId;
    @ApiModelProperty(value = "帖子标题")
    private String title;
    @ApiModelProperty(value = "帖子图片")
    private String postImage;
    @ApiModelProperty(value = "帖子图片地址(从这list获取帖子图片)")
    private List<ImgInfo> postImageList;
    @ApiModelProperty(value = "帖子描述内容")
    private String description;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "发帖人昵称")
    private String nickName;
    @ApiModelProperty(value = "发帖人头像")
    private String profilePhoto;
    @ApiModelProperty(value = "发帖人头像图片地址(从这list获取发帖人头像)")
    private List<ImgInfo> profilePhotoList;
    @ApiModelProperty(value = "帖子评论数量")
    private int commentNum;
    @ApiModelProperty(value = "是否点赞(0-未赞,1-已点赞)")
    private String isUp;
    @ApiModelProperty(value = "点赞数")
    private int upNum;
    @ApiModelProperty(value = "是否置顶(0-未置顶，1-已置顶)")
    private String isTop;
    @ApiModelProperty(value = "帖子类型(1-普通帖子,2-精华帖)")
    private String postsType;
    @ApiModelProperty(value = "是否是组长(0-不是，1-是)")
    private String isGroup;
    @ApiModelProperty(value = "是否禁止发帖/评论(0-禁止，1-允许)")
    private String isForbid;
    @ApiModelProperty(value = "身份类型(0-不是游客,1-游客)")
    private String identityType;
    @ApiModelProperty(value = "显示类型(0=隐藏，1=显示)")
    private String showType;
    private String projectId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
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

    public String getIsUp() {
        return isUp;
    }

    public void setIsUp(String isUp) {
        this.isUp = isUp;
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

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getIsForbid() {
        return isForbid;
    }

    public void setIsForbid(String isForbid) {
        this.isForbid = isForbid;
    }


    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
