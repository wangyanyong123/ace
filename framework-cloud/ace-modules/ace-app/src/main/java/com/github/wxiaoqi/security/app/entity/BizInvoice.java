package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 发票抬头表
 * 
 * @author huangxl
 * @Date 2019-04-16 10:49:40
 */
@Table(name = "biz_invoice")
public class BizInvoice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //发票类型(1-个人2-公司)
    @Column(name = "invoice_type")
    private String invoiceType;
	
	    //发票名称
    @Column(name = "invoice_name")
    private String invoiceName;
	
	    //税号
    @Column(name = "duty_num")
    private String dutyNum;
	
	    //(1-默认0-非默认)
    @Column(name = "is_default")
    private String isDefault;
		//单位地址
	@Column(name = "unit_addr")
	private String unitAddr;
		//电话号码
	@Column(name = "telphone")
	private String telphone;
		//开户银行
	@Column(name = "bank_name")
	private String bankName;
		//银行账户
	@Column(name = "bank_num")
	private String bankNum;
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
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
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
	 * 设置：发票类型(1-个人2-公司)
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	/**
	 * 获取：发票类型(1-个人2-公司)
	 */
	public String getInvoiceType() {
		return invoiceType;
	}
	/**
	 * 设置：发票名称
	 */
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	/**
	 * 获取：发票名称
	 */
	public String getInvoiceName() {
		return invoiceName;
	}
	/**
	 * 设置：税号
	 */
	public void setDutyNum(String dutyNum) {
		this.dutyNum = dutyNum;
	}
	/**
	 * 获取：税号
	 */
	public String getDutyNum() {
		return dutyNum;
	}
	/**
	 * 设置：(1-默认0-非默认)
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * 获取：(1-默认0-非默认)
	 */
	public String getIsDefault() {
		return isDefault;
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

	public String getUnitAddr() {
		return unitAddr;
	}

	public void setUnitAddr(String unitAddr) {
		this.unitAddr = unitAddr;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
}
