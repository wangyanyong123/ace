package com.github.wxiaoqi.security.schedulewo.vo;

import java.util.List;

/**
 * 
 * @author xufeng
 * @Description: 服务资源技能范围
 * @date 2015-6-4 上午11:39:20
 * @version V1.0
 * 
 */
public class SrsServiceskillarea {

	private String id;
	private String serviceId;// 服务资源
	private String skillId;// 技能
	private String areaId;// 服务范围Id
	private String projectId;//
	private String cStatus;// 审核状态
	private String status;// 状态
	
	private List<String> appointSrsIds;//指定的公司服务资源集合

	public SrsServiceskillarea() {
		super();
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getAppointSrsIds() {
		return appointSrsIds;
	}

	public void setAppointSrsIds(List<String> appointSrsIds) {
		this.appointSrsIds = appointSrsIds;
	}

}
