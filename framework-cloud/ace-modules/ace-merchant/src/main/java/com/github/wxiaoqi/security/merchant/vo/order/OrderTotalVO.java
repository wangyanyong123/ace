package com.github.wxiaoqi.security.merchant.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderTotalVO implements Serializable {
    private BigDecimal yesterdayTotal; // 昨日交易额
    private int todayOrderTotal; // 今日订单
    private List<OrderStatusDO> orderStatusList; // 订单状态

}
