package com.github.wxiaoqi.security.app.vo.in;

import com.github.wxiaoqi.security.api.vo.order.in.TransactionLogBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoOperateBean {

	@ApiModelProperty(value = "工单ID")
	private String id;
	@ApiModelProperty(value = "操作ID")
    private String operateId;
	@ApiModelProperty(value = "日志内容")
	private TransactionLogBean transactionLogBean;
	@ApiModelProperty(value = "指派/转派用户Id")
	private String handleBy;
	@ApiModelProperty(value = "当前用户Id")
	private String userId;

	@ApiModelProperty(value = "快递公司(商品订单)")
	private String expressCompany;
	@ApiModelProperty(value = "快递单号(商品订单)")
	private String expressNum;
}
