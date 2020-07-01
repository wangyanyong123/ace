package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计划工单程序表
 * 
 * @author zxl
 * @Date 2019-02-27 15:56:15
 */
@Table(name = "biz_plan_wo_pmp")
public class BizPlanWoPmp implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //程序说明
    @Column(name = "description")
    private String description;
	
	    //程序类型
    @Column(name = "pmp_type")
    private String pmpType;
	
	    //程序编码
    @Column(name = "pmp_id")
    private String pmpId;
	
	    //数据状态
    @Column(name = "data_status")
    private Integer dataStatus;
	
	    //程序名称
    @Column(name = "pmp_name")
    private String pmpName;
	
	    //项目id
    @Column(name = "site_id")
    private String siteId;
	
	    //性质类型
    @Column(name = "extend_type")
    private String extendType;
	
	    //技能
    @Column(name = "tr_list")
    private String trList;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;
	
	    //
    @Column(name = "create_By")
    private String createBy;
	

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
	 * 设置：程序说明
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：程序说明
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：程序类型
	 */
	public void setPmpType(String pmpType) {
		this.pmpType = pmpType;
	}
	/**
	 * 获取：程序类型
	 */
	public String getPmpType() {
		return pmpType;
	}
	/**
	 * 设置：程序编码
	 */
	public void setPmpId(String pmpId) {
		this.pmpId = pmpId;
	}
	/**
	 * 获取：程序编码
	 */
	public String getPmpId() {
		return pmpId;
	}
	/**
	 * 设置：数据状态
	 */
	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}
	/**
	 * 获取：数据状态
	 */
	public Integer getDataStatus() {
		return dataStatus;
	}
	/**
	 * 设置：程序名称
	 */
	public void setPmpName(String pmpName) {
		this.pmpName = pmpName;
	}
	/**
	 * 获取：程序名称
	 */
	public String getPmpName() {
		return pmpName;
	}
	/**
	 * 设置：项目id
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * 获取：项目id
	 */
	public String getSiteId() {
		return siteId;
	}
	/**
	 * 设置：性质类型
	 */
	public void setExtendType(String extendType) {
		this.extendType = extendType;
	}
	/**
	 * 获取：性质类型
	 */
	public String getExtendType() {
		return extendType;
	}
	/**
	 * 设置：技能
	 */
	public void setTrList(String trList) {
		this.trList = trList;
	}
	/**
	 * 获取：技能
	 */
	public String getTrList() {
		return trList;
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
}
