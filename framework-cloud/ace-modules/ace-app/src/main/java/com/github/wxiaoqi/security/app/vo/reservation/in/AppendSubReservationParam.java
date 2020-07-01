package com.github.wxiaoqi.security.app.vo.reservation.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppendSubReservationParam implements Serializable {
    @ApiModelProperty(value = "预约订单id")
    private String subId;
    @ApiModelProperty(value = "服务商品id")
    private String reservationId;
    @ApiModelProperty(value = "规格id")
    private String specId;
    @ApiModelProperty(value = "数量")
    private String subNum;
    @ApiModelProperty(value = "总价")
    private String totalPrice;
}
