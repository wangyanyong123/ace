package com.github.wxiaoqi.security.external.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2019-01-02 18:36:27
 */
@Table(name = "biz_pass")
@Data
public class BizPass implements Serializable {
	private static final long serialVersionUID = -7036537829772603272L;
	
	    //
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //二维码值
    @Column(name = "qr_val")
    private String qrVal;
	
	    //二维码类型:1.临时,2.正式,3.访客
    @Column(name = "qr_type")
    private String qrType;
	
	    //通行类型（0:进，1：出）
    @Column(name = "pass_type")
    private String passType;
	
	    //通行状态（1:成功,0：失败）
    @Column(name = "pass_status")
    private String passStatus;
	
	    //通行地址
    @Column(name = "pass_addr")
    private String passAddr;
	
	    //通行状态描述
    @Column(name = "pass_desc")
    private String passDesc;
	
	    //
    @Column(name = "time_stamp")
    private Date timeStamp;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //编码
    @Column(name = "facilities_code")
    private String facilitiesCode;
	
	    //类型（设备类型）
    @Column(name = "facilities_type")
    private String facilitiesType;
	
	    //状态
    @Column(name = "status")
    private String status;
	
	    //1:成功，2：失败
    @Column(name = "type")
    private String type;
	
	    //原因
    @Column(name = "desc")
    private String desc;
	
	    //开门状态:1未开门，2已开门
    @Column(name = "open_status")
    private String openStatus;

}
