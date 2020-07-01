package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计划工单步骤表
 * 
 * @author zxl
 * @Date 2019-02-27 15:56:15
 */
@Table(name = "biz_plan_wo_pmps")
public class BizPlanWoPmps implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //步骤文档
    @Column(name = "doc")
    private String doc;
	
	    //步骤描述
    @Column(name = "instructions")
    private String instructions;
	
	    //程序编码
    @Column(name = "pmp_id")
    private String pmpId;
	
	    //步骤编码
    @Column(name = "pmps_id")
    private Integer pmpsId;
	
	    //操作类型
    @Column(name = "op_type")
    private String opType;
	
	    //选项列表
    @Column(name = "op_val")
    private String opVal;
	
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
	 * 设置：步骤文档
	 */
	public void setDoc(String doc) {
		this.doc = doc;
	}
	/**
	 * 获取：步骤文档
	 */
	public String getDoc() {
		return doc;
	}
	/**
	 * 设置：步骤描述
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	/**
	 * 获取：步骤描述
	 */
	public String getInstructions() {
		return instructions;
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
	 * 设置：步骤编码
	 */
	public void setPmpsId(Integer pmpsId) {
		this.pmpsId = pmpsId;
	}
	/**
	 * 获取：步骤编码
	 */
	public Integer getPmpsId() {
		return pmpsId;
	}
	/**
	 * 设置：操作类型
	 */
	public void setOpType(String opType) {
		this.opType = opType;
	}
	/**
	 * 获取：操作类型
	 */
	public String getOpType() {
		return opType;
	}
	/**
	 * 设置：选项列表
	 */
	public void setOpVal(String opVal) {
		this.opVal = opVal;
	}
	/**
	 * 获取：选项列表
	 */
	public String getOpVal() {
		return opVal;
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
