package com.github.wxiaoqi.security.api.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class WoDetailOutVo implements Serializable {


	private static final long serialVersionUID = 259115724270895748L;

	@ApiModelProperty(value = "工单详情")
	private WoVo detail;

	@ApiModelProperty(value = "工单操作按钮")
	private List<OperateButton> operateButtonList;

	@ApiModelProperty(value = "操作流水日志")
	private List<TransactionLogVo> transactionLogList;

}
