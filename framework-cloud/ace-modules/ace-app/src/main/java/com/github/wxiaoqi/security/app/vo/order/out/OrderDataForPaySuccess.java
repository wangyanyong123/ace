package com.github.wxiaoqi.security.app.vo.order.out;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付成功后，用于处理订单业务的实体
 */
@Data
public class OrderDataForPaySuccess {

    private String parentId;

    private String orderId;

    private Integer orderType;

    private BigDecimal actualPrice;

    private Integer orderStatus;

    private Integer accountBookPayStatus;


}
