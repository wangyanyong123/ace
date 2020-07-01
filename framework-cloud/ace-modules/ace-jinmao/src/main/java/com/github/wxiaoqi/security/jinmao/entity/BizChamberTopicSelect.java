package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 议事厅话题投票选项表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Table(name = "biz_chamber_topic_select")
public class BizChamberTopicSelect implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String id;
	
	    //话题ID
    @Column(name = "topic_Id")
    private String topicId;
	
	    //选项内容
    @Column(name = "select_content")
    private String selectContent;
	
	    //选项排序
    @Column(name = "sort")
    private Integer sort;
	
	    //状态：0删除，1正常
    @Column(name = "STATUS")
    private String status;
	
	    //时间戳
    @Column(name = "Time_Stamp")
    private Date timeStamp;
	
	    //创建人
    @Column(name = "Create_By")
    private String createBy;
	
	    //创建日期
    @Column(name = "Create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "Modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "Modify_Time")
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
	 * 设置：话题ID
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	/**
	 * 获取：话题ID
	 */
	public String getTopicId() {
		return topicId;
	}
	/**
	 * 设置：选项内容
	 */
	public void setSelectContent(String selectContent) {
		this.selectContent = selectContent;
	}
	/**
	 * 获取：选项内容
	 */
	public String getSelectContent() {
		return selectContent;
	}
	/**
	 * 设置：选项排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：选项排序
	 */
	public Integer getSort() {
		return sort;
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
