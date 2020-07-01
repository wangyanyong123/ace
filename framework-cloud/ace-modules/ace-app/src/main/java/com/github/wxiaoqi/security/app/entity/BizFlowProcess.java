package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 流程工序表
 * 
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
@Table(name = "biz_flow_process")
public class BizFlowProcess implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //工序ID
    @Id
    private String id;
	
	    //流程ID
    @Column(name = "flow_Id")
    private String flowId;
	
	    //序号
    @Column(name = "view_Sort")
    private String viewSort;
	
	    //工序名称
    @Column(name = "step_Name")
    private String stepName;
	
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
	 * 设置：工序ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：工序ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：流程ID
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	/**
	 * 获取：流程ID
	 */
	public String getFlowId() {
		return flowId;
	}
	/**
	 * 设置：序号
	 */
	public void setViewSort(String viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：序号
	 */
	public String getViewSort() {
		return viewSort;
	}
	/**
	 * 设置：工序名称
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	/**
	 * 获取：工序名称
	 */
	public String getStepName() {
		return stepName;
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
