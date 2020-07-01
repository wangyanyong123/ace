package com.github.wxiaoqi.security.merchant.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 预约服务订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 13:13:42
 */
@Data
@Table(name = "biz_reservation_order")
public class BizReservationOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	@Id
	private String id;

	//商户id 未支付时取第一个商品的商户
	@Column(name = "tenant_id")
	private String tenantId;

	//订单编号
	@Column(name = "order_code")
	private String orderCode;

	// 项目ID
	@Column(name = "project_id")
	private String projectId;

	// 用户ID
	@Column(name = "user_id")
	private String userId;

	//订单类型 1：普通订单；2：团购订单。3：秒杀订单
	@Column(name = "order_type")
	private Integer orderType;

	//订单状态 5：待支付，10：待受理；15：待上门；20:服务中；25：待评价；30：已完成；35：退款中 ；40：已关闭；
	@Column(name = "order_status")
	private Integer orderStatus;

	//退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
	@Column(name = "refund_status")
	private Integer refundStatus;

	//是否评论 0：未评论，1.已评论
	@Column(name = "comment_status")
	private Integer commentStatus;

	//订单标题
	@Column(name = "title")
	private String title;

	//订单描述
	@Column(name = "description")
	private String description;

	//下单应用类型 H5:10,微信小程序：20；安卓：30.ios：40
	@Column(name = "app_type")
	private Integer appType;

	//商品总金额
	@Column(name = "product_price")
	private BigDecimal productPrice;

	//预约服务时间
	@Column(name = "reservation_Time")
	private Date reservationTime;

	//实收金额=商品总金额-优惠金额
	@Column(name = "actual_price")
	private BigDecimal actualPrice;

	//优惠金额
	@Column(name = "discount_price")
	private BigDecimal discountPrice;



	//发票类型(0-不开发票,1-个人,2-公司)
	@Column(name = "invoice_type")
	private Integer invoiceType;

	//发票名称
	@Column(name = "invoice_name")
	private String invoiceName;

	//税号
	@Column(name = "duty_code")
	private String dutyCode;

	//收获联系人
	@Column(name = "contact_name")
	private String contactName;

	//收货人联系电话
	@Column(name = "contact_tel")
	private String contactTel;

	//收货地址
	@Column(name = "delivery_addr")
	private String deliveryAddr;

	//备注
	@Column(name = "remark")
	private String remark;

	//
	@Column(name = "create_by")
	private String createBy;

	//
	@Column(name = "create_time")
	private Date createTime;

	//
	@Column(name = "modify_by")
	private String modifyBy;

	//
	@Column(name = "modify_time")
	private Date modifyTime;

	//
	@Column(name = "delete_time")
	private Date deleteTime;

	//数据状态 1：有效；0：无效
	@Column(name = "status")
	private String status;

	//确认完成时间
	@Column(name = "confirm_time")
	private Date confirmTime;

	//隐私号绑定ID
	@Column(name = "bind_id")
	private String bindId;
}
