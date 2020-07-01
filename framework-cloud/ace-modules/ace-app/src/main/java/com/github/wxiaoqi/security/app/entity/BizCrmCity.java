package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 城市表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Table(name = "biz_crm_city")
@Data
public class BizCrmCity implements Serializable {
	private static final long serialVersionUID = 1007032377257889803L;
	
	    //主键
    @Id
    private String cityId;
	
	    //城市编码
    @Column(name = "city_code")
    private String cityCode;
	    //城市编码-城市标准编码
    @Column(name = "c_code")
    private String c_Code;
	
	    //城市名称
    @Column(name = "name")
    private String name;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //crm修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;

}
