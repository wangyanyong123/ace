package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 举报管理表
 * 
 * @author huangxl
 * @Date 2019-03-04 17:13:39
 */
@Table(name = "biz_report")
public class BizReport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //帖子/活动id
    @Column(name = "posts_id")
    private String postsId;
	
	    //评论id(帖子/活动)
    @Column(name = "comment_id")
    private String commentId;
	
	    //被举报人Id
    @Column(name = "be_user_id")
    private String beUserId;
	
	    //被举报人
    @Column(name = "be_report_person")
    private String beReportPerson;
	
	    //被举报者电话
    @Column(name = "be_report_tel")
    private String beReportTel;
	
	    //举报次数
    @Column(name = "report_count")
    private String reportCount;
	
	    //是否反馈，0:否、1:是
    @Column(name = "is_feedback")
    private String isFeedback;
	
	    //是否可评论，0:禁止评论、1:允许评论
    @Column(name = "is_comment")
    private String isComment;
	
	    //1-帖子,2-帖子评论,3-活动评论
    @Column(name = "type")
    private String type;
	
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
	 * 设置：帖子/活动id
	 */
	public void setPostsId(String postsId) {
		this.postsId = postsId;
	}
	/**
	 * 获取：帖子/活动id
	 */
	public String getPostsId() {
		return postsId;
	}
	/**
	 * 设置：评论id(帖子/活动)
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	/**
	 * 获取：评论id(帖子/活动)
	 */
	public String getCommentId() {
		return commentId;
	}
	/**
	 * 设置：被举报人Id
	 */
	public void setBeUserId(String beUserId) {
		this.beUserId = beUserId;
	}
	/**
	 * 获取：被举报人Id
	 */
	public String getBeUserId() {
		return beUserId;
	}
	/**
	 * 设置：被举报人
	 */
	public void setBeReportPerson(String beReportPerson) {
		this.beReportPerson = beReportPerson;
	}
	/**
	 * 获取：被举报人
	 */
	public String getBeReportPerson() {
		return beReportPerson;
	}
	/**
	 * 设置：被举报者电话
	 */
	public void setBeReportTel(String beReportTel) {
		this.beReportTel = beReportTel;
	}
	/**
	 * 获取：被举报者电话
	 */
	public String getBeReportTel() {
		return beReportTel;
	}
	/**
	 * 设置：举报次数
	 */
	public void setReportCount(String reportCount) {
		this.reportCount = reportCount;
	}
	/**
	 * 获取：举报次数
	 */
	public String getReportCount() {
		return reportCount;
	}
	/**
	 * 设置：是否反馈，0:否、1:是
	 */
	public void setIsFeedback(String isFeedback) {
		this.isFeedback = isFeedback;
	}
	/**
	 * 获取：是否反馈，0:否、1:是
	 */
	public String getIsFeedback() {
		return isFeedback;
	}
	/**
	 * 设置：是否可评论，0:禁止评论、1:允许评论
	 */
	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}
	/**
	 * 获取：是否可评论，0:禁止评论、1:允许评论
	 */
	public String getIsComment() {
		return isComment;
	}
	/**
	 * 设置：1-帖子,2-帖子评论,3-活动评论
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：1-帖子,2-帖子评论,3-活动评论
	 */
	public String getType() {
		return type;
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
