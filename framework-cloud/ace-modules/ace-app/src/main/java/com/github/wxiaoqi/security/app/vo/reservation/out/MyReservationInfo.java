package com.github.wxiaoqi.security.app.vo.reservation.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyReservationInfo implements Serializable {

    @ApiModelProperty(value = "工单id")
    private String woId;
    @ApiModelProperty(value = "商户id")
    private String companyId;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "服务封面logo")
    private String reservationLogo;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "预约单号")
    private String reservationNum;
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
    @ApiModelProperty(value = "当前工单工序")
    private String processId;
}
