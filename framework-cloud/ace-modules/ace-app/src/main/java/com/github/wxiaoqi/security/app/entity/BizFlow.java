package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 流程表
 * 
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
@Table(name = "biz_flow")
public class BizFlow implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //流程id
    @Id
    private String id;
	
	    //流程编码
    @Column(name = "flow_code")
    private String flowCode;
	
	    //流程名称
    @Column(name = "flow_Name")
    private String flowName;
	
	    //说明
    @Column(name = "description")
    private String description;
	
	    //排序
    @Column(name = "view_sort")
    private Integer viewSort;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建时间
    @Column(name = "create_date")
    private Date createDate;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //修改时间
    @Column(name = "update_date")
    private Date updateDate;
	
	    //修改人
    @Column(name = "update_by")
    private String updateBy;
	

	/**
	 * 设置：流程id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：流程id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：流程编码
	 */
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	/**
	 * 获取：流程编码
	 */
	public String getFlowCode() {
		return flowCode;
	}
	/**
	 * 设置：流程名称
	 */
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	/**
	 * 获取：流程名称
	 */
	public String getFlowName() {
		return flowName;
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
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
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
	 * 设置：修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：修改人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：修改人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
}
