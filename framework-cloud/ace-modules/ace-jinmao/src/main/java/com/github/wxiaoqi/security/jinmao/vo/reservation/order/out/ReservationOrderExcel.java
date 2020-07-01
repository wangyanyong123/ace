package com.github.wxiaoqi.security.jinmao.vo.reservation.order.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 服务订单导出
 */
@Data
public class ReservationOrderExcel implements Serializable{

    private static final long serialVersionUID = -423081845077076374L;

    @ApiModelProperty(value = "订单号")
    private String orderCode;

    @ApiModelProperty(value = "订单标题")
    private String title;

    @ApiModelProperty(value = "下单时间")
    private String createTime;

    @ApiModelProperty(value = "预约时间")
    private String reservationTime;

    @ApiModelProperty(value = "联系人名称")
    private String contactName;

    @ApiModelProperty(value = "联系电话")
    private String contactTel;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "联系地址")
    private String deliveryAddr;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "支付方式")
    private String payType;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;

    @ApiModelProperty(value = "评论状态")
    private Integer commentStatus;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal applyPrice;

    @ApiModelProperty(value = "退款时间")
    private String applyTime;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "规格名称")
    private String specName;

    @ApiModelProperty(value = "购买数量")
    private String quantity;

    @ApiModelProperty(value = "规格单位")
    private String unit;

    @ApiModelProperty(value = "服务价格")
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "商户名称")
    private String tenantName;

    @ApiModelProperty(value = "销售方式")
    private String salesWay;

    @ApiModelProperty(value = "供应商")
    private String supplier;


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
