package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 热门小组表
 * 
 * @author zxl
 * @Date 2018-12-18 15:13:03
 */
@Table(name = "biz_group")
public class BizGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //小组ID
    @Id
    private String id;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //小组名称
    @Column(name = "name")
    private String name;
	
	    //简介
    @Column(name = "summary")
    private String summary;
	
	    //小组分类编码(关联字典表)
    @Column(name = "classify_code")
    private String classifyCode;
	
	    //小组分类名称
    @Column(name = "classify_name")
    private String classifyName;
	
	    //logo图片
    @Column(name = "logo_image")
    private String logoImage;
	
	    //小组等级
    @Column(name = "grade")
    private String grade;
		//启用状态(1-草稿，2-已发布，3-已撤回)
    @Column(name = "enable_status")
	private String enableStatus;
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
	 * 设置：小组ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：小组ID
	 */
	public String getId() {
		return id;
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
	 * 设置：小组名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：小组名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：简介
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * 获取：简介
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * 设置：小组分类编码(关联字典表)
	 */
	public void setClassifyCode(String classifyCode) {
		this.classifyCode = classifyCode;
	}
	/**
	 * 获取：小组分类编码(关联字典表)
	 */
	public String getClassifyCode() {
		return classifyCode;
	}
	/**
	 * 设置：小组分类名称
	 */
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	/**
	 * 获取：小组分类名称
	 */
	public String getClassifyName() {
		return classifyName;
	}
	/**
	 * 设置：logo图片
	 */
	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}
	/**
	 * 获取：logo图片
	 */
	public String getLogoImage() {
		return logoImage;
	}
	/**
	 * 设置：小组等级
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	/**
	 * 获取：小组等级
	 */

	public String getGrade() {
		return grade;
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
