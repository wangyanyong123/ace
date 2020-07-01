package com.github.wxiaoqi.security.jinmao.vo.wo.date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WoOnDateCount implements Serializable {
    private static final long serialVersionUID = 8274436713418661850L;
    private String projectId;
    @ApiModelProperty(value = "小区")
    private String projectName;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "报修数量")
    private String repairCount;
    @ApiModelProperty(value = "投诉数量")
    private String cmplainCount;
    @ApiModelProperty(value = "计划数量")
    private String planCount;
    @ApiModelProperty(value = "总数")
    private String total;
}
