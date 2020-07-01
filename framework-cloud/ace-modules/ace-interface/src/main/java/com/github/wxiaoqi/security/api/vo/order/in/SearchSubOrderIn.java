package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SearchSubOrderIn implements Serializable {


	private static final long serialVersionUID = 2605791341217767821L;

	// 页码
	@ApiModelProperty(value = "页码")
	private int page;
	// 每页显示记录数
	@ApiModelProperty(value = "每页显示记录数")
	private int limit = 10;
	//订单状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成）
	@ApiModelProperty(value = "订单状态(99-全部、1-待发货、2-待支付、4-待收货、3-待评价）")
	private String subscribeStatus;


}
