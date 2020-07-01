package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计划工单设备表
 * 
 * @author zxl
 * @Date 2019-02-27 15:56:15
 */
@Table(name = "biz_plan_wo_eq")
public class BizPlanWoEq implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //英文名称
    @Column(name = "eq_id")
    private String eqId;
	
	    //英文名称
    @Column(name = "eq_name")
    private String eqName;

	@Column(name = "eq_code")
	private String eqCode;
	
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
	 * 设置：英文名称
	 */
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	/**
	 * 获取：英文名称
	 */
	public String getEqId() {
		return eqId;
	}
	/**
	 * 设置：英文名称
	 */
	public void setEqName(String eqName) {
		this.eqName = eqName;
	}
	/**
	 * 获取：英文名称
	 */
	public String getEqName() {
		return eqName;
	}

	public String getEqCode() {
		return eqCode;
	}

	public void setEqCode(String eqCode) {
		this.eqCode = eqCode;
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
