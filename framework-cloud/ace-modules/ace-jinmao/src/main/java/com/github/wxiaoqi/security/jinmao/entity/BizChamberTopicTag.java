package com.github.wxiaoqi.security.jinmao.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 议事厅话题标签表
 * 
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Table(name = "biz_chamber_topic_tag")
public class BizChamberTopicTag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //话题标签id
    @Id
    private String id;
	
	    //话题标签编码
    @Column(name = "topic_tag_code")
    private String topicTagCode;
	
	    //话题标签名称
    @Column(name = "topic_tag_Name")
    private String topicTagName;
	
	    //排序
    @Column(name = "view_sort")
    private Integer viewSort;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建时间
    @Column(name = "create_date")
    private Date createDate;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //修改时间
    @Column(name = "update_date")
    private Date updateDate;
	
	    //修改人
    @Column(name = "update_by")
    private String updateBy;
	

	/**
	 * 设置：话题标签id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：话题标签id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：话题标签编码
	 */
	public void setTopicTagCode(String topicTagCode) {
		this.topicTagCode = topicTagCode;
	}
	/**
	 * 获取：话题标签编码
	 */
	public String getTopicTagCode() {
		return topicTagCode;
	}
	/**
	 * 设置：话题标签名称
	 */
	public void setTopicTagName(String topicTagName) {
		this.topicTagName = topicTagName;
	}
	/**
	 * 获取：话题标签名称
	 */
	public String getTopicTagName() {
		return topicTagName;
	}
	/**
	 * 设置：排序
	 */
	public void setViewSort(Integer viewSort) {
		this.viewSort = viewSort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getViewSort() {
		return viewSort;
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
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
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
	 * 设置：修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：修改人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：修改人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
}
