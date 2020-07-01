package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 订单产品表
 * 
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@Data
@Table(name = "biz_product_order_detail")
public class BizProductOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //订单id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "parent_id")
    private String parentId;
	
	    //订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30:待评价 ，35：已完成；40：退款中 ；45：已关闭；
    @Column(name = "detail_status")
    private Integer detailStatus;
	
	    //退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
    @Column(name = "detail_refund_status")
    private Integer detailRefundStatus;
	
	    //商户id
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //产品id
    @Column(name = "product_id")
    private String productId;
	
	    //产品名称
    @Column(name = "product_name")
    private String productName;
	
	    //规格ID
    @Column(name = "spec_id")
    private String specId;
	
	    //规格
    @Column(name = "spec_name")
    private String specName;
	
	    //图片id,多张图片逗号分隔
    @Column(name = "spec_img")
    private String specImg;
	
	    //数量
    @Column(name = "quantity")
    private Integer quantity;
	
	    //单价
    @Column(name = "sales_price")
    private BigDecimal salesPrice;
	
	    //总价
    @Column(name = "total_price")
    private BigDecimal totalPrice;
	
	    //单位
    @Column(name = "unit")
    private String unit;
	
	    //是否评论 0：未评论，1.已评论
    @Column(name = "comment_status")
    private Integer commentStatus;
	
	    //状态：0、删除；1、正常
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;

}
