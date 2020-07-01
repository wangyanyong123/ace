package com.github.wxiaoqi.security.merchant.vo.order.product;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 我的商品订单
 */
@Data
public class ProductOrderDetailVO implements Serializable {

    // 订单ID
    private String orderId;

    // 订单编号
    private String orderCode;

    //订单标题
    private String title;

    // 客户联系电话
    private String contactTel;

    // 客户姓名
    private String contactName;

    // 客户地址
    private String deliveryAddr;

    //创建日期
    private String createTime;

    private List<ProductOrderDetail> productList;

    //发票类型(0-不开发票,1-个人,2-公司)
    private Integer invoiceType;

    //抬头
    private String invoiceName;

    //税号
    private String dutyCode;
    private Integer orderStatus;
    private Integer refundStatus;
    private Integer commentStatus;

    // 运费
    private BigDecimal incrementPrice;

    // 优惠金额
    private BigDecimal discountPrice;
    // 实付金额
    private BigDecimal actualPrice;
    // 商品数量
    private Integer quantity;
    // 备注
    private String remark;

    public String getRemark(){
        if(ObjectUtils.isEmpty(remark)){
            return "";
        }
        return remark;
    }


}
