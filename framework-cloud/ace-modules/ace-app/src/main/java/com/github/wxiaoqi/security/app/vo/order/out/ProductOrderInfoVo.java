package com.github.wxiaoqi.security.app.vo.order.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品订单详情
 */
@Data
public class ProductOrderInfoVo implements Serializable {

    private static final long serialVersionUID = 1426711899219869759L;
    @ApiModelProperty(value = "订单Id")
    private String id;
    //订单编码
    @ApiModelProperty(value = "订单编码")
    private String orderCode;

    @ApiModelProperty(value = "订单类型 1：普通订单；2：团购订单。3：秒杀订单")
    private Integer orderType;

    //标题
    @ApiModelProperty(value = "标题")
    private String title;

    //地址
    @ApiModelProperty(value = "收货人地址")
    private String deliveryAddr;

    //联系人名称
    @ApiModelProperty(value = "收货人名称")
    private String contactName;

    //联系人电话
    @ApiModelProperty(value = "收货人电话")
    private String contactTel;

    //买家名字
    @ApiModelProperty(value = "买家名字")
    private String userName;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单退款状态")
    private Integer refundStatus;

    @ApiModelProperty(value = "订单评价状态")
    private Integer commentStatus;

    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;

    @ApiModelProperty(value = "发货时间")
    private Date sendTime;

    @ApiModelProperty(value = "实际支付ID")
    private String actualId;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualCost;

    @ApiModelProperty(value = "商品总金额")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "运费")
    private BigDecimal expressPrice;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "总件数")
    private int quantity;

    @ApiModelProperty(value = "订单产品列表")
    private List<ProductOrderTenantInfo> tenantList;

    @ApiModelProperty(value = "优惠信息")
    private List<ProductOrderDiscountInfo> discountList;

    @ApiModelProperty(value = "订单物流信息")
    private List<OrderLogisticsInfo> logisticsList;

    public String getOrderStatusDesc(){
        return AceDictionary.ORDER_STATUS.get(orderStatus);
    }
    public String getRefundStatusDesc(){
        return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);
    }
    public String getCommentStatusDesc(){
        return AceDictionary.PRODUCT_COMMENT.get(commentStatus);
    }
}
