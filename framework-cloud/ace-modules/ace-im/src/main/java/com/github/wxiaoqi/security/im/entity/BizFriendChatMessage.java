package com.github.wxiaoqi.security.im.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
@Table(name = "biz_friend_chat_message")
public class BizFriendChatMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //发送者
    @Column(name = "from_user_id")
    private String fromUserId;
	
	    //接收者
    @Column(name = "to_user_id")
    private String toUserId;
	
	    //
    @Column(name = "message")
    private String message;
	
	    //聊天信息
    @Column(name = "create_time")
    private Date createTime;
	
	    //o未读 1已读
    @Column(name = "is_read")
    private String isRead;
	
	    //
    @Column(name = "msg_type")
    private Integer msgType;
	
	    //
    @Column(name = "small_img")
    private String smallImg;
	
	    //
    @Column(name = "is_send")
    private String isSend;
	
	    //
    @Column(name = "update_time")
    private Date updateTime;
	
	    //
    @Column(name = "read_time")
    private Date readTime;
	
	    //
    @Column(name = "user_type")
    private String userType;
	
	    //
    @Column(name = "project_id")
    private String projectId;
	
	    //
    @Column(name = "house_id")
    private String houseId;
	

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：发送者
	 */
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	/**
	 * 获取：发送者
	 */
	public String getFromUserId() {
		return fromUserId;
	}
	/**
	 * 设置：接收者
	 */
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	/**
	 * 获取：接收者
	 */
	public String getToUserId() {
		return toUserId;
	}
	/**
	 * 设置：
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取：
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置：聊天信息
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：聊天信息
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：o未读 1已读
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	/**
	 * 获取：o未读 1已读
	 */
	public String getIsRead() {
		return isRead;
	}
	/**
	 * 设置：
	 */
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	/**
	 * 获取：
	 */
	public Integer getMsgType() {
		return msgType;
	}
	/**
	 * 设置：
	 */
	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}
	/**
	 * 获取：
	 */
	public String getSmallImg() {
		return smallImg;
	}
	/**
	 * 设置：
	 */
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	/**
	 * 获取：
	 */
	public String getIsSend() {
		return isSend;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	/**
	 * 获取：
	 */
	public Date getReadTime() {
		return readTime;
	}
	/**
	 * 设置：
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * 获取：
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * 设置：
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：
	 */
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	/**
	 * 获取：
	 */
	public String getHouseId() {
		return houseId;
	}
}
