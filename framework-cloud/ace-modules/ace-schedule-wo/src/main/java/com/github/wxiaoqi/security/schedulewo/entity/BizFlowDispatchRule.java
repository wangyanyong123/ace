package com.github.wxiaoqi.security.schedulewo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 流程调度规则
 * 
 * @author zxl
 * @Date 2018-12-05 10:53:46
 */
@Table(name = "biz_flow_dispatch_rule")
public class BizFlowDispatchRule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //流程ID
    @Column(name = "flow_id")
    private String flowId;
	
	    //cron表达式
    @Column(name = "crontab")
    private String crontab;
	
	    //匹配次数
    @Column(name = "dispatch_time")
    private Integer dispatchTime;
	
	    //执行间隔时间（单位：分钟）
    @Column(name = "minutes")
    private Integer minutes;
	
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
	 * 设置：cron表达式
	 */
	public void setCrontab(String crontab) {
		this.crontab = crontab;
	}
	/**
	 * 获取：cron表达式
	 */
	public String getCrontab() {
		return crontab;
	}
	/**
	 * 设置：匹配次数
	 */
	public void setDispatchTime(Integer dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	/**
	 * 获取：匹配次数
	 */
	public Integer getDispatchTime() {
		return dispatchTime;
	}
	/**
	 * 设置：执行间隔时间（单位：分钟）
	 */
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	/**
	 * 获取：执行间隔时间（单位：分钟）
	 */
	public Integer getMinutes() {
		return minutes;
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
