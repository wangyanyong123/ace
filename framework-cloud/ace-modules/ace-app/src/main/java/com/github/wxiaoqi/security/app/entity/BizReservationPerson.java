package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 预约服务人员表
 * 
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
@Table(name = "biz_reservation_person")
public class BizReservationPerson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //预约单号
    @Column(name = "reservation_num")
    private String reservationNum;
	
	    //服务ID
    @Column(name = "reservation_id")
    private String reservationId;
	
	    //服务分类ID
    @Column(name = "classify_id")
    private String classifyId;

	//服务分类ID
	@Column(name = "user_id")
	private String userId;
	
	    //联系人
    @Column(name = "contactor_name")
    private String contactorName;
	
	    //联系人方式
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //地址
    @Column(name = "address")
    private String address;

	//预约时间
	@Column(name = "reservation_time")
	private String reservationTime;
	
	    //备注
    @Column(name = "remark")
    private String remark;
	
	    //处理状态(1-待联系，2-已取消，3-已联系
    @Column(name = "deal_Status")
    private String dealStatus;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_Stamp")
    private Date timeStamp;
	
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
	 * 设置：预约单号
	 */
	public void setReservationNum(String reservationNum) {
		this.reservationNum = reservationNum;
	}
	/**
	 * 获取：预约单号
	 */
	public String getReservationNum() {
		return reservationNum;
	}
	/**
	 * 设置：服务ID
	 */
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	/**
	 * 获取：服务ID
	 */
	public String getReservationId() {
		return reservationId;
	}
	/**
	 * 设置：服务分类ID
	 */
	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	/**
	 * 获取：服务分类ID
	 */
	public String getClassifyId() {
		return classifyId;
	}
	/**
	 * 设置：联系人
	 */
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	/**
	 * 获取：联系人
	 */
	public String getContactorName() {
		return contactorName;
	}
	/**
	 * 设置：联系人方式
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 获取：联系人方式
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：处理状态(1-待联系，2-已取消，3-已联系
	 */
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	/**
	 * 获取：处理状态(1-待联系，2-已取消，3-已联系
	 */
	public String getDealStatus() {
		return dealStatus;
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

	public String getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
