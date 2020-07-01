package com.github.wxiaoqi.security.jinmao.vo.fosts.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostsInfo implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String id;
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
    @ApiModelProperty(value = "点赞数")
    private int upNum;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getUpNum() {
        return upNum;
    }

    public void setUpNum(int upNum) {
        this.upNum = upNum;
    }


}
