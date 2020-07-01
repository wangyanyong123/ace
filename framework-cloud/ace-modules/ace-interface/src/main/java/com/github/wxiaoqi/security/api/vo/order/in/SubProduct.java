package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订购产品
 * @author huangxl
 * @date 2018-12-18
 */
@Data
public class SubProduct implements Serializable{

	private static final long serialVersionUID = -3002199661729776554L;

    @ApiModelProperty(value = "产品ID")
	private String productId;
    @ApiModelProperty(value = "规格ID")
    private String specId;
    @ApiModelProperty(value = "数量")
    private int subNum;
    @ApiModelProperty(value = "购物车商品id")
    private String shoppingCartId;

}