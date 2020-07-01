package com.github.wxiaoqi.security.merchant.vo.order.product;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 我的商品订单
 */
@Data
public class ProductOrderVO implements Serializable {

    // 订单ID
    private String orderId;

    //订单编号
    private String orderCode;

    // 订单状态
    private Integer orderStatus;

    // 退款状态：0-无退款；10-退款中；15-部分退款;20-退款完成;
    private Integer refundStatus;

    // 评论状态：0-未评论；1-已评论；
    private Integer commentStatus;

    // 订单标题
    private String title;

    // 客户联系电话
    private String contactTel;

    // 客户姓名
    private String contactName;

    // 客户地址
    private String deliveryAddr;

    //创建日期
    private String createTime;

    // 商家商品总额
    private BigDecimal totalPrice;

    // 商品名称 多个商品名称“,”分割
    private String product_name;

    // 商品图片 多个商品图片“,”分割
    private String specImg;

    public String getSpecImg(){
        String[] img = specImg.split(",");
        if(ArrayUtils.isNotEmpty(img)){
            return img[0];
        }
        return specImg;
    }
}
