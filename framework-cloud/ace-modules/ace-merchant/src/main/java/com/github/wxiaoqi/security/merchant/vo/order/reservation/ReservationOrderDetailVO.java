package com.github.wxiaoqi.security.merchant.vo.order.reservation;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 我的商品订单
 */
@Data
public class ReservationOrderDetailVO implements Serializable {

    //订单id
    private String orderId;

    //订单编号
    private String orderCode;

    //产品名称
    private String productName;

    //规格名称
    private String specName;

    //图片id,多张图片逗号分隔
    private String specImg;

    //数量
    private Integer quantity;

    //单价
    private BigDecimal salesPrice;

    //总价
    private BigDecimal totalPrice;

    // 优惠金额
    private BigDecimal discountPrice;

    //单位
    private String unit;

    // 客户联系电话
    private String contactTel;

    // 客户姓名
    private String contactName;

    // 客户地址
    private String deliveryAddr;

    private Integer invoiceType;

    // 服务人员电话
    private String waitelTel;

    // 服务人员姓名
    private String waitelName;

    // 预约服务时间
    private String reservationTime;
    //创建日期
    private String createTime;
    // 备注
    private String remark;

    private Integer orderStatus;
    private Integer refundStatus;
    private Integer commentStatus;

    // 实付金额
    private BigDecimal actualPrice;

    //商户联系电话
    private String tenantTel;

    //商户绑定的隐私电话
    private String bindTel;

    //是否绑定：0-解绑；1-绑定；
    private Integer bindFlag;

}
