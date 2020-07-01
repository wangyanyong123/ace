package com.github.wxiaoqi.security.schedulewo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 工单
 * 
 * @author zxl
 * @Date 2018-11-27 14:57:49
 */
@Table(name = "biz_wo")
public class BizWo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //工单编码
    @Column(name = "wo_code")
    private String woCode;
	
	    //工单类型(1-即时工单,2-计划性工单)
    @Column(name = "wo_type")
    private String woType;
	
	    //业务ID
    @Column(name = "bus_id")
    private String busId;
	
	    //业务名称
    @Column(name = "bus_name")
    private String busName;
	
	    //流程ID
    @Column(name = "flow_id")
    private String flowId;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //描述
    @Column(name = "description")
    private String description;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
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
	
	    //工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
    @Column(name = "come_from")
    private String comeFrom;
	
	    //当前处理类型(1-抢单 、2-人工处理)
    @Column(name = "deal_type")
    private String dealType;
	
	    //工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
    @Column(name = "wo_status")
    private String woStatus;
	
	    //期望服务时间
    @Column(name = "expected_service_time")
    private Date expectedServiceTime;
	
	    //当前工单工序
    @Column(name = "process_Id")
    private String processId;
	
	    //指定服务资源公司ID
    @Column(name = "company_id")
    private String companyId;
	
	    //当前处理人
    @Column(name = "handle_by")
    private String handleBy;
	
	    //首次接单人
    @Column(name = "accept_by")
    private String acceptBy;
	
	    //工单接单时间
    @Column(name = "receive_wo_time")
    private Date receiveWoTime;
	
	    //工单开始处理时间
    @Column(name = "start_process_time")
    private Date startProcessTime;
	
	    //工单完成时间
    @Column(name = "finish_wo_time")
    private Date finishWoTime;
	
	    //图片id,多张图片逗号分隔
    @Column(name = "img_id")
    private String imgId;
	
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
	 * 设置：工单类型(1-即时工单,2-计划性工单)
	 */
	public void setWoType(String woType) {
		this.woType = woType;
	}
	/**
	 * 获取：工单类型(1-即时工单,2-计划性工单)
	 */
	public String getWoType() {
		return woType;
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
	 * 设置：业务名称
	 */
	public void setBusName(String busName) {
		this.busName = busName;
	}
	/**
	 * 获取：业务名称
	 */
	public String getBusName() {
		return busName;
	}
	/**
	 * 设置：流程ID
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	/**
	 * 获取：流程ID
	 */
	public String getFlowId() {
		return flowId;
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
	 * 设置：当前处理类型(1-抢单 、2-人工处理)
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	/**
	 * 获取：当前处理类型(1-抢单 、2-人工处理)
	 */
	public String getDealType() {
		return dealType;
	}
	/**
	 * 设置：工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
	 */
	public void setWoStatus(String woStatus) {
		this.woStatus = woStatus;
	}
	/**
	 * 获取：工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
	 */
	public String getWoStatus() {
		return woStatus;
	}
	/**
	 * 设置：期望服务时间
	 */
	public void setExpectedServiceTime(Date expectedServiceTime) {
		this.expectedServiceTime = expectedServiceTime;
	}
	/**
	 * 获取：期望服务时间
	 */
	public Date getExpectedServiceTime() {
		return expectedServiceTime;
	}
	/**
	 * 设置：当前工单工序
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	/**
	 * 获取：当前工单工序
	 */
	public String getProcessId() {
		return processId;
	}
	/**
	 * 设置：指定服务资源公司ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：指定服务资源公司ID
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：当前处理人
	 */
	public void setHandleBy(String handleBy) {
		this.handleBy = handleBy;
	}
	/**
	 * 获取：当前处理人
	 */
	public String getHandleBy() {
		return handleBy;
	}
	/**
	 * 设置：首次接单人
	 */
	public void setAcceptBy(String acceptBy) {
		this.acceptBy = acceptBy;
	}
	/**
	 * 获取：首次接单人
	 */
	public String getAcceptBy() {
		return acceptBy;
	}
	/**
	 * 设置：工单接单时间
	 */
	public void setReceiveWoTime(Date receiveWoTime) {
		this.receiveWoTime = receiveWoTime;
	}
	/**
	 * 获取：工单接单时间
	 */
	public Date getReceiveWoTime() {
		return receiveWoTime;
	}
	/**
	 * 设置：工单开始处理时间
	 */
	public void setStartProcessTime(Date startProcessTime) {
		this.startProcessTime = startProcessTime;
	}
	/**
	 * 获取：工单开始处理时间
	 */
	public Date getStartProcessTime() {
		return startProcessTime;
	}
	/**
	 * 设置：工单完成时间
	 */
	public void setFinishWoTime(Date finishWoTime) {
		this.finishWoTime = finishWoTime;
	}
	/**
	 * 获取：工单完成时间
	 */
	public Date getFinishWoTime() {
		return finishWoTime;
	}
	/**
	 * 设置：图片id,多张图片逗号分隔
	 */
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	/**
	 * 获取：图片id,多张图片逗号分隔
	 */
	public String getImgId() {
		return imgId;
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
