package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 围合表
 * 
 * @author zxl
 * @Date 2018-12-24 11:45:40
 */
@Table(name = "biz_enclosed")
public class BizEnclosed implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //上级围合
    @Column(name = "enclosed_PID")
    private String enclosedPid;
	
	    //编码
    @Column(name = "enclosed_Code")
    private String enclosedCode;
	
	    //名称
    @Column(name = "enclosed_Name")
    private String enclosedName;
	
	    //项目ID
    @Column(name = "project_Id")
    private String projectId;
	
	    //租户Id
    @Column(name = "tenant_id")
    private String tenantId;
	
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
	 * 设置：上级围合
	 */
	public void setEnclosedPid(String enclosedPid) {
		this.enclosedPid = enclosedPid;
	}
	/**
	 * 获取：上级围合
	 */
	public String getEnclosedPid() {
		return enclosedPid;
	}
	/**
	 * 设置：编码
	 */
	public void setEnclosedCode(String enclosedCode) {
		this.enclosedCode = enclosedCode;
	}
	/**
	 * 获取：编码
	 */
	public String getEnclosedCode() {
		return enclosedCode;
	}
	/**
	 * 设置：名称
	 */
	public void setEnclosedName(String enclosedName) {
		this.enclosedName = enclosedName;
	}
	/**
	 * 获取：名称
	 */
	public String getEnclosedName() {
		return enclosedName;
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
	 * 设置：租户Id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户Id
	 */
	public String getTenantId() {
		return tenantId;
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
