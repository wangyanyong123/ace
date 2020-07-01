package com.github.wxiaoqi.security.jinmao.vo.report.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReportVo implements Serializable {

    @ApiModelProperty(value = "举报id")
    private String reportId;
    @ApiModelProperty(value = "帖子/活动标题")
    private String title;
    @ApiModelProperty(value = "帖子内容")
    private String description;
    @ApiModelProperty(value = "帖子/活动id")
    private String postsId;
    @ApiModelProperty(value = "评论id")
    private String commentId;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "评论时间")
    private String commentTime;
    @ApiModelProperty(value = "被举报人id")
    private String beUserId;
    @ApiModelProperty(value = "被举报人")
    private String beReportPerson;
    @ApiModelProperty(value = "被举报人电话")
    private String beReportTel;
    @ApiModelProperty(value = "举报对象(1-帖子,2-帖子评论,3-活动评论)")
    private String reportType;
    @ApiModelProperty(value = "举报次数")
    private int reportCount;
    @ApiModelProperty(value = "是否允许评论/发帖，0:否、1:是")
    private String isForbid;
    @ApiModelProperty(value = "是否反馈，0:否、1:是")
    private String isFeedback;
    @ApiModelProperty(value = "是否显示，0:否、1:是")
    private String showType;
    @ApiModelProperty(value = "小组名称")
    private String groupName;
    @ApiModelProperty(value = "举报时间")
    private String reportTime;

}
