package com.github.wxiaoqi.security.app.vo.reservation.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReservatPersonInfo implements Serializable {

    @ApiModelProperty(value = "工单id")
    private String woId;
    @ApiModelProperty(value = "服务id")
    private String id;
    @ApiModelProperty(value = "预约单号")
    private String reservationNum;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "所属分类")
    private String classifyName;
    @ApiModelProperty(value = "预约时间")
    private String reservationTime;
    @ApiModelProperty(value = "客户")
    private String contactorName;
    @ApiModelProperty(value = "客户电话")
    private String contactTel;
    @ApiModelProperty(value = "客户地址")
    private String address;
    @ApiModelProperty(value = "工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)")
    private String dealStatus;
    @ApiModelProperty(value = "处理人")
    private String handleName;
    @ApiModelProperty(value = "处理时间")
    private String handleTime;
    @ApiModelProperty(value = "备注")
    private String description;
    @ApiModelProperty(value = "商户名称")
    private String tenantName;
    @ApiModelProperty(value = "服务范围")
    private String projectName;
    @ApiModelProperty(value = "当前工单工序")
    private String processId;
    @ApiModelProperty(value = "是否可以转单(0-不可以，1-可以)")
    private String isTurn;
    @ApiModelProperty(value = "当前工单处理人")
    private String handleId;


}
