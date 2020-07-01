package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 地块表
 * 
 * @author zxl
 * @Date 2018-12-11 11:25:48
 */
@Table(name = "biz_crm_block")
public class BizCrmBlock implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String blockId;
	
	    //地块编码
    @Column(name = "block_code")
    private String blockCode;
	
	    //地块名称
    @Column(name = "name")
    private String name;
	
	    //地块号
    @Column(name = "block_num")
    private String blockNum;
	
	    //关联项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //关联项目编码
    @Column(name = "project_code")
    private String projectCode;
	
	    //ID
    @Column(name = "property_type")
    private String propertyType;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //crm修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	

	/**
	 * 设置：主键
	 */
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	/**
	 * 获取：主键
	 */
	public String getBlockId() {
		return blockId;
	}
	/**
	 * 设置：地块编码
	 */
	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}
	/**
	 * 获取：地块编码
	 */
	public String getBlockCode() {
		return blockCode;
	}
	/**
	 * 设置：地块名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：地块名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：地块号
	 */
	public void setBlockNum(String blockNum) {
		this.blockNum = blockNum;
	}
	/**
	 * 获取：地块号
	 */
	public String getBlockNum() {
		return blockNum;
	}
	/**
	 * 设置：关联项目ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：关联项目ID
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：关联项目编码
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * 获取：关联项目编码
	 */
	public String getProjectCode() {
		return projectCode;
	}
	/**
	 * 设置：ID
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	/**
	 * 获取：ID
	 */
	public String getPropertyType() {
		return propertyType;
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
	 * 设置：crm修改时间
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	/**
	 * 获取：crm修改时间
	 */
	public Date getModifiedOn() {
		return modifiedOn;
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
