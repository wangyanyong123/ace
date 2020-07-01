package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 订单工单表
 * 
 * @author huangxl
 * @Date 2018-12-21 15:09:34
 */
@Table(name = "biz_subscribe_wo")
public class BizSubscribeWo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //订单工单ID
    @Id
    private String id;

	    //订单/工单编码
    @Column(name = "code")
    private String code;

	//订单/工单编码
	@Column(name = "vrobot_wo_id")
	private String vrobotWoId;
	
	    //工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)
    @Column(name = "wo_type")
    private String woType;
	
	    //生成类型(1-只生成工单,2-只生成订单,3-先生成订单后生成工单,4-先生成工单后生成订单)
    @Column(name = "create_type")
    private String createType;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //业务ID
    @Column(name = "bus_id")
    private String busId;
	
	    //业务名称
    @Column(name = "bus_name")
    private String busName;
	
	    //流程ID
    @Column(name = "flow_id")
    private String flowId;
	
	    //当前订单工序
    @Column(name = "process_id")
    private String processId;
	
	    //商户ID
    @Column(name = "company_Id")
    private String companyId;
	
	    //订单状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成）
    @Column(name = "subscribe_status")
    private String subscribeStatus;
	
	    //工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
    @Column(name = "wo_status")
    private String woStatus;
	
	    //评论状态(0-不可评论、1-待评论、2-已评论、3-关闭)
    @Column(name = "comment_status")
    private String commentStatus;

	//团购状态（0-非团购，1-团购中，2-已成团)
	@Column(name = "group_status")
	private String groupStatus;
	
	    //期望服务时间
    @Column(name = "expected_service_time")
    private Date expectedServiceTime;
	
	    //处理类型(1-派单,2-抢单)
    @Column(name = "deal_type")
    private String dealType;
	
	    //工单当前处理人
    @Column(name = "handle_by")
    private String handleBy;
	
	    //工单上一次处理人
    @Column(name = "last_handle_by")
    private String lastHandleBy;
	
	    //工单指派人员
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

    @Column(name = "stop_wo_time")
	private Date stopWoTime;

	@Column(name = "restart_wo_time")
	private Date restartWoTime;

	//到岗时间
	@Column(name = "ARRIVE_WO_TIME")
	private Date arriveWoTime;

	//确认到货时间
	@Column(name = "Review_Wo_Time")
	private Date reviewWoTime;

	//是否准时到岗(0-未评价，1-是，2-否)
	@Column(name = "IS_ARRIVE_ONTIME")
	private String isArriveOntime;

	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //地块ID
    @Column(name = "land_id")
    private String landId;
	
	    //楼栋id
    @Column(name = "build_id")
    private String buildId;
	
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
	
	    //创建日期(下单日期)
    @Column(name = "create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_Time")
    private Date modifyTime;


	/**
	 * 设置：订单工单ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：订单工单ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：订单/工单编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：订单/工单编码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)
	 */
	public void setWoType(String woType) {
		this.woType = woType;
	}
	/**
	 * 获取：工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)
	 */
	public String getWoType() {
		return woType;
	}
	/**
	 * 设置：生成类型(1-只生成工单,2-只生成订单,3-先生成订单后生成工单,4-先生成工单后生成订单)
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	/**
	 * 获取：生成类型(1-只生成工单,2-只生成订单,3-先生成订单后生成工单,4-先生成工单后生成订单)
	 */
	public String getCreateType() {
		return createType;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public String getUserId() {
		return userId;
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
	 * 设置：当前订单工序
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	/**
	 * 获取：当前订单工序
	 */
	public String getProcessId() {
		return processId;
	}
	/**
	 * 设置：商户ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：商户ID
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：订单状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成）
	 */
	public void setSubscribeStatus(String subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}
	/**
	 * 获取：订单状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成）
	 */
	public String getSubscribeStatus() {
		return subscribeStatus;
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
	 * 设置：评论状态(0-不可评论、1-待评论、2-已评论、3-关闭)
	 */
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}
	/**
	 * 获取：评论状态(0-不可评论、1-待评论、2-已评论、3-关闭)
	 */
	public String getCommentStatus() {
		return commentStatus;
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
	 * 设置：处理类型(1-派单,2-抢单)
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	/**
	 * 获取：处理类型(1-派单,2-抢单)
	 */
	public String getDealType() {
		return dealType;
	}
	/**
	 * 设置：工单当前处理人
	 */
	public void setHandleBy(String handleBy) {
		this.handleBy = handleBy;
	}
	/**
	 * 获取：工单当前处理人
	 */
	public String getHandleBy() {
		return handleBy;
	}
	/**
	 * 设置：工单上一次处理人
	 */
	public void setLastHandleBy(String lastHandleBy) {
		this.lastHandleBy = lastHandleBy;
	}
	/**
	 * 获取：工单上一次处理人
	 */
	public String getLastHandleBy() {
		return lastHandleBy;
	}
	/**
	 * 设置：工单指派人员
	 */
	public void setAcceptBy(String acceptBy) {
		this.acceptBy = acceptBy;
	}
	/**
	 * 获取：工单指派人员
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
	 * 设置：创建日期(下单日期)
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建日期(下单日期)
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

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public Date getStopWoTime() {
		return stopWoTime;
	}

	public void setStopWoTime(Date stopWoTime) {
		this.stopWoTime = stopWoTime;
	}

	public Date getRestartWoTime() {
		return restartWoTime;
	}

	public void setRestartWoTime(Date restartWoTime) {
		this.restartWoTime = restartWoTime;
	}

	public String getVrobotWoId() {
		return vrobotWoId;
	}

	public void setVrobotWoId(String vrobotWoId) {
		this.vrobotWoId = vrobotWoId;
	}

	public Date getArriveWoTime() {
		return arriveWoTime;
	}

	public void setArriveWoTime(Date arriveWoTime) {
		this.arriveWoTime = arriveWoTime;
	}

	public String getIsArriveOntime() {
		return isArriveOntime;
	}

	public void setIsArriveOntime(String isArriveOntime) {
		this.isArriveOntime = isArriveOntime;
	}

	public Date getReviewWoTime() {
		return reviewWoTime;
	}

	public void setReviewWoTime(Date reviewWoTime) {
		this.reviewWoTime = reviewWoTime;
	}
}
