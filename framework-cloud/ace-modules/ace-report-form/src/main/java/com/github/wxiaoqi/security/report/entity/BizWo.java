package com.github.wxiaoqi.security.report.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 工单表
 * 
 * @author huangxl
 * @Date 2018-12-21 15:09:34
 */
@Table(name = "biz_wo")
public class BizWo implements Serializable {
	private static final long serialVersionUID = -2483345086335594835L;
	
	    //ID
    @Id
    private String id;
	
	    //工单编码
    @Column(name = "wo_code")
    private String woCode;

	//crm工单编码
	@Column(name = "crm_wo_code")
	private String crmWoCode;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //描述
    @Column(name = "description")
    private String description;

	//描述
	@Column(name = "incident_type")
	private String incidentType;

	@Column(name = "comment_status")
	private String commentStatus;

	    //项目ID
    @Column(name = "project_id")
    private String projectId;

	//CRM项目编码
	@Column(name = "crm_project_code")
	private String crmProjectCode;
	
	    //地块ID
    @Column(name = "land_id")
    private String landId;
	
	    //楼栋id
    @Column(name = "build_id")
    private String buildId;
	
	    //楼栋id
    @Column(name = "unit_id")
    private String unitId;
	
	    //房屋id
    @Column(name = "room_id")
    private String roomId;

	//CRM房屋编码
	@Column(name = "crm_room_code")
	private String crmRoomCode;
	
	    //地址
    @Column(name = "addr")
    private String addr;
	
	    //一级分类编码
    @Column(name = "one_category_code")
    private String oneCategoryCode;
	
	    //一级分类名称
    @Column(name = "one_category_name")
    private String oneCategoryName;
	
	    //二级分类编码
    @Column(name = "two_category_code")
    private String twoCategoryCode;
	
	    //二级分类名称
    @Column(name = "two_category_name")
    private String twoCategoryName;
	
	    //三级分类编码
    @Column(name = "three_category_code")
    private String threeCategoryCode;
	
	    //三级分类名称
    @Column(name = "three_category_name")
    private String threeCategoryName;
	
	    //联系人用户Id
    @Column(name = "contact_user_id")
    private String contactUserId;
	
	    //联系人名称
    @Column(name = "contact_name")
    private String contactName;
	
	    //联系人电话
    @Column(name = "contact_tel")
    private String contactTel;
	
	    //发布人用户Id
    @Column(name = "publish_user_id")
    private String publishUserId;
	
	    //发布人名称
    @Column(name = "publish_name")
    private String publishName;
	
	    //发布人电话
    @Column(name = "publish_tel")
    private String publishTel;
	
	    //工单处理渠道(1-默认服务端APP)
    @Column(name = "handle_channel")
    private String handleChannel;

	//是否同步到CRM系统(0-未同步,1-已同步,2-同步失败)
	@Column(name = "crm_sync_flag")
	private String crmSyncFlag;
	
	    //工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
    @Column(name = "come_from")
    private String comeFrom;
	
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

