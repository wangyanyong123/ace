package com.github.wxiaoqi.security.merchant.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-26 16:21:55
 */
@Table(name = "biz_product_order_discount")
public class BizProductOrderDiscount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //优惠类型 1：优惠券
    @Column(name = "discount_type")
    private String discountType;
	
	    //
    @Column(name = "parent_id")
    private String parentId;
	
	    //
    @Column(name = "order_id")
    private String orderId;
	
	    //
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //
    @Column(name = "order_detail_id")
    private String orderDetailId;
	
	    //与订单对应类型 1：订单。2：订单明细
    @Column(name = "order_relation_type")
    private Integer orderRelationType;
	
	    //优惠关联id
    @Column(name = "relation_id")
    private String relationId;
	
	    //
    @Column(name = "discount_price")
    private BigDecimal discountPrice;
	
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
	
	    //数据状态
    @Column(name = "status")
    private String status;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;
	

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
	 * 设置：优惠类型 1：优惠券
	 */
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	/**
	 * 获取：优惠类型 1：优惠券
	 */
	public String getDiscountType() {
		return discountType;
	}
	/**
	 * 设置：
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：
	 */
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	/**
	 * 获取：
	 */
	public String getOrderDetailId() {
		return orderDetailId;
	}
	/**
	 * 设置：与订单对应类型 1：订单。2：订单明细
	 */
	public void setOrderRelationType(Integer orderRelationType) {
		this.orderRelationType = orderRelationType;
	}
	/**
	 * 获取：与订单对应类型 1：订单。2：订单明细
	 */
	public Integer getOrderRelationType() {
		return orderRelationType;
	}
	/**
	 * 设置：优惠关联id
	 */
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	/**
	 * 获取：优惠关联id
	 */
	public String getRelationId() {
		return relationId;
	}
	/**
	 * 设置：
	 */
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getDiscountPrice() {
		return discountPrice;
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
	 * 设置：数据状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：数据状态
	 */
	public String getStatus() {
		return status;
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
}
