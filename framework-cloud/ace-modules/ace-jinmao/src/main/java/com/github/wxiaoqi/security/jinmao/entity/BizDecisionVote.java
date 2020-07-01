package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 决策投票表
 * 
 * @author guohao
 * @Date 2020-06-04 13:33:20
 */
@Table(name = "biz_decision_vote")
public class BizDecisionVote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "project_id")
    private String projectId;
	
	    //
    @Column(name = "decision_id")
    private String decisionId;
	
	    //
    @Column(name = "user_id")
    private String userId;
	
	    //
    @Column(name = "house_id")
    private String houseId;
	
	    //
    @Column(name = "house_name")
    private String houseName;
	
	    //房屋认证类型  1、家属；2、租客；3、业主
    @Column(name = "identity_type")
    private String identityType;
	
	    //进度占比
    @Column(name = "progress_rate")
    private BigDecimal progressRate;

	//投票状态 0:不同意 1：同意
	@Column(name = "vote_status")
	private Integer voteStatus;
	
	    //
    @Column(name = "remark")
    private String remark;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
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
	 * 设置：
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：
	 */
	public void setDecisionId(String decisionId) {
		this.decisionId = decisionId;
	}
	/**
	 * 获取：
	 */
	public String getDecisionId() {
		return decisionId;
	}
	/**
	 * 设置：
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	/**
	 * 获取：
	 */
	public String getHouseId() {
		return houseId;
	}
	/**
	 * 设置：
	 */
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	/**
	 * 获取：
	 */
	public String getHouseName() {
		return houseName;
	}
	/**
	 * 设置：房屋认证类型  1、家属；2、租客；3、业主
	 */
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	/**
	 * 获取：房屋认证类型  1、家属；2、租客；3、业主
	 */
	public String getIdentityType() {
		return identityType;
	}
	/**
	 * 设置：进度占比
	 */
	public void setProgressRate(BigDecimal progressRate) {
		this.progressRate = progressRate;
	}
	/**
	 * 获取：进度占比
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
