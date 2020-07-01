package com.github.wxiaoqi.security.jinmao.vo.report.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReportPersonVo implements Serializable {

    @ApiModelProperty(value = "举报人")
    private String reportPerson;
    @ApiModelProperty(value = "举报人电话")
    private String contact;
    @ApiModelProperty(value = "举报时间")
    private String reportTime;
    @ApiModelProperty(value = "举报理由")
    private String reportReason;
}
