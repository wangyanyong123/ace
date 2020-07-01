package com.github.wxiaoqi.security.app.vo.reservation.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateSubReservationParam implements Serializable {
    @ApiModelProperty(value = "预约订单id")
    private String subId;
    @ApiModelProperty(value = "服务商品id")
    private String reservationId;
    @ApiModelProperty(value = "预约日期")
    private String expectedServiceTime;
}
