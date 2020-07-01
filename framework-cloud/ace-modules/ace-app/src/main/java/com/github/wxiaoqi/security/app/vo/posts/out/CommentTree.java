package com.github.wxiaoqi.security.app.vo.posts.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.vo.TreeNodeVO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentTree extends TreeNodeVO<CommentTree> {
 /*   String content;
    String nickName;
    String profilePhoto;
    List<ImgInfo> profilePhotoList;
    String createTime;
    int upNum;
    String isUp;*/
    @ApiModelProperty(value = "评论id")
    private String id;
    @ApiModelProperty(value = "父级评论id")
    private String pid;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "评论人昵称")
    private String nickName;
    @ApiModelProperty(value = "评论人头像")
    private String profilePhoto;
    @ApiModelProperty(value = "评论人头像图片地址(从这list获取评论人头像)")
    private List<ImgInfo> profilePhotoList;
    @ApiModelProperty(value = "评论时间")
    private String createTime;
    @ApiModelProperty(value = "点赞数")
    private int upNum;
    @ApiModelProperty(value = "是否点赞(0-未赞,1-已点赞)")
    private String isUp;

    public CommentTree() {

    }

    public CommentTree(Object id, Object pid, String content, String nickName,List<ImgInfo> profilePhotoList,
                       String profilePhoto,String createTime,int upNum,String isUp) {
        this.content = content;
        this.nickName = nickName;
        this.profilePhoto = profilePhoto;
        this.profilePhotoList = profilePhotoList;
        this.createTime = createTime;
        this.upNum = upNum;
        this.isUp = isUp;
        this.setId(id);
        this.setParentId(pid);
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
}
