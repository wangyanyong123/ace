package com.github.wxiaoqi.security.jinmao.vo.coupon.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CouponListVo implements Serializable {
    private static final long serialVersionUID = 3963247672099612788L;

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;
    @ApiModelProperty(value = "优惠券类型")
    private String couponType;
    @ApiModelProperty(value = "数量")
    private String amount;
    @ApiModelProperty(value = "适用范围")
    private String projectName;
    @ApiModelProperty(value = "生成时间")
    private String createTime;
    @ApiModelProperty(value = "开始时间")
    private String startUseTime;
    @ApiModelProperty(value = "结束时间")
    private String endUseTime;
    @ApiModelProperty(value = "使用状态(0-待发布1-已发布2-使用中3-已下架4-已过期5-全部)")
    private String useStatus;


}
