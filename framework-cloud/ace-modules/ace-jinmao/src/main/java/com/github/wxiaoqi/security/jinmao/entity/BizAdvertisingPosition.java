package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 优选商城广告位
 * 
 * @author zxl
 * @Date 2018-12-17 15:07:24
 */
@Table(name = "biz_advertising_position")
public class BizAdvertisingPosition implements Serializable {
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

		//跳转业务
	@Column(name = "skip_bus")
	private String skipBus;

		//跳转地址
	@Column(name = "skip_url")
	private String skipUrl;

	@Column(name = "bus_classify")
	private String busClassify;

	@Column(name = "bus_id")
	private String busId;

	    //排序
    @Column(name = "view_sort")
    private Integer viewSort;
	
	    //租户id
    @Column(name = "tenant_id")
    private String tenantId;
	
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

    @Column(name = "product_id")
	private String productId;

	//位置
	@Column(name = "position")
	private Integer position;
	

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

	public String getSkipBus() {
		return skipBus;
	}

	public void setSkipBus(String skipBus) {
		this.skipBus = skipBus;
	}

	public String getSkipUrl() {
		return skipUrl;
	}

	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}

	/**
	 * 设置：排序
	 */
	public void setViewSort(Integer viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getViewSort() {
		return viewSort;
	}
	/**
	 * 设置：租户id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户id
	 */
	public String getTenantId() {
		return tenantId;
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

	public String getBusClassify() {
		return busClassify;
	}

	public void setBusClassify(String busClassify) {
		this.busClassify = busClassify;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
