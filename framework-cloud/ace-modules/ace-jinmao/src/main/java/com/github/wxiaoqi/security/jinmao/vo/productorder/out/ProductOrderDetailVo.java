package com.github.wxiaoqi.security.jinmao.vo.productorder.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOrderDetailVo {

    private String orderDetailId;

    private String orderId;

    private String productId;

    private String productName;

    private String specId;

    private String specName;

    private String specImg;

    private BigDecimal salesPrice;

    private Integer quantity;

    private Integer detailStatus;

    private Integer detailRefundStatus;

    private Integer commentStatus;

    public String getDetailStatusDesc(){
        return AceDictionary.ORDER_STATUS.get(detailStatus);
    }

    public String getDetailRefundStatusDesc(){
        return AceDictionary.ORDER_REFUND_STATUS.get(detailRefundStatus);
    }
    public String getCommentStatusDesc(){
        return AceDictionary.PRODUCT_COMMENT.get(commentStatus);
    }
}
