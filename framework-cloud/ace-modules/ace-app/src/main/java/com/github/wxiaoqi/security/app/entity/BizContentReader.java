package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 内容(产品)阅读数表
 * 
 * @author zxl
 * @Date 2018-12-13 09:56:49
 */
@Table(name = "biz_content_reader")
public class BizContentReader implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //内容(产品)id
    @Column(name = "content_id")
    private String contentId;
	
	    //查看数量：查看一次统计一次，不管是不是同一用户
    @Column(name = "view_num")
    private Integer viewNum;
	
	    //独立访问用户
    @Column(name = "user_num")
    private Integer userNum;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //时间戳
    @Column(name = "time_stamp")
    private Date timeStamp;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_ty")
    private String modifyTy;
	
	    //修改日期
    @Column(name = "modify_time")
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
	 * 设置：内容(产品)id
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	/**
	 * 获取：内容(产品)id
	 */
	public String getContentId() {
		return contentId;
	}
	/**
	 * 设置：查看数量：查看一次统计一次，不管是不是同一用户
	 */
	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}
	/**
	 * 获取：查看数量：查看一次统计一次，不管是不是同一用户
	 */
	public Integer getViewNum() {
		return viewNum;
	}
	/**
	 * 设置：独立访问用户
	 */
	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}
	/**
	 * 获取：独立访问用户
	 */
	public Integer getUserNum() {
		return userNum;
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
	public void setModifyTy(String modifyTy) {
		this.modifyTy = modifyTy;
	}
	/**
	 * 获取：修改人
	 */
	public String getModifyTy() {
		return modifyTy;
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
