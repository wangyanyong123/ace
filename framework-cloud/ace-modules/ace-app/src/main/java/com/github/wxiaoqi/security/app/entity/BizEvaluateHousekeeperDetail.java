package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 管家评价详情表
 * 
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
@Table(name = "biz_evaluate_housekeeper_detail")
public class BizEvaluateHousekeeperDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //管家id
    @Column(name = "housekeeper_id")
    private String housekeeperId;
	
	    //评价数
    @Column(name = "evaluate_Num")
    private Integer evaluateNum;
	
	    //满意度
    @Column(name = "satisfaction")
    private String satisfaction;
	
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
	 * 设置：管家id
	 */
	public void setHousekeeperId(String housekeeperId) {
		this.housekeeperId = housekeeperId;
	}
	/**
	 * 获取：管家id
	 */
	public String getHousekeeperId() {
		return housekeeperId;
	}
	/**
	 * 设置：评价数
	 */
	public void setEvaluateNum(Integer evaluateNum) {
		this.evaluateNum = evaluateNum;
	}
	/**
	 * 获取：评价数
	 */
	public Integer getEvaluateNum() {
		return evaluateNum;
	}
	/**
	 * 设置：满意度
	 */
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	/**
	 * 获取：满意度
	 */
	public String getSatisfaction() {
		return satisfaction;
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
