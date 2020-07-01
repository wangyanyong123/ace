package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 决策表
 * 
 * @author guohao
 * @Date 2020-06-04 21:29:06
 */
@Table(name = "biz_decision")
public class BizDecision implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //项目id
    @Column(name = "project_id")
    private String projectId;
	
	    //事件类型 1：一般事件 2：特殊事件
    @Column(name = "event_type")
    private Integer eventType;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //内容
    @Column(name = "content")
    private String content;
	
	    //
    @Column(name = "start_time")
    private Date startTime;
	
	    //
    @Column(name = "end_time")
    private Date endTime;
	
	    //发布状态 0：未发布， 1：已发布
    @Column(name = "publish_status")
    private Integer publishStatus;
	
	    //决策结果 0:决策中，1：通过 2：未通过
    @Column(name = "decision_status")
    private Integer decisionStatus;
	
	    //进度比例
    @Column(name = "progress_rate")
    private BigDecimal progressRate;
	
	    //
    @Column(name = "remark")
    private String remark;
	
	    //项目房屋数量
    @Column(name = "house_count")
    private Integer houseCount;
	
	    //房屋总面积
    @Column(name = "house_area")
    private BigDecimal houseArea;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;
	
	    //数据状态
    @Column(name = "status")
    private String status;
	

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
	 * 设置：事件类型 1：一般事件 2：特殊事件
	 */
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	/**
	 * 获取：事件类型 1：一般事件 2：特殊事件
	 */
	public Integer getEventType() {
		return eventType;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：发布状态 0：未发布， 1：已发布
	 */
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
	/**
	 * 获取：发布状态 0：未发布， 1：已发布
	 */
	public Integer getPublishStatus() {
		return publishStatus;
	}
	/**
	 * 设置：决策结果 0:决策中，1：通过 2：未通过
	 */
	public void setDecisionStatus(Integer decisionStatus) {
		this.decisionStatus = decisionStatus;
	}
	/**
	 * 获取：决策结果 0:决策中，1：通过 2：未通过
	 */
	public Integer getDecisionStatus() {
		return decisionStatus;
	}
	/**
	 * 设置：进度比例
	 */
	public void setProgressRate(BigDecimal progressRate) {
		this.progressRate = progressRate;
	}
	/**
	 * 获取：进度比例
	 */
	public BigDecimal getProgressRate() {
		return progressRate;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：项目房屋数量
	 */
	public void setHouseCount(Integer houseCount) {
		this.houseCount = houseCount;
	}
	/**
	 * 获取：项目房屋数量
	 */
	public Integer getHouseCount() {
		return houseCount;
	}
	/**
	 * 设置：房屋总面积
	 */
	public void setHouseArea(BigDecimal houseArea) {
		this.houseArea = houseArea;
	}
	/**
	 * 获取：房屋总面积
	 */
	public BigDecimal getHouseArea() {
		return houseArea;
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
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
	/**
	 * 获取：
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
	/**
	 * 设置：数据状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：数据状态
	 */
	public String getStatus() {
		return status;
	}
}
