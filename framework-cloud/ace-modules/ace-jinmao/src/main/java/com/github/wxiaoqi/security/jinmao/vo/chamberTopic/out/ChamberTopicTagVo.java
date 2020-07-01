package com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qs
 * @date 2019/8/5
 */
@Data
public class ChamberTopicTagVo implements Serializable {

    @ApiModelProperty(value = "话题标签id")
    private String id;
    @ApiModelProperty(value = "话题标签编码")
    private String topicTagCode;
    @ApiModelProperty(value = "话题标签名称")
    private String topicTagName;
    @ApiModelProperty(value = "排序")
    private String viewSort;
    @ApiModelProperty(value = "所属项目")
    private String projectNames;
}
