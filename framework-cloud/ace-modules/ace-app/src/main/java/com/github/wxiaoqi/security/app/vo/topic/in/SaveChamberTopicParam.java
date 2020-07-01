package com.github.wxiaoqi.security.app.vo.topic.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * @author qs
 * @date 2019/8/9
 */
@Data
public class SaveChamberTopicParam implements Serializable {

    @ApiModelProperty(value = "议事类型(1=话题，2=投票)")
    private String topicType;
    @ApiModelProperty(value = "话题标签id")
    private String tagId;
    @ApiModelProperty(value = "话题内容/投票标题")
    private String content;
    @ApiModelProperty(value = "话题图片(多个图片用逗号隔开)")
    private String topicImages;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "投票选项")
    private String[] selectVo;
    @ApiModelProperty(value = "投票截止时间")
    private String endTime;
    @ApiModelProperty(value = "投票方式(1:单选(默认,可不传)、2:最多选2项、3:最多选3项、4:最多选4项...、20:最多选20项)")
    private String selection;

}
