package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 业务技能表
 * 
 * @author huangxl
 * @Date 2019-01-04 14:31:19
 */
@Table(name = "biz_business_skills")
public class BizBusinessSkills implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //业务ID
    @Column(name = "bus_Id")
    private String busId;
	
	    //技能编码
    @Column(name = "skill_code")
    private String skillCode;
	
	    //技能名称
    @Column(name = "skill_name")
    private String skillName;
	
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
	 * 设置：技能编码
	 */
	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}
	/**
	 * 获取：技能编码
	 */
	public String getSkillCode() {
		return skillCode;
	}
	/**
	 * 设置：技能名称
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	/**
	 * 获取：技能名称
	 */
	public String getSkillName() {
		return skillName;
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
