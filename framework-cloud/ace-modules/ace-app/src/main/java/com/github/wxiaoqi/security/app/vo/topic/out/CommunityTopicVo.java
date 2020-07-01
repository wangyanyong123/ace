package com.github.wxiaoqi.security.app.vo.topic.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/5
 */
@Data
public class CommunityTopicVo implements Serializable {

    @ApiModelProperty(value = "社区话题id")
    private String id;
    private String userId;
    @ApiModelProperty(value = "发帖人")
    private String userName;
    @ApiModelProperty(value = "话题标题")
    private String title;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "发帖人头像")
    private String profilePhoto;
    @ApiModelProperty(value = "话题图片")
    private String postImage;

    @ApiModelProperty(value = "评论数")
    private int commentNum;
    @ApiModelProperty(value = "点赞数")
    private int upNum;
    @ApiModelProperty(value = "是否点赞(0-未赞,1-已点赞)")
    private String isUp;

    @ApiModelProperty(value = "话题图片集合")
    private List<ImgInfo> postImageList;

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

}
