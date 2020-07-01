package com.github.wxiaoqi.security.merchant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 快递公司
 * 
 * @author wangyanyong
 * @Date 2020-04-24 16:13:06
 */
@Table(name = "biz_express_company")
public class BizExpressCompany implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //快递公司编码
    @Column(name = "company_code")
    private String companyCode;
	
	    //快递公司名称
    @Column(name = "company_name")
    private String companyName;
	
	    //0:未删除；1：已删除
    @Column(name = "status")
    private Boolean status;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建时间
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //修改时间
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //删除时间
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
	 * 设置：快递公司编码
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 获取：快递公司编码
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * 设置：快递公司名称
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * 获取：快递公司名称
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * 设置：0:未删除；1：已删除
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	/**
	 * 获取：0:未删除；1：已删除
	 */
	public Boolean getStatus() {
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
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
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
	 * 设置：修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置：删除时间
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	/**
	 * 获取：删除时间
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
}
