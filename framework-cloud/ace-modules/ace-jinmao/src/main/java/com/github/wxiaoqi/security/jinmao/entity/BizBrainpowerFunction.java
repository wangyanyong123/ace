package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 智能客服功能点表
 * 
 * @author huangxl
 * @Date 2019-04-10 18:24:34
 */
@Table(name = "biz_brainpower_function")
public class BizBrainpowerFunction implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //功能编码
    @Column(name = "code")
    private String code;
	
	    //功能点名称
    @Column(name = "function_point")
    private String functionPoint;

	//跳转链接编码
	@Column(name = "jump_code")
	private String jumpCode;
	
	    //跳转链接
    @Column(name = "jump_link")
    private String jumpLink;
	
	    //功能点内容
    @Column(name = "description")
    private String description;
	
	    //图片
    @Column(name = "picture")
    private String picture;
	
	    //是否置底显示(0-未置底,1-置底)
    @Column(name = "is_show")
    private String isShow;

	@Column(name = "view_sort")
    private int viewSort;
	
	    //启用状态(1-草稿，2-已发布，3-已撤回)
    @Column(name = "enable_status")
    private String enableStatus;
	
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
	 * 设置：功能编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：功能编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：功能点名称
	 */
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}
	/**
	 * 获取：功能点名称
	 */
	public String getFunctionPoint() {
		return functionPoint;
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
	 * 设置：功能点内容
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：功能点内容
	 */
	public String getDescription() {
		return description;
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
	 * 设置：是否置底显示(0-未置底,1-置底)
	 */
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	/**
	 * 获取：是否置底显示(0-未置底,1-置底)
	 */
	public String getIsShow() {
		return isShow;
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

	public int getViewSort() {
		return viewSort;
	}

	public void setViewSort(int viewSort) {
		this.viewSort = viewSort;
	}
}
