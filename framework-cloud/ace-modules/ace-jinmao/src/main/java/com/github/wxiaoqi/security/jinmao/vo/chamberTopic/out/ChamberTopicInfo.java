package com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out;

import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qs
 * @date 2019/8/7
 */
@Data
public class ChamberTopicInfo implements Serializable {

    @ApiModelProperty(value = "议事厅话题id")
    private String id;
    @ApiModelProperty(value = "发帖人id")
    private String userId;
    @ApiModelProperty(value = "发帖人")
    private String userName;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "截止时间")
    private String endTime;
    @ApiModelProperty(value = "帖子统计点赞数")
    private int upNum;
    @ApiModelProperty(value = "参与人数")
    private int playerNum;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;
    @ApiModelProperty(value = "所属项目id")
    private String projectId;
    @ApiModelProperty(value = "所属项目")
    private String projectName;
    @ApiModelProperty(value = "议事类型(1=话题，2=投票)")
    private String topicType;
    @ApiModelProperty(value = "话题标签id")
    private String tagId;
    @ApiModelProperty(value = "话题标签名称")
    private String topicTagName;
    @ApiModelProperty(value = "投票状态(1.进行中,2.已截止)")
    private String ballotStatus;
    @ApiModelProperty(value = "投票选项")
    private List<SelContentVo> contentVo;
    @ApiModelProperty(value = "帖子图片")
    private String topicImage;
    private String selection;
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

    public String getSelection() {
        String selectionStr = null;
        String a1 = "1";
        if(a1.equals(selection)){
            selectionStr = "单选";
        }else{
            selectionStr = "最多选"+selection+"项";
        }
        return selectionStr;
    }
}
