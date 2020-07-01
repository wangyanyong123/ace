package com.github.wxiaoqi.security.jinmao.vo.report.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FeedbackVo implements Serializable {

    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "用户联系方式")
    private String contact;
    @ApiModelProperty(value = "反馈内容")
    private String content;
    @ApiModelProperty(value = "反馈来源")
    private String source;
    @ApiModelProperty(value = "反馈时间")
    private String feedbackTime;
}
