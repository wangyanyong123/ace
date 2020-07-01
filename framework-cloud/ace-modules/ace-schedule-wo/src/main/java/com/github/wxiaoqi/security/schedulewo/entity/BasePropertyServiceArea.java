package com.github.wxiaoqi.security.schedulewo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2018-12-05 10:24:31
 */
@Table(name = "base_property_service_area")
public class BasePropertyServiceArea implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //app服务端用户ID
    @Column(name = "app_server_id")
    private String appServerId;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //楼栋ID(当为-1时表示全选楼栋)
    @Column(name = "build_id")
    private String buildId;
	
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
	 * 设置：楼栋ID(当为-1时表示全选楼栋)
	 */
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}
	/**
	 * 获取：楼栋ID(当为-1时表示全选楼栋)
	 */
	public String getBuildId() {
		return buildId;
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
