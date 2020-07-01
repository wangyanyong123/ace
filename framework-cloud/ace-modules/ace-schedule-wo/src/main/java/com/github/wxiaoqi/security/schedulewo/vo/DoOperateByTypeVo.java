package com.github.wxiaoqi.security.schedulewo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoOperateByTypeVo {

	@ApiModelProperty(value = "订单/工单ID")
	private String id;
	@ApiModelProperty(value = "图片ID，多个图片用逗号分隔")
	private String imgId;
	@ApiModelProperty(value = "描述内容")
	private String description;
	@ApiModelProperty(value = "操作类型(1-普通操作、2-支付回调、3-退款回调、4-发起退款流程、5-下单未支付的取消、6-退保证金、7-工单开始处理、8-工单受理、9-工单抢单、10-工单拒绝、11-工单接受、12-工单完成、13-团购成团、14-工单关闭、15-售后/退款、16-审核、17-订单确认、18-工单暂停、19-工单恢复)")
	private String operateType;



}
