package com.github.wxiaoqi.security.jinmao.vo.reservation.order.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 预约服务订单
 *
 */
@Data
public class ReservationOrderInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String id;

	//订单编号
	private String orderCode;

	//订单状态 5：待支付，10：待受理；15：待上门；20:服务中；25：待评价；30：已完成；35：退款中 ；40：已关闭；
	private Integer orderStatus;

	private String orderStatusDesc;

	//退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
	private Integer refundStatus;

	private String refundStatusDesc;

	//是否评论 0：未评论，1.已评论
	private Integer commentStatus;
	private String commentStatusDesc;

	//订单标题
	private String title;

	//订单描述
	private String description;

	//商品总金额
	private BigDecimal productPrice;

	//预约服务时间
	private Date reservationTime;

	//发票类型(0-不开发票,1-个人,2-公司)
	private Integer invoiceType;

	//发票名称
	private String invoiceName;

	//税号
	private String dutyCode;

	//收获联系人
	private String contactName;

	//收货人联系电话
	private String contactTel;

	//收货地址
	private String deliveryAddr;

	//
	private Date createTime;

	// 服务详情
	private ReservationOrderDetailInfo reservationOrderDetailInfo;

	// 操作记录
	List<ReservationOrderOperationRecord> reservationOrderOperationRecordList;

	public String getOrderStatusDesc(){
		return AceDictionary.ORDER_STATUS.get(orderStatus);
	}
	public String getRefundStatusDesc(){
		return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);
	}
	public String getCommentStatusDesc(){
		return AceDictionary.PRODUCT_COMMENT.get(commentStatus);
	}
}
