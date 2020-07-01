package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 物业人员服务技能表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
@Table(name = "base_property_service_skills")
public class BasePropertyServiceSkills implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //app服务端用户ID
    @Column(name = "app_server_id")
    private String appServerId;
	
	    //技能ID
    @Column(name = "skill_id")
    private String skillId;
	
	    //技能名称
    @Column(name = "skill_code")
    private String skillCode;

	//状态
	@Column(name = "status")
	private String status;
	
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
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：app服务端用户ID
	 */
	public void setAppServerId(String appServerId) {
		this.appServerId = appServerId;
	}
	/**
	 * 获取：app服务端用户ID
	 */
	public String getAppServerId() {
		return appServerId;
	}
	/**
	 * 设置：技能ID
	 */
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}
	/**
	 * 获取：技能ID
	 */
	public String getSkillId() {
		return skillId;
	}
	/**
	 * 设置：技能名称
	 */
	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}
	/**
	 * 获取：技能名称
	 */
	public String getSkillCode() {
		return skillCode;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
