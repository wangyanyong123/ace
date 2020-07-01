package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 活动二维码表
 * 
 * @author huangxl
 * @Date 2019-04-17 16:26:43
 */
@Table(name = "biz_activity_qr")
public class BizActivityQr implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //活动ID
    @Column(name = "activity_Id")
    private String activityId;
	
	    //二维码尺寸(1-80*80,2-120*120,3-150*150,4-300*300,5-500*500)
    @Column(name = "size")
    private String size;
	
	    //二维码路径
    @Column(name = "qr_url")
    private String qrUrl;
	
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
	 * 设置：活动ID
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	/**
	 * 获取：活动ID
	 */
	public String getActivityId() {
		return activityId;
	}
	/**
	 * 设置：二维码尺寸(1-80*80,2-120*120,3-150*150,4-300*300,5-500*500)
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * 获取：二维码尺寸(1-80*80,2-120*120,3-150*150,4-300*300,5-500*500)
	 */
	public String getSize() {
		return size;
	}
	/**
	 * 设置：二维码路径
	 */
	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}
	/**
	 * 获取：二维码路径
	 */
	public String getQrUrl() {
		return qrUrl;
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
