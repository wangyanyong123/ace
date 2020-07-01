package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 业务商品分类表
 * 
 * @author zxl
 * @Date 2018-12-10 16:29:40
 */
@Table(name = "biz_business_classify")
public class BizBusinessClassify implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //业务ID
    @Column(name = "bus_id")
    private String busId;
	
	    //分类编码
    @Column(name = "classify_code")
    private String classifyCode;
	
	    //分类名称
    @Column(name = "classify_name")
    private String classifyName;
	
	    //分类状态(0：未启用  1：启用）
    @Column(name = "bus_status")
    private String busStatus;
	
	    //序号（隔5设置，1，5，10。。。）
    @Column(name = "view_sort")
    private Integer viewSort;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //时间戳
    @Column(name = "time_stamp")
    private Date timeStamp;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
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
	 * 设置：业务ID
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}
	/**
	 * 获取：业务ID
	 */
	public String getBusId() {
		return busId;
	}
	/**
	 * 设置：分类编码
	 */
	public void setClassifyCode(String classifyCode) {
		this.classifyCode = classifyCode;
	}
	/**
	 * 获取：分类编码
	 */
	public String getClassifyCode() {
		return classifyCode;
	}
	/**
	 * 设置：分类名称
	 */
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	/**
	 * 获取：分类名称
	 */
	public String getClassifyName() {
		return classifyName;
	}
	/**
	 * 设置：分类状态(0：未启用  1：启用）
	 */
	public void setBusStatus(String busStatus) {
		this.busStatus = busStatus;
	}
	/**
	 * 获取：分类状态(0：未启用  1：启用）
	 */
	public String getBusStatus() {
		return busStatus;
	}
	/**
	 * 设置：序号（隔5设置，1，5，10。。。）
	 */
	public void setViewSort(Integer viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：序号（隔5设置，1，5，10。。。）
	 */
	public Integer getViewSort() {
		return viewSort;
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
