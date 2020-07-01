package com.github.wxiaoqi.security.app.vo.decorete.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DecoreteApplyParam implements Serializable {

    @ApiModelProperty(value = "联系人")
    private String contactorName;
    @ApiModelProperty(value = "联系人方式")
    private String contactTel;
    @ApiModelProperty(value = "联系地址")
    private String address;
    @ApiModelProperty(value = "装修阶段")
    private String decoreteStage;
    @ApiModelProperty(value = "建筑面积")
    private String coveredArea;
    @ApiModelProperty(value = "服务价格")
    private String servicePrice;
    @ApiModelProperty(value = "装修监理id")
    private String decoreteId;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "房屋id")
    private String roomId;







}
