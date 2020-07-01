package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 家里人广告位
 * 
 * @author huangxl
 * @Date 2019-08-12 17:59:01
 */
@Table(name = "biz_family_advertising")
public class BizFamilyAdvertising implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //广告图片
    @Column(name = "advertising_img")
    private String advertisingImg;
	
	    //内链业务分类(1-家里人,2-议事厅,3-邻里活动,4-社区话题,5-业主圈帖子)
    @Column(name = "bus_classify")
    private String busClassify;
	
	    //跳转业务(0-无,1-app内部,2-外部URL跳转)
    @Column(name = "skip_bus")
    private String skipBus;
	
	    //业务对象
    @Column(name = "object_id")
    private String objectId;
	
	    //跳转地址
    @Column(name = "skip_url")
    private String skipUrl;
	
	    //广告排序
    @Column(name = "view_sort")
    private Integer viewSort;
	
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
	 * 设置：广告图片
	 */
	public void setAdvertisingImg(String advertisingImg) {
		this.advertisingImg = advertisingImg;
	}
	/**
	 * 获取：广告图片
	 */
	public String getAdvertisingImg() {
		return advertisingImg;
	}
	/**
	 * 设置：内链业务分类(1-家里人,2-议事厅,3-邻里活动,4-社区话题,5-业主圈帖子)
	 */
	public void setBusClassify(String busClassify) {
		this.busClassify = busClassify;
	}
	/**
	 * 获取：内链业务分类(1-家里人,2-议事厅,3-邻里活动,4-社区话题,5-业主圈帖子)
	 */
	public String getBusClassify() {
		return busClassify;
	}
	/**
	 * 设置：跳转业务(0-无,1-app内部,2-外部URL跳转)
	 */
	public void setSkipBus(String skipBus) {
		this.skipBus = skipBus;
	}
	/**
	 * 获取：跳转业务(0-无,1-app内部,2-外部URL跳转)
	 */
	public String getSkipBus() {
		return skipBus;
	}
	/**
	 * 设置：业务对象
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	/**
	 * 获取：业务对象
	 */
	public String getObjectId() {
		return objectId;
	}
	/**
	 * 设置：跳转地址
	 */
	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}
	/**
	 * 获取：跳转地址
	 */
	public String getSkipUrl() {
		return skipUrl;
	}
	/**
	 * 设置：广告排序
	 */
	public void setViewSort(Integer viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：广告排序
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
