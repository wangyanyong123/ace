package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 楼层表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Table(name = "biz_crm_floor")
@Data
public class BizCrmFloor implements Serializable {
	private static final long serialVersionUID = 2076247134280881994L;
	
	    //主键
    @Id
    private String floorId;
	
	    //预售楼层编码
    @Column(name = "floor_code")
    private String floorCode;
	
	    //备案楼层编码
    @Column(name = "record_floor_code")
    private String recordFloorCode;
	
	    //预售楼层名称
    @Column(name = "name")
    private String name;
	
	    //备案楼层名称
    @Column(name = "record_floor_name")
    private String recordFloorName;
	
	    //预售楼层号
    @Column(name = "floor_num")
    private String floorNum;
	
	    //备案楼层号
    @Column(name = "record_floor_num")
    private String recordFloorNum;
	
	    //关联单元ID
    @Column(name = "unit_id")
    private String unitId;
	
	    //关联预售单元编码
    @Column(name = "unit_code")
    private String unitCode;
	
	    //关联备案单元编码
    @Column(name = "record_unit_code")
    private String recordUnitCode;
	
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
