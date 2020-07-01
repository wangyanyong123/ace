package com.github.wxiaoqi.security.app.vo.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FeedbackParam implements Serializable {

    @ApiModelProperty(value = "反馈内容")
    private String content;
    @ApiModelProperty(value = "来源系统（1 ios客户APP 、2 android客户APP）")
    private String source;
}
