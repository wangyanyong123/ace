package com.github.wxiaoqi.security.jinmao.vo.visitsign;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class ResultVisitListVo implements Serializable {
    private static final long serialVersionUID = -6394497449016094881L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "邀请人")
    private String name;
    @ApiModelProperty(value = "访客")
    private String visitorName;
    @ApiModelProperty(value = "访客联系方式")
    private String phone;
    @ApiModelProperty(value = "访客人数")
    private Integer visitNum;
    @ApiModelProperty(value = "到访时间")
    private String visitTime;
    @ApiModelProperty(value = "项目")
    private String projectName;
    @ApiModelProperty(value = "到访地址")
    private String visitAddr;
    @ApiModelProperty(value = "到访缘由")
    private String visitReason;
    @ApiModelProperty(value = "0-未开车1-开车")
    private String isDrive;
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

}
