package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * app服务端用户表租户表
 * 
 * @author huangxl
 * @Date 2019-05-07 14:23:06
 */
@Table(name = "base_app_server_user_tenant_id")
public class BaseAppServerUserTenantId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //用户id
    @Id
    private String id;
	
	    //租户id
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //状态：0、删除；1、正常
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
	 * 设置：用户id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：用户id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：租户id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户id
	 */
	public String getTenantId() {
		return tenantId;
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
}
