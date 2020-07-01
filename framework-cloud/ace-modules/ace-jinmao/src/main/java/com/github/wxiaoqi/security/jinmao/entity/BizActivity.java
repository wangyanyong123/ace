package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 小组活动表
 * 
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@Table(name = "biz_activity")
public class BizActivity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //活动ID
    @Id
    private String id;

	   //项目id
	@Column(name = "project_id")
	private String projectId;

	
	    //小组id
    @Column(name = "group_id")
    private String groupId;
	
	    //活动标题
    @Column(name = "title")
    private String title;
	
	    //活动封面图片
    @Column(name = "activit_ccover")
    private String activitCcover;
	
	    //活动内容(图文详情)
    @Column(name = "summary")
    private String summary;
	
	    //活动地点
    @Column(name = "address")
    private String address;
	
	    //开始时间
    @Column(name = "beg_Time")
    private Date begTime;
	
	    //结束时间
    @Column(name = "end_Time")
    private Date endTime;
	
	    //报名截止时间
    @Column(name = "apply_end_Time")
    private Date applyEndTime;
	
	    //是否收费(0-收费；1-免费)
    @Column(name = "is_free")
    private String isFree;
	
	    //活动费用(收费时不能为空)
    @Column(name = "act_cost")
    private BigDecimal actCost;
	
	    //是否可取消(0-不可以；1-可以。)
    @Column(name = "is_cancel")
    private String isCancel;

    //发布对象(1-小组,2-物业)
	@Column(name = "type")
    private String type;
	
	    //开始前可取消时间(单位:小时)
    @Column(name = "cancel_time")
    private Double cancelTime;
	
	    //活动联系人名字
    @Column(name = "contactor_name")
    private String contactorName;
	
	    //活动联系人手机号码
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //人数上限
    @Column(name = "person_Num")
    private Integer personNum;

	//启用状态(1-草稿，2-已发布，3-已撤回)
	@Column(name = "enable_status")
	private String enableStatus;
	
	    //状态
    @Column(name = "status")
    private String status;

	@Column(name = "size")
    private String size;

	@Column(name = "qr_url")
    private String qrUrl;
	
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
	 * 设置：活动ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：活动ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：小组id
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 获取：小组id
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * 设置：活动标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：活动标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：活动封面图片
	 */
	public void setActivitCcover(String activitCcover) {
		this.activitCcover = activitCcover;
	}
	/**
	 * 获取：活动封面图片
	 */
	public String getActivitCcover() {
		return activitCcover;
	}
	/**
	 * 设置：活动内容(图文详情)
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * 获取：活动内容(图文详情)
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * 设置：活动地点
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：活动地点
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：开始时间
	 */
	public void setBegTime(Date begTime) {
		this.begTime = begTime;
	}
	/**
	 * 获取：开始时间
	 */
	public Date getBegTime() {
		return begTime;
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
	 * 设置：报名截止时间
	 */
	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	/**
	 * 获取：报名截止时间
	 */
	public Date getApplyEndTime() {
		return applyEndTime;
	}
	/**
	 * 设置：是否收费(0-收费；1-免费)
	 */
	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}
	/**
	 * 获取：是否收费(0-收费；1-免费)
	 */
	public String getIsFree() {
		return isFree;
	}
	/**
	 * 设置：活动费用(收费时不能为空)
	 */
	public void setActCost(BigDecimal actCost) {
		this.actCost = actCost;
	}
	/**
	 * 获取：活动费用(收费时不能为空)
	 */
	public BigDecimal getActCost() {
		return actCost;
	}
	/**
	 * 设置：是否可取消(0-不可以；1-可以。)
	 */
	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}
	/**
	 * 获取：是否可取消(0-不可以；1-可以。)
	 */
	public String getIsCancel() {
		return isCancel;
	}
	/**
	 * 设置：开始前可取消时间(单位:小时)
	 */
	public void setCancelTime(Double cancelTime) {
		this.cancelTime = cancelTime;
	}
	/**
	 * 获取：开始前可取消时间(单位:小时)
	 */
	public Double getCancelTime() {
		return cancelTime;
	}
	/**
	 * 设置：活动联系人名字
	 */
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	/**
	 * 获取：活动联系人名字
	 */
	public String getContactorName() {
		return contactorName;
	}
	/**
	 * 设置：活动联系人手机号码
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 获取：活动联系人手机号码
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * 设置：人数上限
	 */
	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}
	/**
	 * 获取：人数上限
	 */
	public Integer getPersonNum() {
		return personNum;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}
}
