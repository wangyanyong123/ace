package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 订单账本表
 *
 * @author zxl
 * @Date 2018-12-14 17:44:12
 */
@Table(name = "biz_account_book")
public class BizAccountBook implements Serializable {
	private static final long serialVersionUID = 1L;

	    //ID
    @Id
    private String id;

	    //订单id
    @Column(name = "sub_id")
    private String subId;

    //业务类型 0：旧业务 ，1：商品订单，2：服务订单
    @Column(name = "bus_type")
    private Integer busType;

	    //支付凭证
    @Column(name = "pay_Id")
    private String payId;

	    //实收金额ID
    @Column(name = "actual_Id")
    private String actualId;

	//多个公司合并的支付id
	@Column(name = "account_pid")
	private String accountPid;

	    //支付状态(1-未支付,2-已支付)
    @Column(name = "pay_status")
    private String payStatus;

	    //支付类型(1-支付宝,2-微信)
    @Column(name = "pay_type")
    private String payType;

	    //支付时间
    @Column(name = "pay_date")
    private Date payDate;

    //微信支付宝应用ID
    @Column(name = "app_id")
    private String appId;

	    //支付宝/微信支付商家账户
    @Column(name = "settle_account")
    private String settleAccount;

	//实收金额=应收金额-优惠金额
	@Column(name = "actual_cost")
	private BigDecimal actualCost;

	@Column(name = "refund_status")
	private String refundStatus;

	@Column(name = "refund_amount")
	private BigDecimal refundAmount;

	@Column(name = "refund_fail_reason")
	private String refundFailReason;

	@Column(name = "refund_reason")
	private String refundReason;

	//支付异常日志
	@Column(name = "pay_fail_remark")
	private String payFailRemark;

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
	 * 设置：订单id
	 */
	public void setSubId(String subId) {
		this.subId = subId;
	}
	/**
	 * 获取：订单id
	 */
	public String getSubId() {
		return subId;
	}
	/**
	 * 设置：支付凭证
	 */
	public void setPayId(String payId) {
		this.payId = payId;
	}
	/**
	 * 获取：支付凭证
	 */
	public String getPayId() {
		return payId;
	}
	/**
	 * 设置：实收金额ID
	 */
	public void setActualId(String actualId) {
		this.actualId = actualId;
	}
	/**
	 * 获取：实收金额ID
	 */
	public String getActualId() {
		return actualId;
	}
	/**
	 * 设置：支付状态(1-未支付,2-已支付)
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * 获取：支付状态(1-未支付,2-已支付)
	 */
	public String getPayStatus() {
		return payStatus;
	}
	/**
	 * 设置：支付类型(1-支付宝,2-微信)
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	/**
	 * 获取：支付类型(1-支付宝,2-微信)
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * 设置：支付时间
	 */
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	/**
	 * 获取：支付时间
	 */
	public Date getPayDate() {
		return payDate;
	}
	/**
	 * 设置：支付宝/微信支付商家账户
	 */
	public void setSettleAccount(String settleAccount) {
		this.settleAccount = settleAccount;
	}
	/**
	 * 获取：支付宝/微信支付商家账户
	 */
	public String getSettleAccount() {
		return settleAccount;
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

	public String getAccountPid() {
		return accountPid;
	}

	public void setAccountPid(String accountPid) {
		this.accountPid = accountPid;
	}

	public BigDecimal getActualCost() {
		return actualCost;
	}

	public void setActualCost(BigDecimal actualCost) {
		this.actualCost = actualCost;
	}

	public String getPayFailRemark() {
		return payFailRemark;
	}

	public void setPayFailRemark(String payFailRemark) {
		this.payFailRemark = payFailRemark;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundFailReason() {
		return refundFailReason;
	}

	public void setRefundFailReason(String refundFailReason) {
		this.refundFailReason = refundFailReason;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getBusType() {
		return busType;
	}

	public void setBusType(Integer busType) {
		this.busType = busType;
	}
}
