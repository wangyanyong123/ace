package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author huangxl
 * @Date 2020-04-14 19:34:50
 */
@Table(name = "biz_hot_home_service")
public class BizHotHomeService implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "title")
    private String title;
	
	    //图片地址
    @Column(name = "img_url")
    private String imgUrl;

	    //展示位置 1：首页，....
    @Column(name = "position")
    private Integer position;
	
	    //展示顺序
    @Column(name = "sort_num")
    private Integer sortNum;
	
	    //租户ID
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //业务ID（预约服务ID）
    @Column(name = "bus_id")
    private String busId;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
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
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：图片地址
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取：图片地址
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * 设置：展示位置 1：首页，....
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	/**
	 * 获取：展示位置 1：首页，....
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * 设置：展示顺序
	 */
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	/**
	 * 获取：展示顺序
	 */
	public Integer getSortNum() {
		return sortNum;
	}
	/**
	 * 设置：租户ID
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户ID
	 */
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * 设置：业务ID（预约服务ID）
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}
	/**
	 * 获取：业务ID（预约服务ID）
	 */
	public String getBusId() {
		return busId;
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
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * 获取：
	 */
	public String getModifyBy() {
		return modifyBy;
	}
	/**
	 * 设置：
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：
	 */
	public Date getModifyTime() {
		return modifyTime;
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
