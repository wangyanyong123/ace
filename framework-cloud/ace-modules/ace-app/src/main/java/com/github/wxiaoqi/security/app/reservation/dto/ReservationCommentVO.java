package com.github.wxiaoqi.security.app.reservation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationCommentVO {

	@ApiModelProperty(value = "订单/工单ID")
	private String orderId;
	@ApiModelProperty(value = "图片ID，多个图片用逗号分隔")
	private String imgIds;
	@ApiModelProperty(value = "描述内容")
	private String description;
	@ApiModelProperty(value = "评价分值（0~5分）")
	private Integer appraisalVal;
	@ApiModelProperty(value = "是否准时到岗(0-未评价，1-是，2-否)")
	private String isArriveOntime;
	@ApiModelProperty(value = "商品ID")
	private String productId;
}
