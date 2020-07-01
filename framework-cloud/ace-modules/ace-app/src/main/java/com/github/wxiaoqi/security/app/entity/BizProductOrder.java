package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 商品订单表
 * 
 * @author guohao
 * @Date 2020-04-18 11:14:12
 */
@Table(name = "biz_product_order")
public class BizProductOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //父订单id，与支付记录一致，与order_id 相同时表示没有拆单
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
	
	    //订单状态 5：待支付，10：待发货，15：部分发货;20：待签收,
		// 30:待评价 ，35：已完成；40：退款中 ；45：已关闭；
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

	
	    //最后一次发货时间
    @Column(name = "send_time")
    private Date sendTime;

    //最后一次发货时间
    @Column(name = "paid_time")
    private Date paidTime;

    //确认收货日期
    @Column(name = "confirm_time")
    private Date confirmTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
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
	

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：父订单id，与支付记录一致，与order_id 相同时表示没有拆单
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父订单id，与支付记录一致，与order_id 相同时表示没有拆单
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：商户id 未支付时取第一个商品的商户
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：商户id 未支付时取第一个商品的商户
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：订单编号
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * 获取：订单编号
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * 设置：
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：订单类型 1：普通订单；2：团购订单。3：秒杀订单
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取：订单类型 1：普通订单；2：团购订单。3：秒杀订单
	 */
	public Integer getOrderType() {
		return orderType;
	}
	/**
	 * 设置：订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30:待评价 ，35：已完成；40：退款中 ；45：已关闭；
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30:待评价 ，35：已完成；40：退款中 ；45：已关闭；
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
	 */
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	/**
	 * 获取：退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
	 */
	public Integer getRefundStatus() {
		return refundStatus;
	}
	/**
	 * 设置：订单标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：订单标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：订单描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：订单描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：下单应用类型 H5:10,微信小程序：20；安卓：30. ios：40
	 */
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	/**
	 * 获取：下单应用类型 H5:10,微信小程序：20；安卓：30. ios：40
	 */
	public Integer getAppType() {
		return appType;
	}
	/**
	 * 设置：商品总金额
	 */
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	/**
	 * 获取：商品总金额
	 */
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	/**
	 * 设置：运费
	 */
	public void setExpressPrice(BigDecimal expressPrice) {
		this.expressPrice = expressPrice;
	}
	/**
	 * 获取：运费
	 */
	public BigDecimal getExpressPrice() {
		return expressPrice;
	}
	/**
	 * 设置：实收金额=商品总金额+运费-优惠金额
	 */
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	/**
	 * 获取：实收金额=商品总金额+运费-优惠金额
	 */
	public BigDecimal getActualPrice() {
		return actualPrice;
	}
	/**
	 * 设置：优惠金额
	 */
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 * 获取：优惠金额
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	/**
	 * 设置：商品总件数
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * 获取：商品总件数
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置：收获联系人
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * 获取：收获联系人
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * 设置：收货人联系电话
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 获取：收货人联系电话
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * 设置：收货地址
	 */
	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}
	/**
	 * 获取：收货地址
	 */
	public String getDeliveryAddr() {
		return deliveryAddr;
	}

	/**
	 * 设置：最后一次发货时间
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * 获取：最后一次发货时间
	 */
	public Date getSendTime() {
		return sendTime;
	}
	/**
	 * 设置：
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * 获取：
	 */
	public String getModifyBy() {
		return modifyBy;
	}
	/**
	 * 设置：
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置：
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	/**
	 * 获取：
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
	/**
	 * 设置：数据状态 1：有效；0：无效
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：数据状态 1：有效；0：无效
	 */
	public String getStatus() {
		return status;
	}

	public Integer getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	public Date getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
}
