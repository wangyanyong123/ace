package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 楼栋表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Table(name = "biz_crm_building")
@Data
public class BizCrmBuilding implements Serializable {
	private static final long serialVersionUID = -5335074469404885145L;
	
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

}
