package com.github.wxiaoqi.security.report.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 楼栋用户注册载量表
 * 
 * @author zxl
 * @Date 2019-03-11 14:26:24
 */
@Table(name = "report_register_statistics")
public class ReportRegisterStatistics implements Serializable {
	private static final long serialVersionUID = 7623728014752798610L;
	
	    //ID
    @Id
    private String id;
	
	    //日期
    @Column(name = "register_date")
    private String registerDate;
	
	    //项目id
    @Column(name = "project_id")
    private Integer projectId;
	
	    //项目名
    @Column(name = "project_name")
    private Integer projectName;
	
	    //楼栋名
    @Column(name = "building_name")
    private Integer buildingName;
	
	    //楼栋id
    @Column(name = "building_id")
    private Integer buildingId;
	
	    //认证数
    @Column(name = "register_number")
    private Integer registerNumber;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
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
	 * 设置：日期
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	/**
	 * 获取：日期
	 */
	public String getRegisterDate() {
		return registerDate;
	}
	/**
	 * 设置：项目id
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目id
	 */
	public Integer getProjectId() {
		return projectId;
	}
	/**
	 * 设置：项目名
	 */
	public void setProjectName(Integer projectName) {
		this.projectName = projectName;
	}
	/**
	 * 获取：项目名
	 */
	public Integer getProjectName() {
		return projectName;
	}
	/**
	 * 设置：楼栋名
	 */
	public void setBuildingName(Integer buildingName) {
		this.buildingName = buildingName;
	}
	/**
	 * 获取：楼栋名
	 */
	public Integer getBuildingName() {
		return buildingName;
	}
	/**
	 * 设置：楼栋id
	 */
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	/**
	 * 获取：楼栋id
	 */
	public Integer getBuildingId() {
		return buildingId;
	}
	/**
	 * 设置：认证数
	 */
	public void setRegisterNumber(Integer registerNumber) {
		this.registerNumber = registerNumber;
	}
	/**
	 * 获取：认证数
	 */
	public Integer getRegisterNumber() {
		return registerNumber;
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
}
