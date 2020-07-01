package com.github.wxiaoqi.security.jinmao.vo.stat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author qs
 * @date 2019/8/20
 */
@Data
public class StatTopicVo implements Serializable {

    @ApiModelProperty(value = "发帖人id")
    private String userId;
    @ApiModelProperty(value = "发帖人")
    private String userName;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "发布时间")
    private String createTime;
    @ApiModelProperty(value = "帖子统计点赞数")
    private int upNum;
    @ApiModelProperty(value = "等级头衔")
    private String gradeTitle;
    private String tagName;

    private String topicType;

    private String groupName;
    @ApiModelProperty(value = "pv数")
    private int pv;
    @ApiModelProperty(value = "uv数")
    private int uv;

}
