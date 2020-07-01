package com.github.wxiaoqi.security.jinmao.vo.ParamsModuleVo;

import java.io.Serializable;

public class ModuleSortParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7025691172578533940L;

	private String moduleId;
	
	private String sort;
	
	private String projectId;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
