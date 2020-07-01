package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * app广告位表
 * 
 * @author huangxl
 * @Date 2019-05-27 09:55:49
 */
@Table(name = "sys_homepage_ad")
public class SysHomepageAd implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //跳转业务:1-无跳转
    @Column(name = "business_Type")
    private String businessType;
	
	    //安卓图片
    @Column(name = "android_Img")
    private String androidImg;
	
	    //ios图片
    @Column(name = "ios_Img")
    private String iosImg;
	
	    //开始时间
    @Column(name = "begin_Time")
    private Date beginTime;
	
	    //结束时间
    @Column(name = "end_Time")
    private Date endTime;
	
	    //停留时间，单位(秒)
    @Column(name = "stop_Time")
    private Integer stopTime;
	
	    //是否发布 1:发布，2:未发布
    @Column(name = "is_Publish")
    private String isPublish;
	
	    //排序
    @Column(name = "sort")
    private Integer sort;
	
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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：跳转业务:1-无跳转
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * 获取：跳转业务:1-无跳转
	 */
	public String getBusinessType() {
		return businessType;
	}
	/**
	 * 设置：安卓图片
	 */
	public void setAndroidImg(String androidImg) {
		this.androidImg = androidImg;
	}
	/**
	 * 获取：安卓图片
	 */
	public String getAndroidImg() {
		return androidImg;
	}
	/**
	 * 设置：ios图片
	 */
	public void setIosImg(String iosImg) {
		this.iosImg = iosImg;
	}
	/**
	 * 获取：ios图片
	 */
	public String getIosImg() {
		return iosImg;
	}
	/**
	 * 设置：开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：停留时间，单位(秒)
	 */
	public void setStopTime(Integer stopTime) {
		this.stopTime = stopTime;
	}
	/**
	 * 获取：停留时间，单位(秒)
	 */
	public Integer getStopTime() {
		return stopTime;
	}
	/**
	 * 设置：是否发布 1:发布，2:未发布
	 */
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}
	/**
	 * 获取：是否发布 1:发布，2:未发布
	 */
	public String getIsPublish() {
		return isPublish;
	}
	/**
	 * 设置：排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSort() {
		return sort;
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
