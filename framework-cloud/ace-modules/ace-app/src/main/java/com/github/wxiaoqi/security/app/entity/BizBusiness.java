package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 业务表
 * 
 * @author huangxl
 * @Date 2018-11-23 13:59:37
 */
@Table(name = "biz_business")
public class BizBusiness implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //业务ID
    @Id
    private String id;
	
	    //业务编码
    @Column(name = "bus_Code")
    private String busCode;
	
	    //业务名称
    @Column(name = "bus_Name")
    private String busName;
	
	    //流程ID
    @Column(name = "flow_Id")
    private String flowId;
	
	    //生成类型(1-只生成工单,2-只生成订单,3-先生成订单后生成工单,4-先生成工单后生成订单)
    @Column(name = "create_type")
    private String createType;
	
	    //说明
    @Column(name = "description")
    private String description;
	
	    //显示方式(001 商铺方式  002 瀑布流方式）
    @Column(name = "view_Type")
    private String viewType;
	
	    //序号（隔5设置，1，5，10。。。）
    @Column(name = "view_Sort")
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
	//业务类型(1-普通类型，2-团购，3-好物探访，4-疯抢)
	@Column(name = "type")
	private String type;
	//工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)
	@Column(name = "wo_type")
	private String woType;
	//工单处理类型(1-派单,2-抢单)
	@Column(name = "deal_type")
	private String dealType;


	/**
	 * 设置：业务ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：业务ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：业务编码
	 */
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	/**
	 * 获取：业务编码
	 */
	public String getBusCode() {
		return busCode;
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
	 * 设置：创建类型(1-工单,2-订单)
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	/**
	 * 获取：创建类型(1-工单,2-订单)
	 */
	public String getCreateType() {
		return createType;
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
	 * 设置：显示方式(001 商铺方式  002 瀑布流方式）
	 */
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	/**
	 * 获取：显示方式(001 商铺方式  002 瀑布流方式）
	 */
	public String getViewType() {
		return viewType;
	}
	/**
	 * 设置：序号（隔5设置，1，5，10。。。）
	 */
	public void setViewSort(Integer viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：序号（隔5设置，1，5，10。。。）
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWoType() {
		return woType;
	}

	public void setWoType(String woType) {
		this.woType = woType;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
}
