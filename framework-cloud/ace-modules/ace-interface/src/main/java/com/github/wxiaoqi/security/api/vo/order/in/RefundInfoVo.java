package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:56 2019/3/28
 * @Modified By:
 */
@Data
public class RefundInfoVo implements Serializable {
	private static final long serialVersionUID = 5341833841992241436L;
	@ApiModelProperty(value = "订单id")
	private String subId;
	@ApiModelProperty(value = "发起人类型 1、买家，2、商业人员")
	private String userType;
	@ApiModelProperty(value = "审核状态")
	private String auditStatus;
}
