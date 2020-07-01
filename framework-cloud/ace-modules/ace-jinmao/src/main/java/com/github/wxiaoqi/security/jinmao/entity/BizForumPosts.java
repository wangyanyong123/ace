package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 小组帖子表
 * 
 * @author huangxl
 * @Date 2019-01-28 15:06:24
 */
@Table(name = "biz_forum_posts")
public class BizForumPosts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //帖子ID
    @Id
    private String id;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //小组Id
    @Column(name = "group_id")
    private String groupId;
	
	    //发帖人id
    @Column(name = "user_id")
    private String userId;
	
	    //帖子标题
    @Column(name = "title")
    private String title;
	
	    //帖子描述
    @Column(name = "description")
    private String description;
	
	    //帖子图片
    @Column(name = "post_image")
    private String postImage;
	
	    //显示类型(0=隐藏，1=显示)
    @Column(name = "show_type")
    private String showType;
	
	    //帖子类型(1-普通帖子,2-精华帖)
    @Column(name = "posts_type")
    private String postsType;
	
	    //是否置顶(0-未置顶，1-已置顶)
    @Column(name = "is_top")
    private String isTop;
	
	    //帖子统计点赞数
    @Column(name = "up_num")
    private Integer upNum;
	
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
	 * 设置：帖子ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：帖子ID
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
	 * 设置：小组Id
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 获取：小组Id
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * 设置：发帖人id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：发帖人id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：帖子标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：帖子标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：帖子描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：帖子描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：帖子图片
	 */
	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}
	/**
	 * 获取：帖子图片
	 */
	public String getPostImage() {
		return postImage;
	}
	/**
	 * 设置：显示类型(0=隐藏，1=显示)
	 */
	public void setShowType(String showType) {
		this.showType = showType;
	}
	/**
	 * 获取：显示类型(0=隐藏，1=显示)
	 */
	public String getShowType() {
		return showType;
	}
	/**
	 * 设置：帖子类型(1-普通帖子,2-精华帖)
	 */
	public void setPostsType(String postsType) {
		this.postsType = postsType;
	}
	/**
	 * 获取：帖子类型(1-普通帖子,2-精华帖)
	 */
	public String getPostsType() {
		return postsType;
	}
	/**
	 * 设置：是否置顶(0-未置顶，1-已置顶)
	 */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	/**
	 * 获取：是否置顶(0-未置顶，1-已置顶)
	 */
	public String getIsTop() {
		return isTop;
	}
	/**
	 * 设置：帖子统计点赞数
	 */
	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}
	/**
	 * 获取：帖子统计点赞数
	 */
	public Integer getUpNum() {
		return upNum;
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
