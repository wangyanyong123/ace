package com.github.wxiaoqi.security.api.vo.to;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:57 2019/3/22
 * @Modified By:
 */
@Data
public class AliRefundTO implements Serializable {
	private static final long serialVersionUID = -1094318417569538044L;
	@ApiModelProperty(value = "商品订单号")
	private String outTradeNo;
	@ApiModelProperty(value = "退款原因")
	private String refundReason;
	@ApiModelProperty(hidden = true)
	private String outRequestNo;
}
