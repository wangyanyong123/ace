package com.github.wxiaoqi.security.jinmao.vo.reservation.order.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PC服务订单查询
 */
@Data
public class ReservationOrderVO implements Serializable{

    private static final long serialVersionUID = -423081845077076374L;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单标题")
    private String title;

    @ApiModelProperty(value = "订单描述")
    private String description;

    @ApiModelProperty(value = "订单状态(5-待支付、6-拼团中、30-待评价、45-已关闭、50-已完成、110-待受理、115-待上门)")
    private Integer orderStatus;

    @ApiModelProperty(value = "退款状态(0-无退款、10-已退款、15-部分退款、20-退款完成)")
    private Integer refundStatus;

    @ApiModelProperty(value = "评论状态(0-未评论、1-已评论)")
    private Integer commentStatus;

    @ApiModelProperty(value = "公司/商户名称")
    private String tenantName;

    @ApiModelProperty(value = "客户名称")
    private String contactName;

    @ApiModelProperty(value = "服务名称")
    private String productName;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "预约时间")
    private Date reservationTime;

    @ApiModelProperty(value = "下单时间")
    private Date createTime;

    public String getOrderStatus(){
        return AceDictionary.ORDER_STATUS.get(orderStatus);
    }
    public String getRefundStatus(){
        return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);
    }
    public String getCommentStatus(){
        return AceDictionary.PRODUCT_COMMENT.get(commentStatus);
    }
}
