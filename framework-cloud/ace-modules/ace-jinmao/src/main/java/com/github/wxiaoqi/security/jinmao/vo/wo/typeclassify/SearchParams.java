package com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchParams implements Serializable {

    private static final long serialVersionUID = 6516183647915931630L;

    @ApiModelProperty(value = "城市ID")
    private String cityId;
    @ApiModelProperty(value = "项目ID")
    private String projectId;
    @ApiModelProperty(value = "工单类型(报修-repair 投诉-cmplain 计划-plan)")
    private String incidentType;
    @ApiModelProperty(value = "分类类型")
    private String classifyType;
    @ApiModelProperty(value = "起止日期(yyyy-MM-dd)")
    private String startTime;
    @ApiModelProperty(value = "结束日期(yyyy-MM-dd)")
    private String endTime;
}
