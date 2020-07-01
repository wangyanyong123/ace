package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 批量认证表
 * 
 * @author zxl
 * @Date 2019-09-25 15:19:18
 */
@Table(name = "biz_user_house_batch_auth")
public class BizUserHouseBatchAuth implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //手机号
    @Column(name = "phone")
    private String phone;
	
	    //姓名
    @Column(name = "name")
    private String name;
	
	    //项目编码
    @Column(name = "project_code")
    private String projectCode;
	
	    //房屋编码
    @Column(name = "house_code")
    private String houseCode;
	
	    //是否认证
    @Column(name = "is_send")
    private String isSend;
	
	    //是否成功
    @Column(name = "is_suc")
    private String isSuc;
	
	    //返回结果
    @Column(name = "auth_result")
    private String authResult;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
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
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：项目编码
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * 获取：项目编码
	 */
	public String getProjectCode() {
		return projectCode;
	}
	/**
	 * 设置：房屋编码
	 */
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	/**
	 * 获取：房屋编码
	 */
	public String getHouseCode() {
		return houseCode;
	}
	/**
	 * 设置：是否认证
	 */
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	/**
	 * 获取：是否认证
	 */
	public String getIsSend() {
		return isSend;
	}
	/**
	 * 设置：是否成功
	 */
	public void setIsSuc(String isSuc) {
		this.isSuc = isSuc;
	}
	/**
	 * 获取：是否成功
	 */
	public String getIsSuc() {
		return isSuc;
	}
	/**
	 * 设置：返回结果
	 */
	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}
	/**
	 * 获取：返回结果
	 */
	public String getAuthResult() {
		return authResult;
	}
	/**
	 * 设置：状态(0-删除，1-正常)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0-删除，1-正常)
	 */
	public String getStatus() {
		return status;
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
