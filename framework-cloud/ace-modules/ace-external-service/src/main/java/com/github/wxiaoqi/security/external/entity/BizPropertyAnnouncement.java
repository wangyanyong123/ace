package com.github.wxiaoqi.security.external.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 物业公告
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-26 13:57:07
 */
@Table(name = "biz_property_announcement")
public class BizPropertyAnnouncement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //公告类型（1 停水通知、2 停电通知、3 保洁养护、4 社区文化、5 绿化保养、6 消防演习、7 高温预警）字典中维护
    @Column(name = "announcement_type")
    private String announcementType;
	
	    //公告类型名称
    @Column(name = "announcement_name")
    private String announcementName;
	
	    //内容
    @Column(name = "content")
    private String content;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //图片
    @Column(name = "images")
    private String images;
	
	    //发布者
    @Column(name = "publisher")
    private String publisher;

	//发布时间
	@Column(name = "publisher_time")
    private Date publisherTime;
	
	    //重要程度（1：重要  2：一般）
    @Column(name = "important_degree")
    private String importantDegree;
	
	    //审核状态
    @Column(name = "audit_status")
    private String auditStatus;
	
	    //租户Id
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
	 * 设置：公告类型（1 停水通知、2 停电通知、3 保洁养护、4 社区文化、5 绿化保养、6 消防演习、7 高温预警）字典中维护
	 */
	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}
	/**
	 * 获取：公告类型（1 停水通知、2 停电通知、3 保洁养护、4 社区文化、5 绿化保养、6 消防演习、7 高温预警）字典中维护
	 */
	public String getAnnouncementType() {
		return announcementType;
	}
	/**
	 * 设置：公告类型名称
	 */
	public void setAnnouncementName(String announcementName) {
		this.announcementName = announcementName;
	}
	/**
	 * 获取：公告类型名称
	 */
	public String getAnnouncementName() {
		return announcementName;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
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
	 * 设置：图片
	 */
	public void setImages(String images) {
		this.images = images;
	}
	/**
	 * 获取：图片
	 */
	public String getImages() {
		return images;
	}
	/**
	 * 设置：发布者
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * 获取：发布者
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * 设置：重要程度（1：重要  2：一般）
	 */
	public void setImportantDegree(String importantDegree) {
		this.importantDegree = importantDegree;
	}
	/**
	 * 获取：重要程度（1：重要  2：一般）
	 */
	public String getImportantDegree() {
		return importantDegree;
	}
	/**
	 * 设置：审核状态
	 */
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取：审核状态
	 */
	public String getAuditStatus() {
		return auditStatus;
	}
	/**
	 * 设置：租户Id
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * 获取：租户Id
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

	public Date getPublisherTime() {
		return publisherTime;
	}

	public void setPublisherTime(Date publisherTime) {
		this.publisherTime = publisherTime;
	}
}
