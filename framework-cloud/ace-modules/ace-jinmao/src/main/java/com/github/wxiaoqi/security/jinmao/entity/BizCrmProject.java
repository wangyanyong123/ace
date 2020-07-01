package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 项目表
 * 
 * @author zxl
 * @Date 2018-12-11 11:12:47
 */
@Table(name = "biz_crm_project")
public class BizCrmProject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String projectId;
	
	    //项目编码
    @Column(name = "project_code")
    private String projectCode;
	
	    //项目名称
    @Column(name = "project_name")
    private String projectName;
	
	    //关联城市id
    @Column(name = "city_id")
    private String cityId;
	
	    //关联城市编码
    @Column(name = "city_code")
    private String cityCode;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //分期
    @Column(name = "project_stage")
    private String projectStage;
	
	    //所属项目公司
    @Column(name = "business_unit_name")
    private String businessUnitName;
	
	    //所属经营单位
    @Column(name = "owning_business_unit")
    private String owningBusinessUnit;
	
	    //开工日期
    @Column(name = "begin_date")
    private Date beginDate;
	
	    //竣工日期
    @Column(name = "end_date")
    private Date endDate;
	
	    //修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;
	

	/**
	 * 设置：主键
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：主键
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：项目编码
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * 获取：项目编码
	 */
	public String getProjectCode() {
		return projectCode;
	}
	/**
	 * 设置：项目名称
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * 获取：项目名称
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 设置：关联城市id
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：关联城市id
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * 设置：关联城市编码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * 获取：关联城市编码
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 设置：状态(0-删除，1-正常)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0-删除，1-正常)
	 */
	public String getStatus() {
		return status;
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
	/**
	 * 设置：分期
	 */
	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}
	/**
	 * 获取：分期
	 */
	public String getProjectStage() {
		return projectStage;
	}
	/**
	 * 设置：所属项目公司
	 */
	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}
	/**
	 * 获取：所属项目公司
	 */
	public String getBusinessUnitName() {
		return businessUnitName;
	}
	/**
	 * 设置：所属经营单位
	 */
	public void setOwningBusinessUnit(String owningBusinessUnit) {
		this.owningBusinessUnit = owningBusinessUnit;
	}
	/**
	 * 获取：所属经营单位
	 */
	public String getOwningBusinessUnit() {
		return owningBusinessUnit;
	}
	/**
	 * 设置：开工日期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * 获取：开工日期
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * 设置：竣工日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 获取：竣工日期
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}
}
