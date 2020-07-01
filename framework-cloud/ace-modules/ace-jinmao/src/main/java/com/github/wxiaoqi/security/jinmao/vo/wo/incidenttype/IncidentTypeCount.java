package com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class IncidentTypeCount implements Serializable {
    private static final long serialVersionUID = -4283394438088204393L;

    private String cityId;
    @ApiModelProperty(value = "城市")
    private String cityName;
    private String projectId;
    @ApiModelProperty(value = "小区")
    private String projectName;
    @ApiModelProperty(value = "报修工单数")
    private String repairCount;
    @ApiModelProperty(value = "投诉工单数")
    private String cmplainCount;
    @ApiModelProperty(value = "计划工单数")
    private String planCount;
    @ApiModelProperty(value = "总数")
    private String total;

}
