package com.github.wxiaoqi.sms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2018-11-20 16:27:29
 */
@Data
@Table(name = "sys_user_msg_setting")
public class SysUserMsgSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //ID
    @Id
    private String id;
	
	    //用户
    @Column(name = "user_id")
    private String userId;
	
	    //消息开关（1推送 2不推送）
    @Column(name = "send_type")
    private String sendType;
	
	    //声音类型（1声音提醒 2无声 ）
    @Column(name = "sound_type")
    private String soundType;
	
	    //振动类型（1振动 2不振动）
    @Column(name = "vibrate_type")
    private String vibrateType;
	
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

}
