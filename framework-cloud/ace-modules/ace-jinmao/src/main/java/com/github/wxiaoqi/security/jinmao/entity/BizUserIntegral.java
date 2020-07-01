package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 运营服务-用户综合积分表
 * 
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@Table(name = "biz_user_integral")
public class BizUserIntegral implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //累计签到积分
    @Column(name = "sign_points")
    private Integer signPoints;
	
	    //累计任务积分
    @Column(name = "task_points")
    private Integer taskPoints;
	
	    //消费积分
    @Column(name = "consume_points")
    private Integer consumePoints;
	
	    //当前总积分=累计签到积+累计任务积分-消费积分
    @Column(name = "total_points")
    private Integer totalPoints;
	
	    //连续签到累计天数
    @Column(name = "sign_count")
    private Integer signCount;
	
	    //时间戳
    @Column(name = "time_Stamp")
    private String timeStamp;
	
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
	
	    //
    @Column(name = "status")
    private String status;
	

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
	 * 设置：累计签到积分
	 */
	public void setSignPoints(Integer signPoints) {
		this.signPoints = signPoints;
	}
	/**
	 * 获取：累计签到积分
	 */
	public Integer getSignPoints() {
		return signPoints;
	}
	/**
	 * 设置：累计任务积分
	 */
	public void setTaskPoints(Integer taskPoints) {
		this.taskPoints = taskPoints;
	}
	/**
	 * 获取：累计任务积分
	 */
	public Integer getTaskPoints() {
		return taskPoints;
	}
	/**
	 * 设置：消费积分
	 */
	public void setConsumePoints(Integer consumePoints) {
		this.consumePoints = consumePoints;
	}
	/**
	 * 获取：消费积分
	 */
	public Integer getConsumePoints() {
		return consumePoints;
	}
	/**
	 * 设置：当前总积分=累计签到积+累计任务积分-消费积分
	 */
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	/**
	 * 获取：当前总积分=累计签到积+累计任务积分-消费积分
	 */
	public Integer getTotalPoints() {
		return totalPoints;
	}
	/**
	 * 设置：连续签到累计天数
	 */
	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}
	/**
	 * 获取：连续签到累计天数
	 */
	public Integer getSignCount() {
		return signCount;
	}
	/**
	 * 设置：时间戳
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：时间戳
	 */
	public String getTimeStamp() {
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
	/**
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
}
