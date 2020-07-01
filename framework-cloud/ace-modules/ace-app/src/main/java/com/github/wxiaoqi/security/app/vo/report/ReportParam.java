package com.github.wxiaoqi.security.app.vo.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReportParam implements Serializable {

    @ApiModelProperty(value = "举报对象(1-帖子(传帖子id),2-帖子评论(传帖子id和评论id),3-活动评论(传活动id和评论id))")
    private String type;
    @ApiModelProperty(value = "帖子/活动id")
    private String postsId;
    @ApiModelProperty(value = "评论id(帖子/活动)")
    private String commentId;
    @ApiModelProperty(value = "举报理由")
    private String reportReason;

}
