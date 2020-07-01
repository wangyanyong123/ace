package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 物业评价详情表
 * 
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
@Table(name = "biz_evaluate_property_scale")
public class BizEvaluatePropertyScale implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //项目id
    @Column(name = "project_id")
    private String projectId;
	
	    //星值评价等级(1-*,2-**,3-***,4-****,5-*****)
    @Column(name = "evaluate_type")
    private int evaluateType;
	
	    //分值评价百分比
    @Column(name = "evaluate_scale")
    private String evaluateScale;
	
	    //评价数量
    @Column(name = "evaluate_sum")
    private Integer evaluateSum;
	
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
	 * 设置：项目id
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目id
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：星值评价等级(1-*,2-**,3-***,4-****,5-*****)
	 */
	public void setEvaluateType(int evaluateType) {
		this.evaluateType = evaluateType;
	}
	/**
	 * 获取：星值评价等级(1-*,2-**,3-***,4-****,5-*****)
	 */
	public int getEvaluateType() {
		return evaluateType;
	}
	/**
	 * 设置：分值评价百分比
	 */
	public void setEvaluateScale(String evaluateScale) {
		this.evaluateScale = evaluateScale;
	}
	/**
	 * 获取：分值评价百分比
	 */
	public String getEvaluateScale() {
		return evaluateScale;
	}
	/**
	 * 设置：评价数量
	 */
	public void setEvaluateSum(Integer evaluateSum) {
		this.evaluateSum = evaluateSum;
	}
	/**
	 * 获取：评价数量
	 */
	public Integer getEvaluateSum() {
		return evaluateSum;
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
}
