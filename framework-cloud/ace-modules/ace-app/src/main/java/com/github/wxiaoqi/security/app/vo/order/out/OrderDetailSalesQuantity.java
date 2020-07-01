package com.github.wxiaoqi.security.app.vo.order.out;

import lombok.Data;

@Data
public class OrderDetailSalesQuantity {

    private String orderId;

    private String parentId;

    private String orderDetailId;

    private String productId;

    private String specId;

    private int quantity;
}
