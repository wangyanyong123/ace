package com.github.wxiaoqi.security.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 议事厅话题表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Table(name = "biz_chamber_topic")
public class BizChamberTopic implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //话题ID
    @Id
    private String id;
	
	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //发帖人id
    @Column(name = "user_id")
    private String userId;
	
	    //议事类型(1=话题，2=投票)
    @Column(name = "topic_type")
    private String topicType;
	
	    //话题标签id
    @Column(name = "topic_tag_id")
    private String topicTagId;
	
	    //话题内容
    @Column(name = "content")
    private String content;
	
	    //话题图片
    @Column(name = "topic_image")
    private String topicImage;
	
	    //投票标题
    @Column(name = "title")
    private String title;
	
	    //投票选择方式，1:单选、2:最多选2项、3:最多选3项、4:最多选4项...、20:最多选20项
    @Column(name = "selection")
    private String selection;
	
	    //投票截止时间
    @Column(name = "endTime")
    private Date endtime;
	
	    //显示类型(0=隐藏，1=显示)
    @Column(name = "show_type")
    private String showType;
	
	    //是否置顶(0-未置顶，1-已置顶)
    @Column(name = "is_top")
    private String isTop;
	
	    //话题统计点赞数
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
	 * 设置：话题ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：话题ID
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
	 * 设置：议事类型(1=话题，2=投票)
	 */
	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
	/**
	 * 获取：议事类型(1=话题，2=投票)
	 */
	public String getTopicType() {
		return topicType;
	}
	/**
	 * 设置：话题标签id
	 */
	public void setTopicTagId(String topicTagId) {
		this.topicTagId = topicTagId;
	}
	/**
	 * 获取：话题标签id
	 */
	public String getTopicTagId() {
		return topicTagId;
	}
	/**
	 * 设置：话题内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：话题内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：话题图片
	 */
	public void setTopicImage(String topicImage) {
		this.topicImage = topicImage;
	}
	/**
	 * 获取：话题图片
	 */
	public String getTopicImage() {
		return topicImage;
	}
	/**
	 * 设置：投票标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：投票标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：投票选择方式，1:单选、2:最多选2项、3:最多选3项、4:最多选4项...、20:最多选20项
	 */
	public void setSelection(String selection) {
		this.selection = selection;
	}
	/**
	 * 获取：投票选择方式，1:单选、2:最多选2项、3:最多选3项、4:最多选4项...、20:最多选20项
	 */
	public String getSelection() {
		return selection;
	}
	/**
	 * 设置：投票截止时间
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	/**
	 * 获取：投票截止时间
	 */
	public Date getEndtime() {
		return endtime;
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
	 * 设置：话题统计点赞数
	 */
	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}
	/**
	 * 获取：话题统计点赞数
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
