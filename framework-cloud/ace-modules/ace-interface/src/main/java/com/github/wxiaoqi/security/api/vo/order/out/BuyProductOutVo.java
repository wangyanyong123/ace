package com.github.wxiaoqi.security.api.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 订购产品输出
 * @author huangxl
 * @date 2018-12-25
 */
@Data
public class BuyProductOutVo implements Serializable{


    private static final long serialVersionUID = -4742039935089594393L;
    @ApiModelProperty(value = "订单ID")
	private String subId;

	@ApiModelProperty(value = "订单标题")
	private String title;

	@ApiModelProperty(value = "实际支付ID")
	private String actualId;

	@ApiModelProperty(value = "实际支付金额(单元：元)")
	private String actualPrice;

}