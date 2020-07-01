package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 议事厅话题用户投票表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Table(name = "biz_chamber_topic_vote")
public class BizChamberTopicVote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //话题id
    @Column(name = "topic_Id")
    private String topicId;
	
	    //选项Id
    @Column(name = "select_Id")
    private String selectId;
	
	    //用户Id
    @Column(name = "user_Id")
    private String userId;
	
	    //状态：0删除，1正常
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
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：话题id
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	/**
	 * 获取：话题id
	 */
	public String getTopicId() {
		return topicId;
	}
	/**
	 * 设置：选项Id
	 */
	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	/**
	 * 获取：选项Id
	 */
	public String getSelectId() {
		return selectId;
	}
	/**
	 * 设置：用户Id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户Id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：状态：0删除，1正常
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0删除，1正常
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
