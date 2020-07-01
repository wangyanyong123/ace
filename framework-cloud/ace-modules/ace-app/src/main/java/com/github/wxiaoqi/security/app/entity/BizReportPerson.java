package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 举报人表
 * 
 * @author huangxl
 * @Date 2019-03-04 17:13:39
 */
@Table(name = "biz_report_person")
public class BizReportPerson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //举报id
    @Column(name = "report_id")
    private String reportId;
	
	    //举报人ID
    @Column(name = "report_person_id")
    private String reportPersonId;
	
	    //举报人姓名
    @Column(name = "report_person")
    private String reportPerson;

	//联系方式
	@Column(name = "contact")
	private String contact;
	
	    //举报理由
    @Column(name = "report_reason")
    private String reportReason;
	
	    //状态：0删除，1正常
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
	 * 设置：举报id
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	/**
	 * 获取：举报id
	 */
	public String getReportId() {
		return reportId;
	}
	/**
	 * 设置：举报人ID
	 */
	public void setReportPersonId(String reportPersonId) {
		this.reportPersonId = reportPersonId;
	}
	/**
	 * 获取：举报人ID
	 */
	public String getReportPersonId() {
		return reportPersonId;
	}
	/**
	 * 设置：举报人姓名
	 */
	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson;
	}
	/**
	 * 获取：举报人姓名
	 */
	public String getReportPerson() {
		return reportPerson;
	}
	/**
	 * 设置：举报理由
	 */
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	/**
	 * 获取：举报理由
	 */
	public String getReportReason() {
		return reportReason;
	}
	/**
	 * 设置：状态：0删除，1正常
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0删除，1正常
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
