package com.github.wxiaoqi.security.app.reservation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 预约服务订单表
 * 
 * @author huangxl
 * @Date 2020-04-19 17:01:24
 */
@Data
public class ReservationOrderVO implements Serializable {
	private static final long serialVersionUID = 1L;

	//父订单id，与支付记录一致，与order_id 相同时表示没有拆单
    private String parentId;
	    //商户id 未支付时取第一个商品的商户
    private String tenantId;
	    //订单编号
    private String orderCode;

    @NotEmpty(message="缺少参数projectId")
    @ApiModelProperty(value = "项目ID")
    private String projectId;

    @NotEmpty(message="缺少参数procCode")
    @ApiModelProperty(value = "收货地址省编码")
    private String procCode;
	    //
    private String userId;
	    //订单类型 1：普通订单；2：团购订单。3：秒杀订单
    private Integer orderType;
	    //订单状态 5：待支付，10：待受理；15：待上门；20:服务中；25：待评价；30：已完成；35：退款中 ；40：已关闭；
    private Integer orderStatus;
	    //退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
    private Integer refundStatus;
	
	    //订单标题
    private String title;
	
	    //订单描述
    private String description;
	
    @NotEmpty(message="缺少参数appType")
    @ApiModelProperty(value = "下单应用类型 H5:10,微信小程序：20；安卓：30.ios：40")
    private Integer appType;
	
	    //商品总金额
    private BigDecimal productPrice;
	
    @NotNull(message="缺少参数appType")
    @ApiModelProperty(value = "预约服务时间")
    private Date reservationTime;
	
	    //实收金额=商品总金额-优惠金额
    private BigDecimal actualPrice;
	
	    //优惠金额
    private BigDecimal discountPrice;
	
	    //商品总件数
    private Integer quantity;
	
	    //发票类型(0-不开发票,1-个人,2-公司)
    private Integer invoiceType;
	
	    //发票名称
    private String invoiceName;
	
	    //税号
    private String dutyCode;
	
    @NotEmpty(message="缺少参数contactName")
    @ApiModelProperty(value = "收获联系人")
    private String contactName;
	
    @NotEmpty(message="缺少参数contactTel")
    @ApiModelProperty(value = "收货人联系电话")
    private String contactTel;
	
    //
    @NotEmpty(message="缺少参数deliveryAddr")
    @ApiModelProperty(value = "收货地址")
    private String deliveryAddr;
	
	    //备注
    private String remark;
	
	    //最后一次发货时间
    private Date sendTime;
}
