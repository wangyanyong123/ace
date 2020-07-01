package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 家里人帖子表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Table(name = "biz_family_posts")
public class BizFamilyPosts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //帖子ID
    @Id
    private String id;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //发帖人id
    @Column(name = "user_id")
    private String userId;
	
	    //帖子内容
    @Column(name = "content")
    private String content;

    //类型(1-图片,2-视频)
    @Column(name = "image_type")
	private String imageType;
	
	    //帖子图片/视频
    @Column(name = "post_image")
    private String postImage;
	
	    //显示类型(0=隐藏，1=显示)
    @Column(name = "show_type")
    private String showType;
	
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
	 * 设置：帖子内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：帖子内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：帖子图片/视频
	 */
	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}
	/**
	 * 获取：帖子图片/视频
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

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}
