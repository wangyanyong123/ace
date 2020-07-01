package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 预约服务表
 * 
 * @author huangxl
 * @Date 2019-03-12 09:26:32
 */
@Table(name = "biz_reservation")
public class BizReservation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //商户ID
    @Column(name = "company_id")
    private String companyId;
	
	    //服务编码
    @Column(name = "reservation_code")
    private String reservationCode;
	
	    //服务名称
    @Column(name = "name")
    private String name;
	
	    //服务封面logo
    @Column(name = "reservation_logo")
    private String reservationLogo;
	
	    //商品图文详情
    @Column(name = "reservation_imagetext_info")
    private String reservationImagetextInfo;
	
	    //预约量
    @Column(name = "sales")
    private Integer sales;
	
	    //服务状态(1-待发布，2-待审核，3-已发布，4已驳回）
    @Column(name = "reserva_Status")
    private String reservaStatus;
	
	    //申请时间
    @Column(name = "apply_time")
    private Date applyTime;
	
	    //审核时间
    @Column(name = "audit_time")
    private Date auditTime;
	
	    //发布时间
    @Column(name = "publish_time")
    private Date publishTime;
	
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
	 * 设置：商户ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：商户ID
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：服务编码
	 */
	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}
	/**
	 * 获取：服务编码
	 */
	public String getReservationCode() {
		return reservationCode;
	}
	/**
	 * 设置：服务名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：服务名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：服务封面logo
	 */
	public void setReservationLogo(String reservationLogo) {
		this.reservationLogo = reservationLogo;
	}
	/**
	 * 获取：服务封面logo
	 */
	public String getReservationLogo() {
		return reservationLogo;
	}
	/**
	 * 设置：商品图文详情
	 */
	public void setReservationImagetextInfo(String reservationImagetextInfo) {
		this.reservationImagetextInfo = reservationImagetextInfo;
	}
	/**
	 * 获取：商品图文详情
	 */
	public String getReservationImagetextInfo() {
		return reservationImagetextInfo;
	}
	/**
	 * 设置：预约量
	 */
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	/**
	 * 获取：预约量
	 */
	public Integer getSales() {
		return sales;
	}
	/**
	 * 设置：服务状态(1-待发布，2-待审核，3-已发布，4已驳回）
	 */
	public void setReservaStatus(String reservaStatus) {
		this.reservaStatus = reservaStatus;
	}
	/**
	 * 获取：服务状态(1-待发布，2-待审核，3-已发布，4已驳回）
	 */
	public String getReservaStatus() {
		return reservaStatus;
	}
	/**
	 * 设置：申请时间
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * 获取：申请时间
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	/**
	 * 设置：审核时间
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublishTime() {
		return publishTime;
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
}
