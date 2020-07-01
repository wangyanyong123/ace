package com.github.wxiaoqi.security.app.vo.reservation.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyReservationVo implements Serializable {

    @ApiModelProperty(value = "预约id")
    private String id;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)")
    private String dealStatus;
    @ApiModelProperty(value = "工单id")
    private String woId;
}
