package com.github.wxiaoqi.security.jinmao.vo.wo.date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultWoOnDate implements Serializable {
    private static final long serialVersionUID = 5586180985078423488L;

    @ApiModelProperty(value = "表格信息")
    private List<WoOnDateCount> info;
    @ApiModelProperty(value = "折线图数据")
    private ResultDateData dataSet;
    int total;
}
