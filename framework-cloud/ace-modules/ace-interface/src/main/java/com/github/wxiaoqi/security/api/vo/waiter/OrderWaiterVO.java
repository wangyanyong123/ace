package com.github.wxiaoqi.security.api.vo.waiter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 服务订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 17:50:27
 */
@Data
public class OrderWaiterVO implements Serializable {
	private static final long serialVersionUID = 1L;


	@NotEmpty(message="缺少参数orderId")
	@ApiModelProperty(value = "订单号" ,required = true)
	private String orderId;

	@NotEmpty(message="缺少参数waiterTel")
	@ApiModelProperty(value = "服务人员联系电话" ,required = true)
	private String waiterTel;

	@NotEmpty(message="缺少参数waiterName")
	@ApiModelProperty(value = "服务人员名称" ,required = true)
	private String waiterName;

	@ApiModelProperty(value = "备注")
	private String remark;


}
