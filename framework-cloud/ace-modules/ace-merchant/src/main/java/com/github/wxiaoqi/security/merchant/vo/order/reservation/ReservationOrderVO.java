package com.github.wxiaoqi.security.merchant.vo.order.reservation;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 我的商品订单
 */
@Data
public class ReservationOrderVO implements Serializable {

    // 订单id
    private String orderId;

    // 订单编号
    private String orderCode;

    // 订单状态
    private Integer orderStatus;

    // 退款状态：0-无退款；10-退款中；15-部分退款;20-退款完成;
    private Integer refundStatus;

    // 评论状态：0-未评论；1-已评论；
    private Integer commentStatus;

    // 产品名称
    private String productName;

    // 订单标题
    private String title;

    //图片id,多张图片逗号分隔
    private String specImg;

    //数量
    private Integer quantity;

    //总价
    private BigDecimal totalPrice;

    //单位
    private String unit;

    // 联系人电话
    private String contactTel;

    //收货地址
    private String deliveryAddr;

    // 联系人姓名
    private String contactName;

    //创建日期
    private String createTime;

}
