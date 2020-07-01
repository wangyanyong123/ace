package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 访客登记表
 * 
 * @author zxl
 * @Date 2019-01-08 17:51:58
 */
@Table(name = "biz_visitor_signlogs")
public class BizVisitorSignlogs implements Serializable {
	private static final long serialVersionUID = 1L;

	//id
	@Id
	private String id;

	//用户id
	@Column(name = "user_id")
	private String userId;

	//房屋id
	@Column(name = "house_id")
	private String houseId;

	//项目id
	@Column(name = "project_id")
	private String projectId;

	//二维码值
	@Column(name = "qrVal")
	private String qrVal;

	//访客人脸图片
	@Column(name = "visitor_photo")
	private String visitorPhoto;

	//访客姓名
	@Column(name = "visitor_name")
	private String visitorName;

	//访客电话
	@Column(name = "visitor_phone")
	private String visitorPhone;

	//访客性别
	@Column(name = "visitor_sex")
	private String visitorSex;

	//访客数量
	@Column(name = "visitor_num")
	private Integer visitorNum;

	//通行有效开始时间
	@Column(name = "visit_effect_time")
	private Date visitEffectTime;

	//通行有效结束时间
	@Column(name = "visit_end_time")
	private Date visitEndTime;

	//到访时间
	@Column(name = "visit_time")
	private Date visitTime;

	//到访地址
	@Column(name = "visit_addr")
	private String visitAddr;

	//来访事由
	@Column(name = "visit_reason")
	private String visitReason;

	//是否驾车：0、未驾车；1、驾车
	@Column(name = "is_drive")
	private String isDrive;

	//车牌号
	@Column(name = "license_plate")
	private String licensePlate;

	//状态：0、删除；1、正常
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
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：房屋id
	 */
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	/**
	 * 获取：房屋id
	 */
	public String getHouseId() {
		return houseId;
	}
	/**
	 * 设置：项目id
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目id
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：二维码值
	 */
	public void setQrVal(String qrVal) {
		this.qrVal = qrVal;
	}
	/**
	 * 获取：二维码值
	 */
	public String getQrVal() {
		return qrVal;
	}
	/**
	 * 设置：访客人脸图片
	 */
	public void setVisitorPhoto(String visitorPhoto) {
		this.visitorPhoto = visitorPhoto;
	}
	/**
	 * 获取：访客人脸图片
	 */
	public String getVisitorPhoto() {
		return visitorPhoto;
	}
	/**
	 * 设置：访客姓名
	 */
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	/**
	 * 获取：访客姓名
	 */
	public String getVisitorName() {
		return visitorName;
	}
	/**
	 * 设置：访客电话
	 */
	public void setVisitorPhone(String visitorPhone) {
		this.visitorPhone = visitorPhone;
	}
	/**
	 * 获取：访客电话
	 */
	public String getVisitorPhone() {
		return visitorPhone;
	}
	/**
	 * 设置：访客性别
	 */
	public void setVisitorSex(String visitorSex) {
		this.visitorSex = visitorSex;
	}
	/**
	 * 获取：访客性别
	 */
	public String getVisitorSex() {
		return visitorSex;
	}
	/**
	 * 设置：访客数量
	 */
	public void setVisitorNum(Integer visitorNum) {
		this.visitorNum = visitorNum;
	}
	/**
	 * 获取：访客数量
	 */
	public Integer getVisitorNum() {
		return visitorNum;
	}
	/**
	 * 设置：通行有效开始时间
	 */
	public void setVisitEffectTime(Date visitEffectTime) {
		this.visitEffectTime = visitEffectTime;
	}
	/**
	 * 获取：通行有效开始时间
	 */
	public Date getVisitEffectTime() {
		return visitEffectTime;
	}
	/**
	 * 设置：通行有效结束时间
	 */
	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}
	/**
	 * 获取：通行有效结束时间
	 */
	public Date getVisitEndTime() {
		return visitEndTime;
	}
	/**
	 * 设置：到访时间
	 */
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	/**
	 * 获取：到访时间
	 */
	public Date getVisitTime() {
		return visitTime;
	}
	/**
	 * 设置：到访地址
	 */
	public void setVisitAddr(String visitAddr) {
		this.visitAddr = visitAddr;
	}
	/**
	 * 获取：到访地址
	 */
	public String getVisitAddr() {
		return visitAddr;
	}
	/**
	 * 设置：来访事由
	 */
	public void setVisitReason(String visitReason) {
		this.visitReason = visitReason;
	}
	/**
	 * 获取：来访事由
	 */
	public String getVisitReason() {
		return visitReason;
	}
	/**
	 * 设置：是否驾车：0、未驾车；1、驾车
	 */
	public void setIsDrive(String isDrive) {
		this.isDrive = isDrive;
	}
	/**
	 * 获取：是否驾车：0、未驾车；1、驾车
	 */
	public String getIsDrive() {
		return isDrive;
	}
	/**
	 * 设置：车牌号
	 */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	/**
	 * 获取：车牌号
	 */
	public String getLicensePlate() {
		return licensePlate;
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
}
