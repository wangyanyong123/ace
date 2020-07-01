package com.github.wxiaoqi.security.jinmao.vo.statement.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatisticsVo implements Serializable {

    @ApiModelProperty(value = "商户id")
    private String id;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;
    @ApiModelProperty(value = "统计日期")
    private String dateTime;
    @ApiModelProperty(value = "结算金额")
    private String actualCost;
}
