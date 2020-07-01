package com.github.wxiaoqi.security.jinmao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 楼栋表
 * 
 * @author zxl
 * @Date 2018-12-11 11:25:48
 */
@Table(name = "biz_crm_building")
public class BizCrmBuilding implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //主键
    @Id
    private String housingResourcesId;
	
	    //预售楼栋编码
    @Column(name = "block_building_code")
    private String blockBuildingCode;
	
	    //备案楼栋编码
    @Column(name = "record_building_code")
    private String recordBuildingCode;
	
	    //预售楼栋号
    @Column(name = "sale_building")
    private String saleBuilding;
	
	    //备案楼栋号
    @Column(name = "building_num")
    private String buildingNum;
	
	    //预售楼栋名称
    @Column(name = "sale_building_name")
    private String saleBuildingName;
	
	    //备案楼栋名称
    @Column(name = "record_building_name")
    private String recordBuildingName;
	
	    //建筑类型
    @Column(name = "construction_type")
    private String constructionType;
	
	    //销售装修类型
    @Column(name = "decoration_standard")
    private String decorationStandard;
	
	    //层高（米）
    @Column(name = "floor_height")
    private Double floorHeight;
	
	    //总层数
    @Column(name = "total_floor")
    private Integer totalFloor;
	
	    //总户数
    @Column(name = "total_house")
    private Integer totalHouse;
	
	    //单元总数
    @Column(name = "total_unit")
    private Integer totalUnit;
	
	    //建筑面积总和（平方米）码
    @Column(name = "total_building_area")
    private BigDecimal totalBuildingArea;
	
	    //绿化面积总和（平方米）
    @Column(name = "total_green_area")
    private BigDecimal totalGreenArea;
	
	    //花园面积总和（平方米）
    @Column(name = "total_garden_area")
    private BigDecimal totalGardenArea;
	
	    //套内面积总和（平方米）
    @Column(name = "total_internal_area")
    private BigDecimal totalInternalArea;
	
	    //楼栋类型
    @Column(name = "building_type")
    private String buildingType;
	
	    //项目期数
    @Column(name = "project_period")
    private String projectPeriod;
	
	    //竣工时间
    @Column(name = "completion_time")
    private Date completionTime;
	
	    //交付时间
    @Column(name = "delivery_time")
    private Date deliveryTime;
	
	    //组团编码
    @Column(name = "group_code")
    private String groupCode;
	
	    //组团名称
    @Column(name = "group_name")
    private String groupName;
	
	    //crm修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;
	
	    //关联地块的Guid
    @Column(name = "block_id")
    private String blockId;
	
	    //关联地块的编码
    @Column(name = "block_code")
    private String blockCode;
	
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
	public void setHousingResourcesId(String housingResourcesId) {
		this.housingResourcesId = housingResourcesId;
	}
	/**
	 * 获取：主键
	 */
	public String getHousingResourcesId() {
		return housingResourcesId;
	}
	/**
	 * 设置：预售楼栋编码
	 */
	public void setBlockBuildingCode(String blockBuildingCode) {
		this.blockBuildingCode = blockBuildingCode;
	}
	/**
	 * 获取：预售楼栋编码
	 */
	public String getBlockBuildingCode() {
		return blockBuildingCode;
	}
	/**
	 * 设置：备案楼栋编码
	 */
	public void setRecordBuildingCode(String recordBuildingCode) {
		this.recordBuildingCode = recordBuildingCode;
	}
	/**
	 * 获取：备案楼栋编码
	 */
	public String getRecordBuildingCode() {
		return recordBuildingCode;
	}
	/**
	 * 设置：预售楼栋号
	 */
	public void setSaleBuilding(String saleBuilding) {
		this.saleBuilding = saleBuilding;
	}
	/**
	 * 获取：预售楼栋号
	 */
	public String getSaleBuilding() {
		return saleBuilding;
	}
	/**
	 * 设置：备案楼栋号
	 */
	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	/**
	 * 获取：备案楼栋号
	 */
	public String getBuildingNum() {
		return buildingNum;
	}
	/**
	 * 设置：预售楼栋名称
	 */
	public void setSaleBuildingName(String saleBuildingName) {
		this.saleBuildingName = saleBuildingName;
	}
	/**
	 * 获取：预售楼栋名称
	 */
	public String getSaleBuildingName() {
		return saleBuildingName;
	}
	/**
	 * 设置：备案楼栋名称
	 */
	public void setRecordBuildingName(String recordBuildingName) {
		this.recordBuildingName = recordBuildingName;
	}
	/**
	 * 获取：备案楼栋名称
	 */
	public String getRecordBuildingName() {
		return recordBuildingName;
	}
	/**
	 * 设置：建筑类型
	 */
	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}
	/**
	 * 获取：建筑类型
	 */
	public String getConstructionType() {
		return constructionType;
	}
	/**
	 * 设置：销售装修类型
	 */
	public void setDecorationStandard(String decorationStandard) {
		this.decorationStandard = decorationStandard;
	}
	/**
	 * 获取：销售装修类型
	 */
	public String getDecorationStandard() {
		return decorationStandard;
	}
	/**
	 * 设置：层高（米）
	 */
	public void setFloorHeight(Double floorHeight) {
		this.floorHeight = floorHeight;
	}
	/**
	 * 获取：层高（米）
	 */
	public Double getFloorHeight() {
		return floorHeight;
	}
	/**
	 * 设置：总层数
	 */
	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
	}
	/**
	 * 获取：总层数
	 */
	public Integer getTotalFloor() {
		return totalFloor;
	}
	/**
	 * 设置：总户数
	 */
	public void setTotalHouse(Integer totalHouse) {
		this.totalHouse = totalHouse;
	}
	/**
	 * 获取：总户数
	 */
	public Integer getTotalHouse() {
		return totalHouse;
	}
	/**
	 * 设置：单元总数
	 */
	public void setTotalUnit(Integer totalUnit) {
		this.totalUnit = totalUnit;
	}
	/**
	 * 获取：单元总数
	 */
	public Integer getTotalUnit() {
		return totalUnit;
	}
	/**
	 * 设置：建筑面积总和（平方米）码
	 */
	public void setTotalBuildingArea(BigDecimal totalBuildingArea) {
		this.totalBuildingArea = totalBuildingArea;
	}
	/**
	 * 获取：建筑面积总和（平方米）码
	 */
	public BigDecimal getTotalBuildingArea() {
		return totalBuildingArea;
	}
	/**
	 * 设置：绿化面积总和（平方米）
	 */
	public void setTotalGreenArea(BigDecimal totalGreenArea) {
		this.totalGreenArea = totalGreenArea;
	}
	/**
	 * 获取：绿化面积总和（平方米）
	 */
	public BigDecimal getTotalGreenArea() {
		return totalGreenArea;
	}
	/**
	 * 设置：花园面积总和（平方米）
	 */
	public void setTotalGardenArea(BigDecimal totalGardenArea) {
		this.totalGardenArea = totalGardenArea;
	}
	/**
	 * 获取：花园面积总和（平方米）
	 */
	public BigDecimal getTotalGardenArea() {
		return totalGardenArea;
	}
	/**
	 * 设置：套内面积总和（平方米）
	 */
	public void setTotalInternalArea(BigDecimal totalInternalArea) {
		this.totalInternalArea = totalInternalArea;
	}
	/**
	 * 获取：套内面积总和（平方米）
	 */
	public BigDecimal getTotalInternalArea() {
		return totalInternalArea;
	}
	/**
	 * 设置：楼栋类型
	 */
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}
	/**
	 * 获取：楼栋类型
	 */
	public String getBuildingType() {
		return buildingType;
	}
	/**
	 * 设置：项目期数
	 */
	public void setProjectPeriod(String projectPeriod) {
		this.projectPeriod = projectPeriod;
	}
	/**
	 * 获取：项目期数
	 */
	public String getProjectPeriod() {
		return projectPeriod;
	}
	/**
	 * 设置：竣工时间
	 */
	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}
	/**
	 * 获取：竣工时间
	 */
	public Date getCompletionTime() {
		return completionTime;
	}
	/**
	 * 设置：交付时间
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/**
	 * 获取：交付时间
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	/**
	 * 设置：组团编码
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	/**
	 * 获取：组团编码
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * 设置：组团名称
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * 获取：组团名称
	 */
	public String getGroupName() {
		return groupName;
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
	 * 设置：关联地块的Guid
	 */
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	/**
	 * 获取：关联地块的Guid
	 */
	public String getBlockId() {
		return blockId;
	}
	/**
	 * 设置：关联地块的编码
	 */
	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}
	/**
	 * 获取：关联地块的编码
	 */
	public String getBlockCode() {
		return blockCode;
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
