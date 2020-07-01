package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 装修监理申请表
 * 
 * @author huangxl
 * @Date 2019-04-01 15:20:10
 */
@Table(name = "biz_decorete_apply")
public class BizDecoreteApply implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;

	//装修监理ID
	@Column(name = "sub_id")
	private String subId;
	
	    //装修监理ID
    @Column(name = "decorete_id")
    private String decoreteId;
	
	    //申请人id
    @Column(name = "user_id")
    private String userId;
	
	    //联系人
    @Column(name = "contactor_name")
    private String contactorName;
	
	    //联系人方式
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //房屋地址
    @Column(name = "address")
    private String address;
	
	    //装修阶段
    @Column(name = "decorete_stage")
    private String decoreteStage;
	
	    //建筑面积
    @Column(name = "covered_area")
    private String coveredArea;
	
	    //总金额
    @Column(name = "cost")
    private String cost;
	
	    //支付状态(0-待支付,1-待接单,2-监理中,3-已完成,4-已取消,5-已评价)
    @Column(name = "decorete_status")
    private String decoreteStatus;
	
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
	 * 设置：装修监理ID
	 */
	public void setDecoreteId(String decoreteId) {
		this.decoreteId = decoreteId;
	}
	/**
	 * 获取：装修监理ID
	 */
	public String getDecoreteId() {
		return decoreteId;
	}
	/**
	 * 设置：申请人id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：申请人id
	 */
	public String getUserId() {
		return userId;
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
	 * 设置：房屋地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：房屋地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：装修阶段
	 */
	public void setDecoreteStage(String decoreteStage) {
		this.decoreteStage = decoreteStage;
	}
	/**
	 * 获取：装修阶段
	 */
	public String getDecoreteStage() {
		return decoreteStage;
	}
	/**
	 * 设置：建筑面积
	 */
	public void setCoveredArea(String coveredArea) {
		this.coveredArea = coveredArea;
	}
	/**
	 * 获取：建筑面积
	 */
	public String getCoveredArea() {
		return coveredArea;
	}
	/**
	 * 设置：总金额
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}
	/**
	 * 获取：总金额
	 */
	public String getCost() {
		return cost;
	}
	/**
	 * 设置：支付状态(0-待支付,1-待接单,2-监理中,3-已完成,4-已取消,5-已评价)
	 */
	public void setDecoreteStatus(String decoreteStatus) {
		this.decoreteStatus = decoreteStatus;
	}
	/**
	 * 获取：支付状态(0-待支付,1-待接单,2-监理中,3-已完成,4-已取消,5-已评价)
	 */
	public String getDecoreteStatus() {
		return decoreteStatus;
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

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}
}
