package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 预约服务配送范围
 * 
 * @author guohao
 * @Date 2020-06-11 12:21:50
 */
@Data
@Table(name = "biz_reservation_delivery")
public class BizReservationDelivery implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //商户ID
    @Column(name = "company_id")
    private String companyId;
	
	    //
    @Column(name = "product_id")
    private String productId;
	
	    //区域编码
    @Column(name = "proc_code")
    private String procCode;
	
	    //区域名称
    @Column(name = "proc_name")
    private String procName;
	
	    //城市编码
    @Column(name = "city_code")
    private String cityCode;
	
	    //城市名称
    @Column(name = "city_name")
    private String cityName;
	
	    //区域全称
    @Column(name = "full_Name")
    private String fullName;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_by")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;

}
