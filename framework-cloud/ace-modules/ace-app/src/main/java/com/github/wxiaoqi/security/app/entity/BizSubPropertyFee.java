package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 物业缴费订单明细表
 * 
 * @author huangxl
 * @Date 2019-01-29 17:31:29
 */
@Table(name = "biz_sub_property_fee")
public class BizSubPropertyFee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //订单id
    @Column(name = "sub_Id")
    private String subId;

    	//应收id
    @Column(name = "should_id")
	private String shouldId;
	
	    //所属账期格式(yyyyMM)
    @Column(name = "should_date")
    private String shouldDate;
	
	    //收费科目(1-物业管理费,2-车位租赁费)
    @Column(name = "item")
    private String item;
	
	    //数量
    @Column(name = "sub_num")
    private Integer subNum;

	    //应收金额（元）
    @Column(name = "should_amount")
    private BigDecimal shouldAmount;
	
	    //状态：0、删除；1、正常
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
	 * 设置：所属账期格式(yyyyMM)
	 */
	public void setShouldDate(String shouldDate) {
		this.shouldDate = shouldDate;
	}
	/**
	 * 获取：所属账期格式(yyyyMM)
	 */
	public String getShouldDate() {
		return shouldDate;
	}
	/**
	 * 设置：收费科目(1-物业管理费,2-车位租赁费)
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * 获取：收费科目(1-物业管理费,2-车位租赁费)
	 */
	public String getItem() {
		return item;
	}
	/**
	 * 设置：数量
	 */
	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}
	/**
	 * 获取：数量
	 */
	public Integer getSubNum() {
		return subNum;
	}
	/**
	 * 设置：应收金额（元）
	 */
	public void setShouldAmount(BigDecimal shouldAmount) {
		this.shouldAmount = shouldAmount;
	}
	/**
	 * 获取：应收金额（元）
	 */
	public BigDecimal getShouldAmount() {
		return shouldAmount;
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

	public String getShouldId() {
		return shouldId;
	}

	public void setShouldId(String shouldId) {
		this.shouldId = shouldId;
	}

}
