package com.github.wxiaoqi.security.merchant.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 订单产品表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 22:16:33
 */
@Table(name = "biz_product_order_detail")
public class BizProductOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //订单id
    @Column(name = "order_id")
    private String orderId;
	
	    //订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30待评价 35：已完成；40：退款中 ；45：已关闭；
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
    private Boolean commentStatus;
	
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
	

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：订单id
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30待评价 35：已完成；40：退款中 ；45：已关闭；
	 */
	public void setDetailStatus(Integer detailStatus) {
		this.detailStatus = detailStatus;
	}
	/**
	 * 获取：订单状态 5：待支付，10：待发货，15：部分发货20：待签收, 30待评价 35：已完成；40：退款中 ；45：已关闭；
	 */
	public Integer getDetailStatus() {
		return detailStatus;
	}
	/**
	 * 设置：退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
	 */
	public void setDetailRefundStatus(Integer detailRefundStatus) {
		this.detailRefundStatus = detailRefundStatus;
	}
	/**
	 * 获取：退款状态：0：无退款，10：退款中，15:部分退款 20：已退款
	 */
	public Integer getDetailRefundStatus() {
		return detailRefundStatus;
	}
	/**
	 * 设置：商户id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：商户id
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：产品id
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品id
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置：产品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：产品名称
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：规格ID
	 */
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	/**
	 * 获取：规格ID
	 */
	public String getSpecId() {
		return specId;
	}
	/**
	 * 设置：规格
	 */
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	/**
	 * 获取：规格
	 */
	public String getSpecName() {
		return specName;
	}
	/**
	 * 设置：图片id,多张图片逗号分隔
	 */
	public void setSpecImg(String specImg) {
		this.specImg = specImg;
	}
	/**
	 * 获取：图片id,多张图片逗号分隔
	 */
	public String getSpecImg() {
		return specImg;
	}
	/**
	 * 设置：数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	/**
	 * 获取：数量
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * 设置：单价
	 */
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	/**
	 * 获取：单价
	 */
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	/**
	 * 设置：总价
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取：总价
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	/**
	 * 设置：单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 获取：单位
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * 设置：是否评论 0：未评论，1.已评论
	 */
	public void setCommentStatus(Boolean commentStatus) {
		this.commentStatus = commentStatus;
	}
	/**
	 * 获取：是否评论 0：未评论，1.已评论
	 */
	public Boolean getCommentStatus() {
		return commentStatus;
	}
	/**
	 * 设置：状态：0、删除；1、正常
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0、删除；1、正常
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改人
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * 获取：修改人
	 */
	public String getModifyBy() {
		return modifyBy;
	}
	/**
	 * 设置：修改日期
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改日期
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
}
