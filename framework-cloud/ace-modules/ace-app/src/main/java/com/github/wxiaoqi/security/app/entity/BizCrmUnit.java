package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 单元表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:11
 */
@Table(name = "biz_crm_unit")
@Data
public class BizCrmUnit implements Serializable {
	private static final long serialVersionUID = 2351279679109875795L;
	
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
