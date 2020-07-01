package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 流程操作表
 * 
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
@Table(name = "biz_flow_process_operate")
public class BizFlowProcessOperate implements Serializable {
	private static final long serialVersionUID = 3368021989184455671L;
	
	    //操作ID
    @Id
    private String id;
	
	    //流程ID
    @Column(name = "flow_Id")
    private String flowId;
	
	    //工序ID
    @Column(name = "process_Id")
    private String processId;
	
	    //操作编码
    @Column(name = "operate_Code")
    private String operateCode;
	
	    //操作名称
    @Column(name = "operate_Name")
    private String operateName;
	
	    //序号
    @Column(name = "view_Sort")
    private String viewSort;
	
	    //操作成功跳转工序
    @Column(name = "succ_next_step")
    private String succNextStep;
	
	    //操作成功后提示
    @Column(name = "succ_tips")
    private String succTips;
	
	    //操作前处理服务
    @Column(name = "before_service")
    private String beforeService;
	
	    //当前操作服务
    @Column(name = "curr_service")
    private String currService;
	
	    //操作后处理服务流
    @Column(name = "after_service")
    private String afterService;
	
	    //异常处理服务集合
    @Column(name = "exception_service")
    private String exceptionService;
	
	    //当前工单状态
    @Column(name = "now_wo_status")
    private String nowWoStatus;
	
	    //操作完工单状态
    @Column(name = "next_wo_status")
    private String nextWoStatus;

	//当前工单状态
	@Column(name = "now_sub_status")
	private String nowSubStatus;

	//操作完工单状态
	@Column(name = "next_sub_status")
	private String nextSubStatus;

	    //操作完处理下一个操作
    @Column(name = "next_operate_id")
    private String nextOperateId;
	
	    //是否生成工订单标志(1-不生成、2-生成)
    @Column(name = "create_flag")
    private String createFlag;
	
	    //按钮颜色(0-默认，1-红色)
    @Column(name = "button_colour")
    private String buttonColour;
	
	    //按钮类型(01-调接口、02-app支付页面、03-评价页面、04-app退货/售后页面、05-取消)
    @Column(name = "button_type")
    private String buttonType;
	
	    //说明
    @Column(name = "description")
    private String description;
	
	    //按钮操作类型(1-普通操作、2-支付回调、3-退款回调、4-发起退款流程、5-下单后未支付的取消、6-6-退保证金))
    @Column(name = "operate_type")
    private String operateType;
	
	    //显示范围(1-客户端显示,2-服务端显示，3-客户端和服务端都不显示)
    @Column(name = "show_flag")
    private String showFlag;
	
	    //交易流水步骤名称
    @Column(name = "translog_step_name")
    private String translogStepName;
	
	    //交易流水步骤描述
    @Column(name = "translog_step_desc")
    private String translogStepDesc;
	
	    //是否记录交易流水(0-不记录、1-记录)
    @Column(name = "is_record_translog")
    private String isRecordTranslog;
	
	    //状态(0-删除、1-正常)
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

	@Transient
	private List<BizFlowService> beforeServiceList;
	@Transient
	private List<BizFlowService> currServiceList;
	@Transient
	private List<BizFlowService> afterServiceList;
	@Transient
	private List<BizFlowService> exceptionServiceList;


