package com.github.wxiaoqi.security.merchant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 订单物流信息表
 * 
 * @author wangyanyong
 * @Date 2020-04-24 16:49:37
 */
@Table(name = "biz_order_logistics")
public class BizOrderLogistics implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "order_id")
    private String orderId;
	
	    //
    @Column(name = "order_detail_id")
    private String orderDetailId;
	
	    //物流公司编码
    @Column(name = "logistics_code")
    private String logisticsCode;
	
	    //物流公司名称
    @Column(name = "logistics_name")
    private String logisticsName;
	
	    //物流单号
    @Column(name = "logistics_no")
    private String logisticsNo;
	
	    //发货时间
    @Column(name = "send_time")
    private Date sendTime;
	
	    //备注
    @Column(name = "remark")
    private String remark;
	
	    //数据状态 1：有效 0：无效
    @Column(name = "status")
    private String status;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;
	

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
	 * 设置：
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：
	 */
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	/**
	 * 获取：
	 */
	public String getOrderDetailId() {
		return orderDetailId;
	}
	/**
	 * 设置：物流公司编码
	 */
	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	/**
	 * 获取：物流公司编码
	 */
	public String getLogisticsCode() {
		return logisticsCode;
	}
	/**
	 * 设置：物流公司名称
	 */
	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	/**
	 * 获取：物流公司名称
	 */
	public String getLogisticsName() {
		return logisticsName;
	}
	/**
	 * 设置：物流单号
	 */
	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}
	/**
	 * 获取：物流单号
	 */
	public String getLogisticsNo() {
		return logisticsNo;
	}
	/**
	 * 设置：发货时间
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	/**
	 * 获取：发货时间
	 */
	public Date getSendTime() {
		return sendTime;
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
	 * 设置：数据状态 1：有效 0：无效
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：数据状态 1：有效 0：无效
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置：
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * 获取：
	 */
	public String getModifyBy() {
		return modifyBy;
	}
	/**
	 * 设置：
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	/**
	 * 获取：
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
}
