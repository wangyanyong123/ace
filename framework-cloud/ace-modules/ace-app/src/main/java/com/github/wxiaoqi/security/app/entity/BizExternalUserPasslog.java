package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 对外提供接口用户信息
 * 
 * @author zxl
 * @Date 2018-12-29 17:50:38
 */
@Table(name = "biz_external_user_passlog")
public class BizExternalUserPasslog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //通行时间
    @Column(name = "pass_time")
    private Date passTime;
	
	    //通行状态
    @Column(name = "pass_status")
    private String passStatus;
	
	    //通行地址(小区门或单元门地址)
    @Column(name = "pass_addr")
    private String passAddr;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建时间
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //修改时间
    @Column(name = "modify_time")
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
	 * 设置：通行时间
	 */
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	/**
	 * 获取：通行时间
	 */
	public Date getPassTime() {
		return passTime;
	}
	/**
	 * 设置：通行状态
	 */
	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}
	/**
	 * 获取：通行状态
	 */
	public String getPassStatus() {
		return passStatus;
	}
	/**
	 * 设置：通行地址(小区门或单元门地址)
	 */
	public void setPassAddr(String passAddr) {
		this.passAddr = passAddr;
	}
	/**
	 * 获取：通行地址(小区门或单元门地址)
	 */
	public String getPassAddr() {
		return passAddr;
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
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
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
	 * 设置：修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
}
