package com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultIncidentType implements Serializable {
    private static final long serialVersionUID = 1875380293768660604L;

    @ApiModelProperty(value = "表格信息")
    private List<IncidentType> info;
    @ApiModelProperty(value = "柱形图数据")
    private DataSet dataSet;
    int total;
}
