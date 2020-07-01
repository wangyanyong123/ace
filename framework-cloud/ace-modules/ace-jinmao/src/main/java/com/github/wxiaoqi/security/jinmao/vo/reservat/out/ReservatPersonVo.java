package com.github.wxiaoqi.security.jinmao.vo.reservat.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReservatPersonVo implements Serializable {


    @ApiModelProperty(value = "工单id")
    private String woId;
    @ApiModelProperty(value = "预约id")
    private String id;
    @ApiModelProperty(value = "预约单号")
    private String reservationNum;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "所属分类")
    private String classifyName;
    @ApiModelProperty(value = "联系人")
    private String contactorName;
    @ApiModelProperty(value = "联系人方式")
    private String contactTel;
    @ApiModelProperty(value = "联系地址")
    private String address;
    @ApiModelProperty(value = "预约时间")
    private String reservationTime;
    @ApiModelProperty(value = "工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)")
    private String dealStatus;



}
