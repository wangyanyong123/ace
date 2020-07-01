package com.github.wxiaoqi.security.jinmao.vo.productorder.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductOrderListVo implements Serializable {
    private static final long serialVersionUID = 2090233328734821731L;

    private String orderId;

    private String orderCode;

    private String title;

    private String contactName;

    private BigDecimal actualPrice;

    private Date createTime;

    private Integer orderStatus;

    private Integer refundStatus;

    public String getOrderStatusDesc() {
        return AceDictionary.ORDER_STATUS.get(orderStatus);
    }

    public String getRefundStatusDesc() {
        return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);
    }
}
