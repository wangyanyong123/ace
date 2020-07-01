package com.github.wxiaoqi.security.merchant.vo.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderStatusDO implements Serializable {

    private Integer orderStatus; // 订单状态
    private Integer total; // 数量
    private String orderStatusStr; // 订单状态说明

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof OrderStatusDO){
            OrderStatusDO orderStatusDO = (OrderStatusDO) obj;
            if(orderStatusDO.orderStatus.equals(this.orderStatus)){
                return true;
            }
        }

        return false;
    }
}
