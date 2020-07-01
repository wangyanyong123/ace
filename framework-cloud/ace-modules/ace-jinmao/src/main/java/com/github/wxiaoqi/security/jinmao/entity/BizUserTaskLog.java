package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 运营服务-用户任务日志表
 * 
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@Table(name = "biz_user_task_log")
public class BizUserTaskLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //记录当天的任务
    @Column(name = "task_in_records")
    private String taskInRecords;
	
	    //任务完成日期
    @Column(name = "task_date")
    private Date taskDate;
	
	    //任务积分
    @Column(name = "task_points")
    private Integer taskPoints;
	
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
	 * 设置：用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：记录当天的任务
	 */
	public void setTaskInRecords(String taskInRecords) {
		this.taskInRecords = taskInRecords;
	}
	/**
	 * 获取：记录当天的任务
	 */
	public String getTaskInRecords() {
		return taskInRecords;
	}
	/**
	 * 设置：任务完成日期
	 */
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	/**
	 * 获取：任务完成日期
	 */
	public Date getTaskDate() {
		return taskDate;
	}
	/**
	 * 设置：任务积分
	 */
	public void setTaskPoints(Integer taskPoints) {
		this.taskPoints = taskPoints;
	}
	/**
	 * 获取：任务积分
	 */
	public Integer getTaskPoints() {
		return taskPoints;
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
