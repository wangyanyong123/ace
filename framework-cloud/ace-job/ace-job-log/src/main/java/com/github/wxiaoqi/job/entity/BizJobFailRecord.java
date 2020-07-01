package com.github.wxiaoqi.job.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import javax.persistence.*;

/**
 * 
 * 
 * @author guohao
 * @Date 2020-05-05 13:04:28
 */
@Table(name = "biz_job_fail_record")
public class BizJobFailRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //执行器名称
    @Column(name = "app_name")
    private String appName;
	
	    //
    @Column(name = "job_handler")
    private String jobHandler;
	
	    //任务详细信息 json格式
    @Column(name = "job_data")
    private String jobData;
	
	    //
    @Column(name = "error_message")
    private String errorMessage;
	
	    //处理状态 0：未处理，1：已处理
    @Column(name = "handle_status")
    private Integer handleStatus;
	
	    //备注
    @Column(name = "remark")
    private String remark;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;
	
	    //
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
	 * 设置：执行器名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * 获取：执行器名称
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * 设置：
	 */
	public void setJobHandler(String jobHandler) {
		this.jobHandler = jobHandler;
	}
	/**
	 * 获取：
	 */
	public String getJobHandler() {
		return jobHandler;
	}
	/**
	 * 设置：任务详细信息 json格式
	 */
	public void setJobData(String jobData) {
		this.jobData = jobData;
	}
	/**
	 * 获取：任务详细信息 json格式
	 */
	public String getJobData() {
		return jobData;
	}
	/**
	 * 设置：
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * 获取：
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * 设置：处理状态 0：未处理，1：已处理
	 */
	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}
	/**
	 * 获取：处理状态 0：未处理，1：已处理
	 */
	public Integer getHandleStatus() {
		return handleStatus;
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
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
}
