package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 装修监理表
 * 
 * @Date 2019-04-01 15:47:05
 */
@Table(name = "biz_decorete_supervise")
public class BizDecoreteSupervise implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //服务价格(元)
    @Column(name = "service_price")
    private String servicePrice;
	
	    //原价(元)
    @Column(name = "cost_price")
    private String costPrice;
	
	    //推广图片
    @Column(name = "promo_imge")
    private String promoImge;
	
	    //服务介绍
    @Column(name = "service_intro")
    private String serviceIntro;
	
	    //发布状态(0-草稿1-未发布2-已发布)
    @Column(name = "publish_status")
    private String publishStatus;
	
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
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：项目ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目ID
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：服务价格(元)
	 */
	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}
	/**
	 * 获取：服务价格(元)
	 */
	public String getServicePrice() {
		return servicePrice;
	}
	/**
	 * 设置：原价(元)
	 */
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	/**
	 * 获取：原价(元)
	 */
	public String getCostPrice() {
		return costPrice;
	}
	/**
	 * 设置：推广图片
	 */
	public void setPromoImge(String promoImge) {
		this.promoImge = promoImge;
	}
	/**
	 * 获取：推广图片
	 */
	public String getPromoImge() {
		return promoImge;
	}
	/**
	 * 设置：服务介绍
	 */
	public void setServiceIntro(String serviceIntro) {
		this.serviceIntro = serviceIntro;
	}
	/**
	 * 获取：服务介绍
	 */
	public String getServiceIntro() {
		return serviceIntro;
	}
	/**
	 * 设置：发布状态(0-草稿1-未发布2-已发布)
	 */
	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}
	/**
	 * 获取：发布状态(0-草稿1-未发布2-已发布)
	 */
	public String getPublishStatus() {
		return publishStatus;
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
