package com.github.wxiaoqi.security.app.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 消息通知
 *
 * @Date 2019-02-27 11:58:04
 */
@Table(name = "sys_msg_notice")
public class SysMsgNotice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //接收者ID
    @Column(name = "receiver_id")
    private String receiverId;
	
	    //业务类型（1 报修 2 住户审核 3 系统通知）
    @Column(name = "msg_type")
    private String msgType;
	
	    //业务ID
    @Column(name = "object_id")
    private String objectId;
	
	    //是否跳转
    @Column(name = "is_jump")
    private String isJump;
	
	    //点击手机通知跳转页
    @Column(name = "page")
    private String page;
	
	    //消息标题
    @Column(name = "msg_title")
    private String msgTitle;
	
	    //消息内容
    @Column(name = "msg_content")
    private String msgContent;
	
	    //是否已读
    @Column(name = "is_read")
    private String isRead;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
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
	 * 设置：接收者ID
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	/**
	 * 获取：接收者ID
	 */
	public String getReceiverId() {
		return receiverId;
	}
	/**
	 * 设置：业务类型（1 报修 2 住户审核 3 系统通知）
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * 获取：业务类型（1 报修 2 住户审核 3 系统通知）
	 */
	public String getMsgType() {
		return msgType;
	}
	/**
	 * 设置：业务ID
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	/**
	 * 获取：业务ID
	 */
	public String getObjectId() {
		return objectId;
	}
	/**
	 * 设置：是否跳转
	 */
	public void setIsJump(String isJump) {
		this.isJump = isJump;
	}
	/**
	 * 获取：是否跳转
	 */
	public String getIsJump() {
		return isJump;
	}
	/**
	 * 设置：点击手机通知跳转页
	 */
	public void setPage(String page) {
		this.page = page;
	}
	/**
	 * 获取：点击手机通知跳转页
	 */
	public String getPage() {
		return page;
	}
	/**
	 * 设置：消息标题
	 */
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	/**
	 * 获取：消息标题
	 */
	public String getMsgTitle() {
		return msgTitle;
	}
	/**
	 * 设置：消息内容
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	/**
	 * 获取：消息内容
	 */
	public String getMsgContent() {
		return msgContent;
	}
	/**
	 * 设置：是否已读
	 */
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	/**
	 * 获取：是否已读
	 */
	public String getIsRead() {
		return isRead;
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
