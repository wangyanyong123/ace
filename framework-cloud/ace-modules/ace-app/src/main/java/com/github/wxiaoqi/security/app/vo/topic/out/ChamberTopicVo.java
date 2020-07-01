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
 * @date 2019/8/9
 */
@Data
public class ChamberTopicVo implements Serializable {

    @ApiModelProperty(value = "议事厅话题id")
    private String id;
    @ApiModelProperty(value = "发帖人id")
    private String userId;
    @ApiModelProperty(value = "性别(0-未知,1-男,2-女)")
    private String sex;
    @ApiModelProperty(value = "发帖人")
    private String userName;
    @ApiModelProperty(value = "发帖人头像")
    private String profilePhoto;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "评论数")
    private int commentNum;
    @ApiModelProperty(value = "点赞数")
    private int upNum;
    @ApiModelProperty(value = "是否点赞(0-未赞,1-已点赞)")
    private String isUp;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;
    @ApiModelProperty(value = "议事类型(1=话题，2=投票)")
    private String topicType;
    @ApiModelProperty(value = "话题标签名称")
    private String topicTagName;
    @ApiModelProperty(value = "投票状态(1.进行中,2.已截止)")
    private String ballotStatus;


    @ApiModelProperty(value = "帖子图片")
    private String topicImage;
    @ApiModelProperty(value = "帖子图片集合")
    private List<ImgInfo> postImageList;

    public List<ImgInfo> getPostImageList() {
        List<ImgInfo> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(topicImage)){
            String[] imArrayIds = new String[]{topicImage};
            if(topicImage.indexOf(",")!= -1){
                imArrayIds = topicImage.split(",");
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
