package com.github.wxiaoqi.security.app.vo.order.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderIdResult {

    private String orderId;

    private String parentId;

    private Integer orderStatus;

}
