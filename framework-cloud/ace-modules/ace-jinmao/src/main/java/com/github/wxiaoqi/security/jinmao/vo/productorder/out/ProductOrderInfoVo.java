package com.github.wxiaoqi.security.jinmao.vo.productorder.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductOrderInfoVo implements Serializable {

    private static final long serialVersionUID = -2684242166669124468L;
    private String orderId;

    private String parentId;

    private String tenantId;

    private String orderCode;

    private Integer orderStatus;

    private Integer refundStatus;

    private String contactName;

    private String contactTel;

    private String deliveryAddr;

    private Date createTime;

    private String description;

    //发票类型(0-不开发票,1-个人,2-公司)
    private Integer invoiceType;

    //发票名称
    private String invoiceName;

    //税号
    private String dutyCode;

    // 商品总价
    private BigDecimal productPrice;

    private List<ProductOrderDetailVo> productList;

    private List<ProductOrderOperationVo> operationList;

    public String getOrderStatusDesc(){
        return AceDictionary.ORDER_STATUS.get(orderStatus);
    }

    public String getRefundStatusDesc(){
        return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);
    }


}
