package com.github.wxiaoqi.security.im.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 好友申请表
 * 
 * @author zxl
 * @Date 2019-09-03 14:05:23
 */
@Table(name = "biz_friend_apply")
public class BizFriendApply implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //好友id
    @Column(name = "friend_id")
    private String friendId;
	
	    //是否删除该用户：0、待审核；1、通过；2、拒绝
    @Column(name = "is_pass")
    private String isPass;
	
	    //状态(0-删除，1-正常)
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
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：好友id
	 */
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	/**
	 * 获取：好友id
	 */
	public String getFriendId() {
		return friendId;
	}
	/**
	 * 设置：是否删除该用户：0、待审核；1、通过；2、拒绝
	 */
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	/**
	 * 获取：是否删除该用户：0、待审核；1、通过；2、拒绝
	 */
	public String getIsPass() {
		return isPass;
	}
	/**
	 * 设置：状态(0-删除，1-正常)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0-删除，1-正常)
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
