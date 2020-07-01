package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 帖子(活动)评论表
 * 
 * @author huangxl
 * @Date 2019-01-28 15:06:24
 */
@Table(name = "biz_comment")
public class BizComment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //对象(帖子、活动)id
    @Column(name = "object_id")
    private String objectId;
	
	    //小组id
    @Column(name = "group_id")
    private String groupId;
	
	    //用户Id，关联用户表Id
    @Column(name = "user_id")
    private String userId;
	
	    //父级评论ID(当是一级评论时，pid为null)
    @Column(name = "pid")
    private String pid;
	
	    //评论对象(1-帖子,2-活动）
    @Column(name = "type")
    private String type;
	
	    //评论图片
    @Column(name = "pic")
    private String pic;
	
	    //评论内容
    @Column(name = "content")
    private String content;
	
	    //显示类型(0:已隐藏、1:已显示)
    @Column(name = "show_type")
    private String showType;
	
	    //评论统计点赞数量
    @Column(name = "up_num")
    private Integer upNum;
	
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
	 * 设置：对象(帖子、活动)id
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	/**
	 * 获取：对象(帖子、活动)id
	 */
	public String getObjectId() {
		return objectId;
	}
	/**
	 * 设置：小组id
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 获取：小组id
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * 设置：用户Id，关联用户表Id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户Id，关联用户表Id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：父级评论ID(当是一级评论时，pid为null)
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父级评论ID(当是一级评论时，pid为null)
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置：评论对象(1-帖子,2-活动）
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：评论对象(1-帖子,2-活动）
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：评论图片
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：评论图片
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：评论内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：评论内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：显示类型(0:已隐藏、1:已显示)
	 */
	public void setShowType(String showType) {
		this.showType = showType;
	}
	/**
	 * 获取：显示类型(0:已隐藏、1:已显示)
	 */
	public String getShowType() {
		return showType;
	}
	/**
	 * 设置：评论统计点赞数量
	 */
	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}
	/**
	 * 获取：评论统计点赞数量
	 */
	public Integer getUpNum() {
		return upNum;
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