	/**
	 * 设置：操作ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：操作ID
	 */
	public String getId() {
		return id;
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
	 * 设置：工序ID
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	/**
	 * 获取：工序ID
	 */
	public String getProcessId() {
		return processId;
	}
	/**
	 * 设置：操作编码
	 */
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	/**
	 * 获取：操作编码
	 */
	public String getOperateCode() {
		return operateCode;
	}
	/**
	 * 设置：操作名称
	 */
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	/**
	 * 获取：操作名称
	 */
	public String getOperateName() {
		return operateName;
	}
	/**
	 * 设置：序号
	 */
	public void setViewSort(String viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：序号
	 */
	public String getViewSort() {
		return viewSort;
	}
	/**
	 * 设置：操作成功跳转工序
	 */
	public void setSuccNextStep(String succNextStep) {
		this.succNextStep = succNextStep;
	}
	/**
	 * 获取：操作成功跳转工序
	 */
	public String getSuccNextStep() {
		return succNextStep;
	}
	/**
	 * 设置：操作成功后提示
	 */
	public void setSuccTips(String succTips) {
		this.succTips = succTips;
	}
	/**
	 * 获取：操作成功后提示
	 */
	public String getSuccTips() {
		return succTips;
	}
	/**
	 * 设置：操作前处理服务
	 */
	public void setBeforeService(String beforeService) {
		this.beforeService = beforeService;
	}
	/**
	 * 获取：操作前处理服务
	 */
	public String getBeforeService() {
		return beforeService;
	}
	/**
	 * 设置：当前操作服务
	 */
	public void setCurrService(String currService) {
		this.currService = currService;
	}
	/**
	 * 获取：当前操作服务
	 */
	public String getCurrService() {
		return currService;
	}
	/**
	 * 设置：操作后处理服务流
	 */
	public void setAfterService(String afterService) {
		this.afterService = afterService;
	}
	/**
	 * 获取：操作后处理服务流
	 */
	public String getAfterService() {
		return afterService;
	}
	/**
	 * 设置：异常处理服务集合
	 */
	public void setExceptionService(String exceptionService) {
		this.exceptionService = exceptionService;
	}
	/**
	 * 获取：异常处理服务集合
	 */
	public String getExceptionService() {
		return exceptionService;
	}
	/**
	 * 设置：操作完处理下一个操作
	 */
	public void setNextOperateId(String nextOperateId) {
		this.nextOperateId = nextOperateId;
	}
	/**
	 * 获取：操作完处理下一个操作
	 */
	public String getNextOperateId() {
		return nextOperateId;
	}
	/**
	 * 设置：是否生成工订单标志(1-不生成、2-生成)
	 */
	public void setCreateFlag(String createFlag) {
		this.createFlag = createFlag;
	}
	/**
	 * 获取：是否生成工订单标志(1-不生成、2-生成)
	 */
	public String getCreateFlag() {
		return createFlag;
	}
	/**
	 * 设置：按钮颜色(0-默认，1-红色)
	 */
	public void setButtonColour(String buttonColour) {
		this.buttonColour = buttonColour;
	}
	/**
	 * 获取：按钮颜色(0-默认，1-红色)
	 */
	public String getButtonColour() {
		return buttonColour;
	}
	/**
	 * 设置：按钮类型(01-调接口、02-app支付页面、03-评价页面、04-app退货/售后页面、05-取消)
	 */
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	/**
	 * 获取：按钮类型(01-调接口、02-app支付页面、03-评价页面、04-app退货/售后页面、05-取消)
	 */
	public String getButtonType() {
		return buttonType;
	}
	/**
	 * 设置：说明
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：说明
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：按钮操作类型(1-普通操作、2-支付回调、3-退款回调、4-发起退款流程、5-下单后未支付的取消、6-6-退保证金))
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	/**
	 * 获取：按钮操作类型(1-普通操作、2-支付回调、3-退款回调、4-发起退款流程、5-下单后未支付的取消、6-6-退保证金))
	 */
	public String getOperateType() {
		return operateType;
	}
	/**
	 * 设置：显示范围(1-客户端显示,2-服务端显示，3-客户端和服务端都不显示)
	 */
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
	/**
	 * 获取：显示范围(1-客户端显示,2-服务端显示，3-客户端和服务端都不显示)
	 */
	public String getShowFlag() {
		return showFlag;
	}
	/**
	 * 设置：交易流水步骤名称
	 */
	public void setTranslogStepName(String translogStepName) {
		this.translogStepName = translogStepName;
	}
	/**
	 * 获取：交易流水步骤名称
	 */
	public String getTranslogStepName() {
		return translogStepName;
	}
	/**
	 * 设置：交易流水步骤描述
	 */
	public void setTranslogStepDesc(String translogStepDesc) {
		this.translogStepDesc = translogStepDesc;
	}
	/**
	 * 获取：交易流水步骤描述
	 */
	public String getTranslogStepDesc() {
		return translogStepDesc;
	}
	/**
	 * 设置：是否记录交易流水(0-不记录、1-记录)
	 */
	public void setIsRecordTranslog(String isRecordTranslog) {
		this.isRecordTranslog = isRecordTranslog;
	}
	/**
	 * 获取：是否记录交易流水(0-不记录、1-记录)
	 */
	public String getIsRecordTranslog() {
		return isRecordTranslog;
	}
	/**
	 * 设置：状态(0-删除、1-正常)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0-删除、1-正常)
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

	public String getNowWoStatus() {
		return nowWoStatus;
	}

	public void setNowWoStatus(String nowWoStatus) {
		this.nowWoStatus = nowWoStatus;
	}

	public String getNextWoStatus() {
		return nextWoStatus;
	}

	public void setNextWoStatus(String nextWoStatus) {
		this.nextWoStatus = nextWoStatus;
	}

	public String getNowSubStatus() {
		return nowSubStatus;
	}

	public void setNowSubStatus(String nowSubStatus) {
		this.nowSubStatus = nowSubStatus;
	}

	public String getNextSubStatus() {
		return nextSubStatus;
	}

	public void setNextSubStatus(String nextSubStatus) {
		this.nextSubStatus = nextSubStatus;
	}

	public List<BizFlowService> getBeforeServiceList() {
		return beforeServiceList;
	}

	public void setBeforeServiceList(List<BizFlowService> beforeServiceList) {
		this.beforeServiceList = beforeServiceList;
	}

	public List<BizFlowService> getCurrServiceList() {
		return currServiceList;
	}

	public void setCurrServiceList(List<BizFlowService> currServiceList) {
		this.currServiceList = currServiceList;
	}

	public List<BizFlowService> getAfterServiceList() {
		return afterServiceList;
	}

	public void setAfterServiceList(List<BizFlowService> afterServiceList) {
		this.afterServiceList = afterServiceList;
	}

	public List<BizFlowService> getExceptionServiceList() {
		return exceptionServiceList;
	}

	public void setExceptionServiceList(List<BizFlowService> exceptionServiceList) {
		this.exceptionServiceList = exceptionServiceList;
	}
}
