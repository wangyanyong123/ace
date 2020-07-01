package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author huangxl
 * @Date 2019-03-19 15:54:18
 */
@Table(name = "biz_account_statement")
public class BizAccountStatement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //账单号
    @Id
    private String id;
	
	    //账单编码
    @Column(name = "bill_number")
    private String billNumber;
	
	    //商户id
    @Column(name = "tenant_id")
    private String tenantId;

	//对账周期
	@Column(name = "settlement_cycle_start")
	private Date settlementCycleStart;
	
	    //对账周期
    @Column(name = "settlement_cycle_end")
    private Date settlementCycleEnd;
	
	    //营收金额
    @Column(name = "revenue_money")
    private String revenueMoney;
	
	    //结算金额
    @Column(name = "balance_money")
    private String balanceMoney;
	
	    //结算状态,0：未结算，1：结算中，2：已结算
    @Column(name = "balance_status")
    private String balanceStatus;
	
	    //结算图，结算完成有效
    @Column(name = "balance_img")
    private String balanceImg;
	

	/**
	 * 设置：账单号
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：账单号
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：账单编码
	 */
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	/**
	 * 获取：账单编码
	 */
	public String getBillNumber() {
		return billNumber;
	}
	/**
	 * 设置：商户id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：商户id
	 */
	public String getTenantId() {
		return tenantId;
	}

	public Date getSettlementCycleStart() {
		return settlementCycleStart;
	}

	public void setSettlementCycleStart(Date settlementCycleStart) {
		this.settlementCycleStart = settlementCycleStart;
	}

	public Date getSettlementCycleEnd() {
		return settlementCycleEnd;
	}

	public void setSettlementCycleEnd(Date settlementCycleEnd) {
		this.settlementCycleEnd = settlementCycleEnd;
	}

	/**
	 * 设置：营收金额
	 */
	public void setRevenueMoney(String revenueMoney) {
		this.revenueMoney = revenueMoney;
	}
	/**
	 * 获取：营收金额
	 */
	public String getRevenueMoney() {
		return revenueMoney;
	}
	/**
	 * 设置：结算金额
	 */
	public void setBalanceMoney(String balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
	/**
	 * 获取：结算金额
	 */
	public String getBalanceMoney() {
		return balanceMoney;
	}
	/**
	 * 设置：结算状态,0：未结算，1：结算中，2：已结算
	 */
	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
	}
	/**
	 * 获取：结算状态,0：未结算，1：结算中，2：已结算
	 */
	public String getBalanceStatus() {
		return balanceStatus;
	}
	/**
	 * 设置：结算图，结算完成有效
	 */
	public void setBalanceImg(String balanceImg) {
		this.balanceImg = balanceImg;
	}
	/**
	 * 获取：结算图，结算完成有效
	 */
	public String getBalanceImg() {
		return balanceImg;
	}
}
