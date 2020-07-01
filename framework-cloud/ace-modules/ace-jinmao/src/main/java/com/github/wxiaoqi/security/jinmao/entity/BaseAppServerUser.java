package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * app服务端用户表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
@Table(name = "base_app_server_user")
public class BaseAppServerUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //用户id
    @Id
    private String id;
	
	    //手机号
    @Column(name = "mobile_phone")
    private String mobilePhone;
	
	    //密码
    @Column(name = "password")
    private String password;
	
	    //姓名
    @Column(name = "name")
    private String name;
	
	    //生日
    @Column(name = "birthday")
    private String birthday;
	
	    //邮箱
    @Column(name = "email")
    private String email;
	
	    //性别：0、未知；1、男；2、女
    @Column(name = "sex")
    private String sex;
	
	    //头像
    @Column(name = "profile_photo")
    private String profilePhoto;
	
	    //资质图片
    @Column(name = "seniority_photo")
    private String seniorityPhoto;

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	//是否激活
	@Column(name = "is_active")
	private String isActive;
	
	    //物业人员分类id
    @Column(name = "service_group_id")
    private String serviceGroupId;
	
	    //是否是物业服务人员(0-否，1-是)
    @Column(name = "is_service")
    private String isService;
	
	    //是否是管家(0-否，1-是)
    @Column(name = "is_housekeeper")
    private String isHousekeeper;
	
	    //是否是客服人员(0-否，1-是)
    @Column(name = "is_customer")
    private String isCustomer;

	//是否是商业人员(0-否，1-是)
	@Column(name = "is_business")
    private String isBusiness;
	
	    //租户id
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //启用状态(0表示禁用，1表示启用)
    @Column(name = "enable_status")
    private String enableStatus;
	
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
	 * 设置：手机号
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
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
	 * 设置：生日
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：生日
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：性别：0、未知；1、男；2、女
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别：0、未知；1、男；2、女
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：头像
	 */
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	/**
	 * 获取：头像
	 */
	public String getProfilePhoto() {
		return profilePhoto;
	}
	/**
	 * 设置：资质图片
	 */
	public void setSeniorityPhoto(String seniorityPhoto) {
		this.seniorityPhoto = seniorityPhoto;
	}
	/**
	 * 获取：资质图片
	 */
	public String getSeniorityPhoto() {
		return seniorityPhoto;
	}
	/**
	 * 设置：物业人员分类id
	 */
	public void setServiceGroupId(String serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	/**
	 * 获取：物业人员分类id
	 */
	public String getServiceGroupId() {
		return serviceGroupId;
	}
	/**
	 * 设置：是否是物业服务人员(0-否，1-是)
	 */
	public void setIsService(String isService) {
		this.isService = isService;
	}
	/**
	 * 获取：是否是物业服务人员(0-否，1-是)
	 */
	public String getIsService() {
		return isService;
	}
	/**
	 * 设置：是否是管家(0-否，1-是)
	 */
	public void setIsHousekeeper(String isHousekeeper) {
		this.isHousekeeper = isHousekeeper;
	}
	/**
	 * 获取：是否是管家(0-否，1-是)
	 */
	public String getIsHousekeeper() {
		return isHousekeeper;
	}
	/**
	 * 设置：是否是客服人员(0-否，1-是)
	 */
	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}
	/**
	 * 获取：是否是客服人员(0-否，1-是)
	 */
	public String getIsCustomer() {
		return isCustomer;
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
	 * 设置：启用状态(0表示禁用，1表示启用)
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	/**
	 * 获取：启用状态(0表示禁用，1表示启用)
	 */
	public String getEnableStatus() {
		return enableStatus;
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
	public void setCreateBy(String   createBy) {
		this.  createBy =   createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return   createBy;
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

	public String getIsBusiness() {
		return isBusiness;
	}

	public void setIsBusiness(String isBusiness) {
		this.isBusiness = isBusiness;
	}
}
