package com.github.wxiaoqi.security.app.biz.order.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商户信息
 * @author: guohao
 * @create: 2020-04-18 13:48
 **/
@Data
public class ReservationTenantInfo implements Serializable {

    private static final long serialVersionUID = -3650399961427691786L;

    @ApiModelProperty(value = "前台传递的该商户应付金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "商户id")
    private String tenantId;

    @ApiModelProperty(value = "商家下单商品金额")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "运费")
    private BigDecimal expressPrice;

    @ApiModelProperty(value = "店铺备注")
    private String remark;

    @ApiModelProperty(value = "订购产品信息")
    private ReservationInfo reservationInfo;

    @ApiModelProperty(value = "发票信息")
    private InvoiceInfo invoiceInfo;

    @ApiModelProperty(value = "优惠信息")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "优惠信息")
    private DiscountInfo discountInfo;

    @ApiModelProperty(value = "预约时间")
    private Date reservationTime;
}
