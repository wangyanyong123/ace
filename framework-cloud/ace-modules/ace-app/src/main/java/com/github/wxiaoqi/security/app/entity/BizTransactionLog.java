package com.github.wxiaoqi.security.app.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 订单工单流水日志表
 * 
 * @author huangxl
 * @Date 2018-12-21 15:09:34
 */
@Table(name = "biz_transaction_log")
public class BizTransactionLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //工单ID
    @Column(name = "wo_id")
    private String woId;
	
	    //当前节点
    @Column(name = "curr_step")
    private String currStep;
	
	    //当前节点描述,Json 格式
    @Column(name = "description")
    private String description;
	
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
	
	    //图片id,多张图片逗号分隔
    @Column(name = "img_id")
    private String imgId;
	
	    //联系人姓名
    @Column(name = "con_name")
    private String conName;
	
	    //联系人电话
    @Column(name = "con_tel")
    private String conTel;

	//评价分值（0~5分）
	@Column(name = "appraisal_val")
	private int appraisalVal;

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
	 * 设置：工单ID
	 */
	public void setWoId(String woId) {
		this.woId = woId;
	}
	/**
	 * 获取：工单ID
	 */
	public String getWoId() {
		return woId;
	}
	/**
	 * 设置：当前节点
	 */
	public void setCurrStep(String currStep) {
		this.currStep = currStep;
	}
	/**
	 * 获取：当前节点
	 */
	public String getCurrStep() {
		return currStep;
	}
	/**
	 * 设置：当前节点描述,Json 格式
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：当前节点描述,Json 格式
	 */
	public String getDescription() {
		return description;
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
	/**
	 * 设置：图片id,多张图片逗号分隔
	 */
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	/**
	 * 获取：图片id,多张图片逗号分隔
	 */
	public String getImgId() {
		return imgId;
	}
	/**
	 * 设置：联系人姓名
	 */
	public void setConName(String conName) {
		this.conName = conName;
	}
	/**
	 * 获取：联系人姓名
	 */
	public String getConName() {
		return conName;
	}
	/**
	 * 设置：联系人电话
	 */
	public void setConTel(String conTel) {
		this.conTel = conTel;
	}
	/**
	 * 获取：联系人电话
	 */
	public String getConTel() {
		return conTel;
	}

	public int getAppraisalVal() {
		return appraisalVal;
	}

	public void setAppraisalVal(int appraisalVal) {
		this.appraisalVal = appraisalVal;
	}
}
