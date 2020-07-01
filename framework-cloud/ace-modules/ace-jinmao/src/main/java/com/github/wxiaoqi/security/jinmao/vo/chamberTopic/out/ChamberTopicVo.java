package com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/7
 */
@Data
public class ChamberTopicVo implements Serializable {

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
    @ApiModelProperty(value = "显示类型(0=隐藏，1=显示)")
    private String showType;
    @ApiModelProperty(value = "是否置顶(0-未置顶，1-已置顶)")
    private String isTop;
    @ApiModelProperty(value = "所属项目")
    private String projectName;
    @ApiModelProperty(value = "议事类型(1=话题，2=投票)")
    private String topicType;
    @ApiModelProperty(value = "话题标签名称")
    private String topicTagName;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;
}
