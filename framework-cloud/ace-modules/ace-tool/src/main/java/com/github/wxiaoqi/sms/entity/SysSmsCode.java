package com.github.wxiaoqi.sms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 短信验证码
 * 
 * @author zxl
 * @Date 2018-11-20 11:24:20
 */
@Data
@Table(name = "sys_sms_code")
public class SysSmsCode implements Serializable {
	private static final long serialVersionUID = -6528081960736181299L;
	
	    //ID
    @Id
    private Long id;
	
	    //手机号
    @Column(name = "mobile_phone")
    private String mobilePhone;

	//业务类型：1、登录验证
	@Column(name = "biz_type")
	private String bizType;

	    //位数
    @Column(name = "num")
    private Integer num;
	
	    //验证码
    @Column(name = "code")
    private String code;
	
	    //失效时间
    @Column(name = "lose_time")
    private Date loseTime;
	
	    //状态 0:未使用、1:已使用
    @Column(name = "status")
    private String status;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
}
