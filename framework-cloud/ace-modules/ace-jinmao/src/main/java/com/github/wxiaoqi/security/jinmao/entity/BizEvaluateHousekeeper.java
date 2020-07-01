package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 管家评价表
 * 
 * @author huangxl
 * @Date 2019-02-19 16:55:19
 */
@Table(name = "biz_evaluate_housekeeper")
public class BizEvaluateHousekeeper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;

	@Column(name = "user_Name")
	private String userName;
	
	    //管家id
    @Column(name = "housekeeper_id")
    private String housekeeperId;
	
	    //评价内容
    @Column(name = "content")
    private String content;
	
	    //评价等级(1-不满意,2-一般,3-满意)
    @Column(name = "evaluate_type")
    private String evaluateType;
	
	    //评价理由(多个用,隔开)
    @Column(name = "evaluate_reason")
    private String evaluateReason;
	
	    //评价时间(yyyy-mm)
    @Column(name = "evaluate_date")
    private String evaluateDate;
	
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
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
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
	 * 设置：评价内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：评价内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：评价等级(1-不满意,2-一般,3-满意)
	 */
	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}
	/**
	 * 获取：评价等级(1-不满意,2-一般,3-满意)
	 */
	public String getEvaluateType() {
		return evaluateType;
	}
	/**
	 * 设置：评价理由(多个用,隔开)
	 */
	public void setEvaluateReason(String evaluateReason) {
		this.evaluateReason = evaluateReason;
	}
	/**
	 * 获取：评价理由(多个用,隔开)
	 */
	public String getEvaluateReason() {
		return evaluateReason;
	}
	/**
	 * 设置：评价时间(yyyy-mm)
	 */
	public void setEvaluateDate(String evaluateDate) {
		this.evaluateDate = evaluateDate;
	}
	/**
	 * 获取：评价时间(yyyy-mm)
	 */
	public String getEvaluateDate() {
		return evaluateDate;
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


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
