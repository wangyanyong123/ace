package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 租户(公司、商户)支付宝账号信息表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
@Table(name = "biz_settlement_ali")
public class BizSettlementAli implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键ID
    @Id
    private String id;
		//是否商城支付管理
	@Column(name = "is_PayInMall")
    private String isPayInMall;
	
	    //租户(公司、商户)ID
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //支付宝账号
    @Column(name = "alipay_account_no")
    private String alipayAccountNo;
	
	    //支付宝商户名
    @Column(name = "alipay_account_name")
    private String alipayAccountName;
	
	    //支付宝伙伴ID
    @Column(name = "alipay_partner")
    private String alipayPartner;
	
	    //支付宝Key
    @Column(name = "alipay_key")
    private String alipayKey;
	
	    //支付宝rsa私钥内容
    @Column(name = "rsa")
    private String rsa;

        //支付宝rsa公钥内容
	@Column(name = "ali_public_key")
    private String aliPublicKey;

	@Column(name = "app_ali_public_key")
	private String appAliPublicKey;

	@Column(name = "app_rsa2")
	private String appRsa2;

	@Column(name = "app_id")
	private String appId;
	
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
	 * 设置：主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：租户(公司、商户)ID
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户(公司、商户)ID
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：支付宝账号
	 */
	public void setAlipayAccountNo(String alipayAccountNo) {
		this.alipayAccountNo = alipayAccountNo;
	}
	/**
	 * 获取：支付宝账号
	 */
	public String getAlipayAccountNo() {
		return alipayAccountNo;
	}
	/**
	 * 设置：支付宝商户名
	 */
	public void setAlipayAccountName(String alipayAccountName) {
		this.alipayAccountName = alipayAccountName;
	}
	/**
	 * 获取：支付宝商户名
	 */
	public String getAlipayAccountName() {
		return alipayAccountName;
	}
	/**
	 * 设置：支付宝伙伴ID
	 */
	public void setAlipayPartner(String alipayPartner) {
		this.alipayPartner = alipayPartner;
	}
	/**
	 * 获取：支付宝伙伴ID
	 */
	public String getAlipayPartner() {
		return alipayPartner;
	}
	/**
	 * 设置：支付宝Key
	 */
	public void setAlipayKey(String alipayKey) {
		this.alipayKey = alipayKey;
	}
	/**
	 * 获取：支付宝Key
	 */
	public String getAlipayKey() {
		return alipayKey;
	}
	/**
	 * 设置：支付宝rsa私钥内容
	 */
	public void setRsa(String rsa) {
		this.rsa = rsa;
	}
	/**
	 * 获取：支付宝rsa私钥内容
	 */
	public String getRsa() {
		return rsa;
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


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAliPublicKey() {
		return aliPublicKey;
	}

	public void setAliPublicKey(String aliPublicKey) {
		this.aliPublicKey = aliPublicKey;
	}

	public String getIsPayInMall() {
		return isPayInMall;
	}

	public void setIsPayInMall(String isPayInMall) {
		this.isPayInMall = isPayInMall;
	}

	public String getAppAliPublicKey() {
		return appAliPublicKey;
	}

	public void setAppAliPublicKey(String appAliPublicKey) {
		this.appAliPublicKey = appAliPublicKey;
	}

	public String getAppRsa2() {
		return appRsa2;
	}

	public void setAppRsa2(String appRsa2) {
		this.appRsa2 = appRsa2;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}
