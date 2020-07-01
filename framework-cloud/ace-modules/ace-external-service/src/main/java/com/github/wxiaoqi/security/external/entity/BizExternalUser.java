package com.github.wxiaoqi.security.external.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 对外提供接口用户信息
 * 
 * @author zxl
 * @Date 2018-12-25 18:23:09
 */
@Table(name = "biz_external_user")
public class BizExternalUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //供应商名字
    @Column(name = "name")
    private String name;
	
	    //供应商key
    @Column(name = "app_id")
    private String appId;
	
	    //秘钥
    @Column(name = "app_secret")
    private String appSecret;
	
	    //回调地址
    @Column(name = "callback_url")
    private String callbackUrl;
	
	    //备注
    @Column(name = "remark")
    private String remark;
	
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
	
	    //外部对接门禁厂商注册用户标识表
    @Column(name = "external_user_type")
    private String externalUserType;
	

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
	 * 设置：供应商名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：供应商名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：供应商key
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：供应商key
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：秘钥
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	/**
	 * 获取：秘钥
	 */
	public String getAppSecret() {
		return appSecret;
	}
	/**
	 * 设置：回调地址
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	/**
	 * 获取：回调地址
	 */
	public String getCallbackUrl() {
		return callbackUrl;
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
	/**
	 * 设置：外部对接门禁厂商注册用户标识表
	 */
	public void setExternalUserType(String externalUserType) {
		this.externalUserType = externalUserType;
	}
	/**
	 * 获取：外部对接门禁厂商注册用户标识表
	 */
	public String getExternalUserType() {
		return externalUserType;
	}
}
