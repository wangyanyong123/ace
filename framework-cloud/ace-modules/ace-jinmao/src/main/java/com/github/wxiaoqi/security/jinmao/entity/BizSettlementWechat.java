package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 租户(公司、商户)微信账号信息表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
@Table(name = "biz_settlement_wechat")
public class BizSettlementWechat implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键ID
    @Id
    private String id;

    	//是否商城管理支付账号
	@Column(name = "is_PayInMall")
	private String isPayInMall;
	    //租户(公司、商户)ID
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //微信支付appid
    @Column(name = "wechat_appid")
    private String wechatAppid;
	
	    //微信支付证书密码
    @Column(name = "wechat_code")
    private String wechatCode;
	
	    //微信支付证书地址
    @Column(name = "wechat_certificate")
    private String wechatCertificate;
	
	    //微信支付手续费
    @Column(name = "wechat_fee")
    private Double wechatFee;
	
	    //微信支付商户帐号
    @Column(name = "wechat_account")
    private String wechatAccount;
	
	    //微信支付密钥
    @Column(name = "wechat_key")
    private String wechatKey;
	
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
	 * 设置：微信支付appid
	 */
	public void setWechatAppid(String wechatAppid) {
		this.wechatAppid = wechatAppid;
	}
	/**
	 * 获取：微信支付appid
	 */
	public String getWechatAppid() {
		return wechatAppid;
	}
	/**
	 * 设置：微信支付证书密码
	 */
	public void setWechatCode(String wechatCode) {
		this.wechatCode = wechatCode;
	}
	/**
	 * 获取：微信支付证书密码
	 */
	public String getWechatCode() {
		return wechatCode;
	}
	/**
	 * 设置：微信支付证书地址
	 */
	public void setWechatCertificate(String wechatCertificate) {
		this.wechatCertificate = wechatCertificate;
	}
	/**
	 * 获取：微信支付证书地址
	 */
	public String getWechatCertificate() {
		return wechatCertificate;
	}
	/**
	 * 设置：微信支付手续费
	 */
	public void setWechatFee(Double wechatFee) {
		this.wechatFee = wechatFee;
	}
	/**
	 * 获取：微信支付手续费
	 */
	public Double getWechatFee() {
		return wechatFee;
	}
	/**
	 * 设置：微信支付商户帐号
	 */
	public void setWechatAccount(String wechatAccount) {
		this.wechatAccount = wechatAccount;
	}
	/**
	 * 获取：微信支付商户帐号
	 */
	public String getWechatAccount() {
		return wechatAccount;
	}
	/**
	 * 设置：微信支付密钥
	 */
	public void setWechatKey(String wechatKey) {
		this.wechatKey = wechatKey;
	}
	/**
	 * 获取：微信支付密钥
	 */
	public String getWechatKey() {
		return wechatKey;
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

	public String getIsPayInMall() {
		return isPayInMall;
	}

	public void setIsPayInMall(String isPayInMall) {
		this.isPayInMall = isPayInMall;
	}
}
