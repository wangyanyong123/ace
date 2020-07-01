package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 意见反馈
 * 
 * @author zxl
 * @Date 2019-01-08 11:00:06
 */
@Table(name = "biz_feedback")
public class BizFeedback implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //用户姓名
    @Column(name = "user_name")
    private String userName;
	
	    //用户联系方式
    @Column(name = "user_tel")
    private String userTel;
	
	    //项目ID(客户端APP时有)
    @Column(name = "project_id")
    private String projectId;
	
	    //房屋ID(客户端APP认证房屋了有)
    @Column(name = "hourse_id")
    private String hourseId;
	
	    //反馈内容
    @Column(name = "content")
    private String content;
	
	    //来源系统(1-IOS客户端APP,2-IOS员工端APP,3-android客户端APP,4-android员工端APP)
    @Column(name = "source")
    private String source;
	
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
	 * 设置：用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：用户联系方式
	 */
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	/**
	 * 获取：用户联系方式
	 */
	public String getUserTel() {
		return userTel;
	}
	/**
	 * 设置：项目ID(客户端APP时有)
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目ID(客户端APP时有)
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：房屋ID(客户端APP认证房屋了有)
	 */
	public void setHourseId(String hourseId) {
		this.hourseId = hourseId;
	}
	/**
	 * 获取：房屋ID(客户端APP认证房屋了有)
	 */
	public String getHourseId() {
		return hourseId;
	}
	/**
	 * 设置：反馈内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：反馈内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：来源系统(1-IOS客户端APP,2-IOS员工端APP,3-android客户端APP,4-android员工端APP)
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取：来源系统(1-IOS客户端APP,2-IOS员工端APP,3-android客户端APP,4-android员工端APP)
	 */
	public String getSource() {
		return source;
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
