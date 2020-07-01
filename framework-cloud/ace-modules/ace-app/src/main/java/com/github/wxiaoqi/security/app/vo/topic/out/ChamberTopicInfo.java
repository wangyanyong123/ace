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
public class ChamberTopicInfo implements Serializable {


    @ApiModelProperty(value = "议事厅话题id")
    private String id;
    @ApiModelProperty(value = "发帖人id")
    private String userId;
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
    @ApiModelProperty(value = "是否置顶(0-未置顶，1-已置顶)")
    private String isTop;
    @ApiModelProperty(value = "显示类型(0=隐藏，1=显示)")
    private String showType;
    @ApiModelProperty(value = "是否投票(0-未投票,1-已投票)")
    private String isSelect;
    @ApiModelProperty(value = "议事类型(1=话题，2=投票)")
    private String topicType;
    @ApiModelProperty(value = "话题标签名称")
    private String topicTagName;
    @ApiModelProperty(value = "投票状态(1.进行中,2.已截止)")
    private String ballotStatus;
    @ApiModelProperty(value = "投票方式")
    private String selection;
    @ApiModelProperty(value = "投票方式")
    private String selectionStr;
    @ApiModelProperty(value = "截止时间")
    private String endTime;
    @ApiModelProperty(value = "参与人数")
    private int playerNum;
    @ApiModelProperty(value = "投票选项列表")
    private List<SelContentVo> contentVo;

    @ApiModelProperty(value = "身份类型(0-不是游客,1-游客,2-运营人员)")
    private String identityType;

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
