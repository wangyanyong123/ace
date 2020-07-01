package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 单元表
 * 
 * @author zxl
 * @Date 2018-12-24 11:45:40
 */
@Table(name = "biz_crm_unit")
public class BizCrmUnit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String unitId;
	
	    //预售单元编码
    @Column(name = "unit_code")
    private String unitCode;
	
	    //备案单元编码
    @Column(name = "record_unit_code")
    private String recordUnitCode;
	
	    //预售单元名称
    @Column(name = "name")
    private String name;
	
	    //备案单元名称
    @Column(name = "record_unit_name")
    private String recordUnitName;
	
	    //预售单元号
    @Column(name = "unit_num")
    private String unitNum;
	
	    //备案单元号
    @Column(name = "record_unit_num")
    private String recordUnitNum;
	
	    //关联楼栋ID
    @Column(name = "building_id")
    private String buildingId;
	
	    //关联预售楼栋编码
    @Column(name = "building_code")
    private String buildingCode;
	
	    //关联备案楼栋编码
    @Column(name = "record_building_code")
    private String recordBuildingCode;
	
	    //crm修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;
	
	    //
    @Column(name = "property_type")
    private String propertyType;
	
	    //围合ID
    @Column(name = "enclosed_id")
    private String enclosedId;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	

	/**
	 * 设置：主键
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	/**
	 * 获取：主键
	 */
	public String getUnitId() {
		return unitId;
	}
	/**
	 * 设置：预售单元编码
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	/**
	 * 获取：预售单元编码
	 */
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * 设置：备案单元编码
	 */
	public void setRecordUnitCode(String recordUnitCode) {
		this.recordUnitCode = recordUnitCode;
	}
	/**
	 * 获取：备案单元编码
	 */
	public String getRecordUnitCode() {
		return recordUnitCode;
	}
	/**
	 * 设置：预售单元名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：预售单元名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：备案单元名称
	 */
	public void setRecordUnitName(String recordUnitName) {
		this.recordUnitName = recordUnitName;
	}
	/**
	 * 获取：备案单元名称
	 */
	public String getRecordUnitName() {
		return recordUnitName;
	}
	/**
	 * 设置：预售单元号
	 */
	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}
	/**
	 * 获取：预售单元号
	 */
	public String getUnitNum() {
		return unitNum;
	}
	/**
	 * 设置：备案单元号
	 */
	public void setRecordUnitNum(String recordUnitNum) {
		this.recordUnitNum = recordUnitNum;
	}
	/**
	 * 获取：备案单元号
	 */
	public String getRecordUnitNum() {
		return recordUnitNum;
	}
	/**
	 * 设置：关联楼栋ID
	 */
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	/**
	 * 获取：关联楼栋ID
	 */
	public String getBuildingId() {
		return buildingId;
	}
	/**
	 * 设置：关联预售楼栋编码
	 */
	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}
	/**
	 * 获取：关联预售楼栋编码
	 */
	public String getBuildingCode() {
		return buildingCode;
	}
	/**
	 * 设置：关联备案楼栋编码
	 */
	public void setRecordBuildingCode(String recordBuildingCode) {
		this.recordBuildingCode = recordBuildingCode;
	}
	/**
	 * 获取：关联备案楼栋编码
	 */
	public String getRecordBuildingCode() {
		return recordBuildingCode;
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
	 * 设置：
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	/**
	 * 获取：
	 */
	public String getPropertyType() {
		return propertyType;
	}
	/**
	 * 设置：围合ID
	 */
	public void setEnclosedId(String enclosedId) {
		this.enclosedId = enclosedId;
	}
	/**
	 * 获取：围合ID
	 */
	public String getEnclosedId() {
		return enclosedId;
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
