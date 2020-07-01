package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 智能客服问答表
 * 
 * @author huangxl
 * @Date 2019-04-10 18:24:34
 */
@Table(name = "biz_brainpower_question")
public class BizBrainpowerQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //功能点id
    @Column(name = "function_id")
    private String functionId;
	
	    //问题
    @Column(name = "question")
    private String question;

	//跳转链接编码
	@Column(name = "jump_code")
	private String jumpCode;
	
	    //跳转链接
    @Column(name = "jump_link")
    private String jumpLink;

	
	    //答案
    @Column(name = "answer")
    private String answer;
	
	    //图片
    @Column(name = "picture")
    private String picture;
	
	    //启用状态(1-草稿，2-已发布，3-已撤回)
    @Column(name = "enable_status")
    private String enableStatus;
	
	    //解决数
    @Column(name = "solve_number")
    private String solveNumber;
	
	    //未解决数
    @Column(name = "unsolve_number")
    private String unsolveNumber;
	
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
	 * 设置：功能点id
	 */
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	/**
	 * 获取：功能点id
	 */
	public String getFunctionId() {
		return functionId;
	}
	/**
	 * 设置：问题
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * 获取：问题
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * 设置：跳转链接
	 */
	public void setJumpLink(String jumpLink) {
		this.jumpLink = jumpLink;
	}
	/**
	 * 获取：跳转链接
	 */
	public String getJumpLink() {
		return jumpLink;
	}

	/**
	 * 设置：答案
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * 获取：答案
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * 设置：图片
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * 获取：图片
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * 设置：启用状态(1-草稿，2-已发布，3-已撤回)
	 */
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	/**
	 * 获取：启用状态(1-草稿，2-已发布，3-已撤回)
	 */
	public String getEnableStatus() {
		return enableStatus;
	}
	/**
	 * 设置：解决数
	 */
	public void setSolveNumber(String solveNumber) {
		this.solveNumber = solveNumber;
	}
	/**
	 * 获取：解决数
	 */
	public String getSolveNumber() {
		return solveNumber;
	}
	/**
	 * 设置：未解决数
	 */
	public void setUnsolveNumber(String unsolveNumber) {
		this.unsolveNumber = unsolveNumber;
	}
	/**
	 * 获取：未解决数
	 */
	public String getUnsolveNumber() {
		return unsolveNumber;
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

	public String getJumpCode() {
		return jumpCode;
	}

	public void setJumpCode(String jumpCode) {
		this.jumpCode = jumpCode;
	}
}
