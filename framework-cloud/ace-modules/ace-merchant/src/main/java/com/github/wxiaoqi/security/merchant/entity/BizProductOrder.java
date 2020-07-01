package com.github.wxiaoqi.security.merchant.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 商品订单表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 13:13:28
 */
@Data
@Table(name = "biz_product_order")
public class BizProductOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //父订单id，与支付记录一致，
    @Column(name = "parent_id")
    private String parentId;
	
	    //商户id 未支付时取第一个商品的商户
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //订单编号
    @Column(name = "order_code")
    private String orderCode;
	
	    //
    @Column(name = "project_id")
    private String projectId;
	
	    //
    @Column(name = "user_id")
    private String userId;
	
	    //订单类型 1：普通订单；2：团购订单。3：秒杀订单
    @Column(name = "order_type")
    private Integer orderType;
	
	    //订单状态 5：待支付，6：拼团中， 10：待发货，15：部分发货20：待签收 35：已完成；40：退款中 ；45：已关闭；
    @Column(name = "order_status")
    private Integer orderStatus;
	
	    //退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
    @Column(name = "refund_status")
    private Integer refundStatus;
	
	    //是否评论 0：未评论，1.已评论
    @Column(name = "comment_status")
    private Integer commentStatus;
	
	    //订单标题
    @Column(name = "title")
    private String title;
	
	    //订单描述
    @Column(name = "description")
    private String description;
	
	    //下单应用类型 H5:10,微信小程序：20；安卓：30. ios：40
    @Column(name = "app_type")
    private Integer appType;
	
	    //商品总金额
    @Column(name = "product_price")
    private BigDecimal productPrice;
	
	    //运费
    @Column(name = "express_price")
    private BigDecimal expressPrice;
	
	    //实收金额=商品总金额+运费-优惠金额
    @Column(name = "actual_price")
    private BigDecimal actualPrice;
	
	    //优惠金额
    @Column(name = "discount_price")
    private BigDecimal discountPrice;
	
	    //商品总件数
    @Column(name = "quantity")
    private Integer quantity;
	
	    //收获联系人
    @Column(name = "contact_name")
    private String contactName;
	
	    //收货人联系电话
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //收货地址
    @Column(name = "delivery_addr")
    private String deliveryAddr;
	
	    //
    @Column(name = "paid_time")
    private Date paidTime;
	
	    //最后一次发货时间
    @Column(name = "send_time")
    private Date sendTime;

	//确认收货日期
	@Column(name = "confirm_time")
	private Date confirmTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //支付时间
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;
	
	    //数据状态 1：有效；0：无效
    @Column(name = "status")
    private String status;

}
