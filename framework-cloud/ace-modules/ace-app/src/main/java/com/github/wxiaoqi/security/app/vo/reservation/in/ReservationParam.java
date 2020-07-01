package com.github.wxiaoqi.security.app.vo.reservation.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReservationParam implements Serializable {

    @ApiModelProperty(value = "联系人")
    private String contactorName;
    @ApiModelProperty(value = "联系人方式")
    private String contactTel;
    @ApiModelProperty(value = "联系地址")
    private String address;
    @ApiModelProperty(value = "预约时间")
    private String reservationTime;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "服务id")
    private String reservationId;
    @ApiModelProperty(value = "分类id")
    private String classifyId;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "房屋id")
    private String roomId;


}
