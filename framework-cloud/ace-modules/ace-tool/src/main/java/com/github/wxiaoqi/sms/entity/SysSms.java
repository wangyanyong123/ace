package com.github.wxiaoqi.sms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 短信发送
 * 
 * @author zxl
 * @Date 2018-11-20 18:51:20
 */
@Data
@Table(name = "sys_sms")
public class SysSms implements Serializable {
	private static final long serialVersionUID = -6111898231165677921L;
	
	    //id
    @Id
    private String id;
	
	    //电话
    @Column(name = "phone")
    private String phone;
	
	    //发送内容
    @Column(name = "message")
    private String message;
	
	    //附加号
    @Column(name = "addserial")
    private String addserial;
	
	    //定时时间
    @Column(name = "sendtime")
    private Date sendtime;
	
	    //状态报告ID
    @Column(name = "seqid")
    private String seqid;
	
	    //优先级（短信优先级1-5）
    @Column(name = "smspriority")
    private String smspriority;
	
	    //返回状态
    @Column(name = "sms_status")
    private String smsStatus;
	
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
    private String modifyTime;
}
