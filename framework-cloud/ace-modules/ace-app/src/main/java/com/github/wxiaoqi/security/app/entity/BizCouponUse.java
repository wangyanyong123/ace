package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 优惠券使用表
 * 
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
@Table(name = "biz_coupon_use")
public class BizCouponUse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //优惠券ID
    @Column(name = "coupon_id")
    private String couponId;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //优惠券编码
    @Column(name = "discount_code")
    private String discountCode;
	
	    //使用状态(0-未领取,1-已领取,2-已使用,3-已退款,4-已过期)
    @Column(name = "use_status")
    private String useStatus;
	
	    //使用订单ID
    @Column(name = "order_id")
    private String orderId;
	
	    //业务id
    @Column(name = "bus_id")
    private String busId;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_By")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_Time")
    private Date modifyTime;
	

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：优惠券ID
	 */
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	/**
	 * 设置：用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：优惠券编码
	 */
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	/**
	 * 获取：优惠券编码
	 */
	public String getDiscountCode() {
		return discountCode;
	}
	/**
	 * 设置：使用状态(0-未领取,1-已领取,2-已使用,3-已退款,4-已过期)
	 */
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	/**
	 * 获取：使用状态(0-未领取,1-已领取,2-已使用,3-已退款,4-已过期)
	 */
	public String getUseStatus() {
		return useStatus;
	}
	/**
	 * 设置：使用订单ID
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：使用订单ID
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：业务id
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}
	/**
	 * 获取：业务id
	 */
	public String getBusId() {
		return busId;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
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
}
