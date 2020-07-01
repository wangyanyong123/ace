package com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IncidentType implements Serializable {
    private static final long serialVersionUID = -474215275545143259L;

    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "小区工单信息")
    private List<IncidentTypeCount> woInfo;
}
