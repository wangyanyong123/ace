package com.github.wxiaoqi.security.app.vo.reservation.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ReservationVo implements Serializable {

    @ApiModelProperty(value = "服务id")
    private String id;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "服务封面logo")
    private String reservationLogo;
    @ApiModelProperty(value = "分类名称")
    private String classifyName;
    @ApiModelProperty(value = "分类ID")
    private String classifyId;
    @ApiModelProperty(value = "单价")
    private String price;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "商品标签 ")
    private List<String> label;

    @ApiModelProperty(value = "优惠券信息")
    private String coupon;
}
