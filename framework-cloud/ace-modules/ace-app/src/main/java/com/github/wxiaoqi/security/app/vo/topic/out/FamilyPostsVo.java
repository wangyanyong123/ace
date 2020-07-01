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
public class FamilyPostsVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "1-家里人帖子,2-议事厅(话题),3-议事厅(投票),4-邻里活动,5-社区话题,6-业主圈帖子,7-业主圈小组活动")
    private String typeStr;
    private String userId;
    @ApiModelProperty(value = "性别(0-未知,1-男,2-女)")
    private String sex;
    @ApiModelProperty(value = "发帖人")
    private String userName;
    @ApiModelProperty(value = "帖子内容")
    private String content;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "发帖人头像")
    private String profilePhoto;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;

    @ApiModelProperty(value = "议事类型(1=话题，2=投票)")
    private String topicType;
    @ApiModelProperty(value = "类型(1-图片,2-视频)")
    private String imageType;
    @ApiModelProperty(value = "帖子图片/视频")
    private String postImage;
    @ApiModelProperty(value = "视频图片")
    private String videoImage;

    @ApiModelProperty(value = "话题标签名称")
    private String topicTagName;
    @ApiModelProperty(value = "投票状态(1.进行中,2.已截止)")
    private String ballotStatus;

    @ApiModelProperty(value = "帖子描述内容")
    private String description;
    @ApiModelProperty(value = "小组名称")
    private String groupName;
    @ApiModelProperty(value = "阅读人头像")
    private List<ImgInfo> imgList;

    @ApiModelProperty(value = "活动地点")
    private String address;
    @ApiModelProperty(value = "活动报名截止时间")
    private String applyEndTime;
    @ApiModelProperty(value = "活动内容")
    private String summary;
    @ApiModelProperty(value = "活动状态(1-未开始,2-进行中,3-已过期)")
    private String activityStatus;
    @ApiModelProperty(value = "活动发起人")
    private String contactorName;
    @ApiModelProperty(value = "报名人数")
    private int applyNum;
    @ApiModelProperty(value = "1-未报名,2-已报名(待支付)," +
            "3-报名成功(支付成功),4-报名已截止,5-活动已过期,6-报名已报满" +
            "7-取消报名退款审核中，8-取消报名退款完成，9-取消报名拒绝退款")
    private String applyStatus;
    @ApiModelProperty(value = "上限人数(-1:无上限)")
    private String personNum;

    @ApiModelProperty(value = "阅读数")
    private int viewNum;
    @ApiModelProperty(value = "评论数")
    private int commentNum;
    @ApiModelProperty(value = "点赞数")
    private int upNum;
    @ApiModelProperty(value = "是否点赞(0-未赞,1-已点赞)")
    private String isUp;

    @ApiModelProperty(value = "帖子图片集合")
    private List<ImgInfo> postImageList;

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
