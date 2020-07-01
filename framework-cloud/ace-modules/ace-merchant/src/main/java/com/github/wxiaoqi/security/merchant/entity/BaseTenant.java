package com.github.wxiaoqi.security.merchant.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 租户表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
@Table(name = "base_tenant")
public class BaseTenant implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //账号
    @Column(name = "account")
    private String account;

    	//类型(1-公司,2-商户)
	@Column(name = "tenant_type")
	private String tenantType;

	@Column(name = "center_city_name")
	private String centerCityName;
	    //编码
    @Column(name = "code")
    private String code;
	
	    //公司名称
    @Column(name = "name")
    private String name;
	
	    //营业执照号
    @Column(name = "licence_no")
    private String licenceNo;
	
	    //公司地址
    @Column(name = "address")
    private String address;
	
	    //企业法人
    @Column(name = "juristicPerson")
    private String juristicperson;
	
	    //成立时间
    @Column(name = "setup_time")
    private Date setupTime;
	
	    //注册资本,以万元为单位
    @Column(name = "reg_capital")
    private String regCapital;
	
	    //负责人名字
    @Column(name = "contactor_name")
    private String contactorName;
	
	    //负责人手机号码
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //负责人邮箱
    @Column(name = "contact_email")
    private String contactEmail;

    //营业资历图片
	@Column(name = "qualific_img")
    private String qualificImg;
	
	    //简介
    @Column(name = "summary")
    private String summary;
	
	    //创建人
    @Column(name = "crt_user_name")
    private String crtUserName;

	//状态
	@Column(name = "status")
	private String status;

	//启用状态(0表示禁用，1表示启用)
	@Column(name = "enable_status")
	private String enableStatus;

	//商户logo
	@Column(name = "logo_img")
	private String logoImg;
	    //创建人ID
    @Column(name = "crt_user_id")
    private String crtUserId;
	
	    //创建时间
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //最后更新人
    @Column(name = "upd_user_name")
    private String updUserName;
	
	    //最后更新人ID
    @Column(name = "upd_user_id")
    private String updUserId;
	
	    //最后更新时间
    @Column(name = "upd_time")
    private Date updTime;
	
	    //
    @Column(name = "attr1")
    private String attr1;
	
	    //
    @Column(name = "attr2")
    private String attr2;
	
	    //
    @Column(name = "attr3")
    private String attr3;
	
	    //
    @Column(name = "attr4")
    private String attr4;
	
	    //
    @Column(name = "description")
    private String description;
	
	    //是否超级租户
    @Column(name = "is_super_tenant")
    private String isSuperTenant;
	
	    //
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //拥有者
    @Column(name = "owner")
    private String owner;

    	//是否可打烊
	@Column(name = "is_close")
	private String isClose;

		//是否可开票
	@Column(name = "is_invoice")
	private String isInvoice;

        //是否可打印
    @Column(name = "is_print")
    private String isPrint;

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }

    public String getIsClose() {
		return isClose;
	}

	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

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
	 * 设置：账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：类型(1-公司,2-商户)
	 */
	public String getTenantType() {
		return tenantType;
	}
	/**
	 * 获取：类型(1-公司,2-商户)
	 */
	public void setTenantType(String tenantType) {
		this.tenantType = tenantType;
	}

	/**
	 * 设置：编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：公司名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：公司名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：营业执照号
	 */
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	/**
	 * 获取：营业执照号
	 */
	public String getLicenceNo() {
		return licenceNo;
	}
	/**
	 * 设置：公司地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：公司地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：企业法人
	 */
	public void setJuristicperson(String juristicperson) {
		this.juristicperson = juristicperson;
	}
	/**
	 * 获取：企业法人
	 */
	public String getJuristicperson() {
		return juristicperson;
	}
	/**
	 * 设置：成立时间
	 */
	public void setSetupTime(Date setupTime) {
		this.setupTime = setupTime;
	}
	/**
	 * 获取：成立时间
	 */
	public Date getSetupTime() {
		return setupTime;
	}
	/**
	 * 设置：注册资本,以万元为单位
	 */
	public void setRegCapital(String regCapital) {
		this.regCapital = regCapital;
	}
	/**
	 * 获取：注册资本,以万元为单位
	 */
	public String getRegCapital() {
		return regCapital;
	}
	/**
	 * 设置：负责人名字
	 */
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	/**
	 * 获取：负责人名字
	 */
	public String getContactorName() {
		return contactorName;
	}
	/**
	 * 设置：负责人手机号码
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 获取：负责人手机号码
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * 设置：负责人邮箱
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	/**
	 * 获取：负责人邮箱
	 */
	public String getContactEmail() {
		return contactEmail;
	}
	/**
	 * 设置：简介
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * 获取：简介
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * 设置：创建人
	 */
	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}
	/**
	 * 获取：创建人
	 */
	public String getCrtUserName() {
		return crtUserName;
	}
	/**
	 * 设置：创建人ID
	 */
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	/**
	 * 获取：创建人ID
	 */
	public String getCrtUserId() {
		return crtUserId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCrtTime() {
		return crtTime;
	}
	/**
	 * 设置：最后更新人
	 */
	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}
	/**
	 * 获取：最后更新人
	 */
	public String getUpdUserName() {
		return updUserName;
	}
	/**
	 * 设置：最后更新人ID
	 */
	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}
	/**
	 * 获取：最后更新人ID
	 */
	public String getUpdUserId() {
		return updUserId;
	}
	/**
	 * 设置：最后更新时间
	 */
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	/**
	 * 获取：最后更新时间
	 */
	public Date getUpdTime() {
		return updTime;
	}
	/**
	 * 设置：
	 */
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	/**
	 * 获取：
	 */
	public String getAttr1() {
		return attr1;
	}
	/**
	 * 设置：
	 */
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	/**
	 * 获取：
	 */
	public String getAttr2() {
		return attr2;
	}
	/**
	 * 设置：
	 */
	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}
	/**
	 * 获取：
	 */
	public String getAttr3() {
		return attr3;
	}
	/**
	 * 设置：
	 */
	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}
	/**
	 * 获取：
	 */
	public String getAttr4() {
		return attr4;
	}
	/**
	 * 设置：
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：是否超级租户
	 */
	public void setIsSuperTenant(String isSuperTenant) {
		this.isSuperTenant = isSuperTenant;
	}
	/**
	 * 获取：是否超级租户
	 */
	public String getIsSuperTenant() {
		return isSuperTenant;
	}
	/**
	 * 设置：
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：拥有者
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * 获取：拥有者
	 */
	public String getOwner() {
		return owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	/**
	 * 设置：商户LOGO
	 */
	public String getLogoImg() {
		return logoImg;
	}
	/**
	 * 获取：商户LOGO
	 */
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getQualificImg() {
		return qualificImg;
	}

	public void setQualificImg(String qualificImg) {
		this.qualificImg = qualificImg;
	}

	public String getCenterCityName() {
		return centerCityName;
	}

	public void setCenterCityName(String centerCityName) {
		this.centerCityName = centerCityName;
	}
}
