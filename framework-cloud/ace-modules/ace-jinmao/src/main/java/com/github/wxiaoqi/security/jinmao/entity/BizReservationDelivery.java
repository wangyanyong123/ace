package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 预约服务配送范围
 * 
 * @author guohao
 * @Date 2020-06-11 12:19:38
 */
@Table(name = "biz_reservation_delivery")
public class BizReservationDelivery implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //商户ID
    @Column(name = "company_id")
    private String companyId;
	
	    //
    @Column(name = "product_id")
    private String productId;
	
	    //区域编码
    @Column(name = "proc_code")
    private String procCode;
	
	    //区域名称
    @Column(name = "proc_name")
    private String procName;
	
	    //城市编码
    @Column(name = "city_code")
    private String cityCode;
	
	    //城市名称
    @Column(name = "city_name")
    private String cityName;
	
	    //区域全称
    @Column(name = "full_Name")
    private String fullName;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;
	

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
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：商户ID
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取：
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置：区域编码
	 */
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	/**
	 * 获取：区域编码
	 */
	public String getProcCode() {
		return procCode;
	}
	/**
	 * 设置：区域名称
	 */
	public void setProcName(String procName) {
		this.procName = procName;
	}
	/**
	 * 获取：区域名称
	 */
	public String getProcName() {
		return procName;
	}
	/**
	 * 设置：城市编码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * 获取：城市编码
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 设置：城市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 获取：城市名称
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 设置：区域全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * 获取：区域全称
	 */
	public String getFullName() {
		return fullName;
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
