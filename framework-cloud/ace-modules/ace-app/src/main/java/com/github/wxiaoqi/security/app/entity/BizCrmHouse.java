package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 房屋表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Table(name = "biz_crm_house")
@Data
public class BizCrmHouse implements Serializable {
	private static final long serialVersionUID = -9006558111747297042L;
	
	    //主键
    @Id
    private String houseId;
	
	    //使用面积（平方米）
    @Column(name = "use_area")
    private BigDecimal useArea;
	
	    //业态类型
    @Column(name = "business_class")
    private String businessClass;
	
	    //业态类型
    @Column(name = "business_categories")
    private String businessCategories;
	
	    //实际入住日期
    @Column(name = "actual_checkin_date")
    private Date actualCheckinDate;
	
	    //产权性质
    @Column(name = "property_nature")
    private String propertyNature;
	
	    //使用性质
    @Column(name = "use_nature")
    private String useNature;
	
	    //房屋状态
    @Column(name = "house_status")
    private String houseStatus;
	
	    //朝向
    @Column(name = "orientation")
    private String orientation;
	
	    //户型-室
    @Column(name = "bedroom")
    private Integer bedroom;
	
	    //户型-厅
    @Column(name = "parlour")
    private Integer parlour;
	
	    //户型-卫
    @Column(name = "toilet")
    private Integer toilet;
	
	    //户型-厨
    @Column(name = "kitchen")
    private Integer kitchen;
	
	    //户型-阳台
    @Column(name = "balcony")
    private Integer balcony;
	
	    //建筑面积
    @Column(name = "structure_area")
    private BigDecimal structureArea;
	
	    //套内面积
    @Column(name = "inside_space")
    private BigDecimal insideSpace;
	
	    //装修标准
    @Column(name = "decoration_standard")
    private String decorationStandard;
	
	    //预售房间编码
    @Column(name = "unit_house_code")
    private String unitHouseCode;
	
	    //备案房间编码
    @Column(name = "record_house_code")
    private String recordHouseCode;
	
	    //关联楼层ID
    @Column(name = "floor_id")
    private String floorId;
	
	    //关联预售楼层编码
    @Column(name = "floor_code")
    private String floorCode;
	
	    //关联备案楼层编码
    @Column(name = "record_floor_code")
    private String recordFloorCode;
	
	    //预售房间号
    @Column(name = "house_num")
    private String houseNum;
	
	    //备案房间号
    @Column(name = "record_house_num")
    private String recordHouseNum;
	
	    //预售房间名称
    @Column(name = "house_name")
    private String houseName;
	
	    //备案房间名称
    @Column(name = "record_house_name")
    private String recordHouseName;
	
	    //crm修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;
	
	    //
    @Column(name = "block_building_code")
    private String blockBuildingCode;
	
	    //
    @Column(name = "business_type")
    private String businessType;
	
	    //
    @Column(name = "housing_resources_id")
    private String housingResourcesId;
	
	    //
    @Column(name = "project_code")
    private String projectCode;
	
	    //
    @Column(name = "project_id")
    private String projectId;
	
	    //
    @Column(name = "property_type")
    private String propertyType;
	
	    //
    @Column(name = "record_building_code")
    private String recordBuildingCode;
	
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
