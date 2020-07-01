package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoOperateVo {

	@ApiModelProperty(value = "订单/工单ID")
	private String id;
	@ApiModelProperty(value = "操作ID")
    private String operateId;
	@ApiModelProperty(value = "图片ID，多个图片用逗号分隔")
	private String imgIds;
	@ApiModelProperty(value = "描述内容")
	private String description;
	@ApiModelProperty(value = "评价分值（0~5分）")
	private Integer appraisalVal;
	@ApiModelProperty(value = "是否准时到岗(0-未评价，1-是，2-否)")
	private String isArriveOntime;

	@ApiModelProperty(value = "快递公司(商品订单)")
	private String expressCompany;
	@ApiModelProperty(value = "快递单号(商品订单)")
	private String expressNum;
}
