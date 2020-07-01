package com.github.wxiaoqi.security.app.vo.decorete.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyDecoreteInfo implements Serializable {

    @ApiModelProperty(value = "工单id")
    private String woId;
    @ApiModelProperty(value = "装修监理id")
    private String id;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
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
    @ApiModelProperty(value = "支付金额")
    private String cost;
    @ApiModelProperty(value = "当前工单工序")
    private String processId;
}