	@Column(name = "valet")
    private String valet;

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
	 * 设置：工单编码
	 */
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	/**
	 * 获取：工单编码
	 */
	public String getWoCode() {
		return woCode;
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
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
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
	 * 设置：地块ID
	 */
	public void setLandId(String landId) {
		this.landId = landId;
	}
	/**
	 * 获取：地块ID
	 */
	public String getLandId() {
		return landId;
	}
	/**
	 * 设置：楼栋id
	 */
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}
	/**
	 * 获取：楼栋id
	 */
	public String getBuildId() {
		return buildId;
	}
	/**
	 * 设置：楼栋id
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	/**
	 * 获取：楼栋id
	 */
	public String getUnitId() {
		return unitId;
	}
	/**
	 * 设置：房屋id
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	/**
	 * 获取：房屋id
	 */
	public String getRoomId() {
		return roomId;
	}
	/**
	 * 设置：地址
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * 获取：地址
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * 设置：一级分类编码
	 */
	public void setOneCategoryCode(String oneCategoryCode) {
		this.oneCategoryCode = oneCategoryCode;
	}
	/**
	 * 获取：一级分类编码
	 */
	public String getOneCategoryCode() {
		return oneCategoryCode;
	}
	/**
	 * 设置：一级分类名称
	 */
	public void setOneCategoryName(String oneCategoryName) {
		this.oneCategoryName = oneCategoryName;
	}
	/**
	 * 获取：一级分类名称
	 */
	public String getOneCategoryName() {
		return oneCategoryName;
	}
	/**
	 * 设置：二级分类编码
	 */
	public void setTwoCategoryCode(String twoCategoryCode) {
		this.twoCategoryCode = twoCategoryCode;
	}
	/**
	 * 获取：二级分类编码
	 */
	public String getTwoCategoryCode() {
		return twoCategoryCode;
	}
	/**
	 * 设置：二级分类名称
	 */
	public void setTwoCategoryName(String twoCategoryName) {
		this.twoCategoryName = twoCategoryName;
	}
	/**
	 * 获取：二级分类名称
	 */
	public String getTwoCategoryName() {
		return twoCategoryName;
	}
	/**
	 * 设置：三级分类编码
	 */
	public void setThreeCategoryCode(String threeCategoryCode) {
		this.threeCategoryCode = threeCategoryCode;
	}
	/**
	 * 获取：三级分类编码
	 */
	public String getThreeCategoryCode() {
		return threeCategoryCode;
	}
	/**
	 * 设置：三级分类名称
	 */
	public void setThreeCategoryName(String threeCategoryName) {
		this.threeCategoryName = threeCategoryName;
	}
	/**
	 * 获取：三级分类名称
	 */
	public String getThreeCategoryName() {
		return threeCategoryName;
	}
	/**
	 * 设置：联系人用户Id
	 */
	public void setContactUserId(String contactUserId) {
		this.contactUserId = contactUserId;
	}
	/**
	 * 获取：联系人用户Id
	 */
	public String getContactUserId() {
		return contactUserId;
	}
	/**
	 * 设置：联系人名称
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * 获取：联系人名称
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * 设置：联系人电话
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 获取：联系人电话
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * 设置：发布人用户Id
	 */
	public void setPublishUserId(String publishUserId) {
		this.publishUserId = publishUserId;
	}
	/**
	 * 获取：发布人用户Id
	 */
	public String getPublishUserId() {
		return publishUserId;
	}
	/**
	 * 设置：发布人名称
	 */
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	/**
	 * 获取：发布人名称
	 */
	public String getPublishName() {
		return publishName;
	}
	/**
	 * 设置：发布人电话
	 */
	public void setPublishTel(String publishTel) {
		this.publishTel = publishTel;
	}
	/**
	 * 获取：发布人电话
	 */
	public String getPublishTel() {
		return publishTel;
	}
	/**
	 * 设置：工单处理渠道(1-默认服务端APP)
	 */
	public void setHandleChannel(String handleChannel) {
		this.handleChannel = handleChannel;
	}
	/**
	 * 获取：工单处理渠道(1-默认服务端APP)
	 */
	public String getHandleChannel() {
		return handleChannel;
	}
	/**
	 * 设置：工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
	 */
	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}
	/**
	 * 获取：工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
	 */
	public String getComeFrom() {
		return comeFrom;
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

	public String getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}

	public String getCrmProjectCode() {
		return crmProjectCode;
	}

	public void setCrmProjectCode(String crmProjectCode) {
		this.crmProjectCode = crmProjectCode;
	}

	public String getCrmRoomCode() {
		return crmRoomCode;
	}

	public void setCrmRoomCode(String crmRoomCode) {
		this.crmRoomCode = crmRoomCode;
	}

	public String getCrmWoCode() {
		return crmWoCode;
	}

	public void setCrmWoCode(String crmWoCode) {
		this.crmWoCode = crmWoCode;
	}

	public String getCrmSyncFlag() {
		return crmSyncFlag;
	}

	public void setCrmSyncFlag(String crmSyncFlag) {
		this.crmSyncFlag = crmSyncFlag;
	}

	public String getValet() {
		return valet;
	}

	public void setValet(String valet) {
		this.valet = valet;
	}

	public String getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}
}
