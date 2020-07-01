package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 物业缴费支付日志表
 * 
 * @Date 2019-02-18 13:59:22
 */
@Table(name = "biz_property_pay_log")
public class BizPropertyPayLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;

		//房屋ID
	@Column(name = "room_id")
	private String roomId;


	//订单ID
    @Column(name = "sub_id")
    private String subId;
	
	    //缴费时间
    @Column(name = "charge_time")
    private String chargeTime;
	
	    //缴费总金额
    @Column(name = "pay_amount")
    private BigDecimal payAmount;

    	//缴费方式
    @Column(name = "pay_type")
	private String payType;

    @Column(name = "notice_describe")
	private String noticeDescribe;
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_stamp")
    private Date timeStamp;
	
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
	

	/**
	 * 设置：ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public String getId() {
		return id;
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
	 * 设置：项目ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目ID
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * 设置：房屋ID
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	/**
	 * 获取：房屋ID
	 */
	public String getRoomId() {
		return roomId;
	}
	/**
	 * 设置：订单ID
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}
	/**
	 * 获取：订单ID
	 */
	public String getSubId() {
		return subId;
	}
	/**
	 * 设置：缴费时间
	 */
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}
	/**
	 * 获取：缴费时间
	 */
	public String getChargeTime() {
		return chargeTime;
	}
	/**
	 * 设置：缴费总金额
	 */
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}


	/**
	 * 获取：缴费总金额
	 */
	public BigDecimal getPayAmount() {
		return payAmount;
	}


	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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
	 * 设置：时间戳
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：时间戳
	 */
	public Date getTimeStamp() {
		return timeStamp;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getNoticeDescribe() {
		return noticeDescribe;
	}

	public void setNoticeDescribe(String noticeDescribe) {
		this.noticeDescribe = noticeDescribe;
	}
}
