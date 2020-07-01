package com.github.wxiaoqi.security.jinmao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 商户协议表
 * 
 * @author zxl
 * @Date 2018-12-05 10:04:44
 */
@Table(name = "base_tenant_contract")
public class BaseTenantContract implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //商户ID
    @Column(name = "enterprise_id")
    private String enterpriseId;
	
	    //协议签署人
    @Column(name = "protocol_person")
    private String protocolPerson;
	
	    //协议签署人联系电话
    @Column(name = "protocol_tel")
    private String protocolTel;
	
	    //签约日期
    @Column(name = "sign_date")
    private Date signDate;
	
	    //平台保证金
    @Column(name = "bond")
    private BigDecimal bond;
	
	    //年费
    @Column(name = "annual_fee")
    private BigDecimal annualFee;
	
	    //协议文件(存图片取图片的标识)
    @Column(name = "image_id")
    private String imageId;
	
	    //收款账户类型(1-支付宝，2-微信，3-银行卡)
    @Column(name = "account_type")
    private String accountType;
	
	    //支付宝账号名/微信账号名/银行卡账号名
    @Column(name = "account_name")
    private String accountName;
	
	    //支付宝账号/微信账号/银行卡账号
    @Column(name = "account_number")
    private String accountNumber;
	
	    //开户银行名称
    @Column(name = "account_book_name")
    private String accountBookName;
	
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
	 * 设置：商户ID
	 */
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	/**
	 * 获取：商户ID
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}
	/**
	 * 设置：协议签署人
	 */
	public void setProtocolPerson(String protocolPerson) {
		this.protocolPerson = protocolPerson;
	}
	/**
	 * 获取：协议签署人
	 */
	public String getProtocolPerson() {
		return protocolPerson;
	}
	/**
	 * 设置：协议签署人联系电话
	 */
	public void setProtocolTel(String protocolTel) {
		this.protocolTel = protocolTel;
	}
	/**
	 * 获取：协议签署人联系电话
	 */
	public String getProtocolTel() {
		return protocolTel;
	}
	/**
	 * 设置：签约日期
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	/**
	 * 获取：签约日期
	 */
	public Date getSignDate() {
		return signDate;
	}
	/**
	 * 设置：平台保证金
	 */
	public void setBond(BigDecimal bond) {
		this.bond = bond;
	}
	/**
	 * 获取：平台保证金
	 */
	public BigDecimal getBond() {
		return bond;
	}
	/**
	 * 设置：年费
	 */
	public void setAnnualFee(BigDecimal annualFee) {
		this.annualFee = annualFee;
	}
	/**
	 * 获取：年费
	 */
	public BigDecimal getAnnualFee() {
		return annualFee;
	}
	/**
	 * 设置：协议文件(存图片取图片的标识)
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	/**
	 * 获取：协议文件(存图片取图片的标识)
	 */
	public String getImageId() {
		return imageId;
	}
	/**
	 * 设置：收款账户类型(1-支付宝，2-微信，3-银行卡)
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * 获取：收款账户类型(1-支付宝，2-微信，3-银行卡)
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * 设置：支付宝账号名/微信账号名/银行卡账号名
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取：支付宝账号名/微信账号名/银行卡账号名
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置：支付宝账号/微信账号/银行卡账号
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * 获取：支付宝账号/微信账号/银行卡账号
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * 设置：开户银行名称
	 */
	public void setAccountBookName(String accountBookName) {
		this.accountBookName = accountBookName;
	}
	/**
	 * 获取：开户银行名称
	 */
	public String getAccountBookName() {
		return accountBookName;
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
