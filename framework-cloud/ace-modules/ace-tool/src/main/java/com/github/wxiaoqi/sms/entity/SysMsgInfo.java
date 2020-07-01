package com.github.wxiaoqi.sms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 消息推送
 * 
 * @author zxl
 * @Date 2018-11-21 11:06:38
 */
@Data
@Table(name = "sys_msg_info")
public class SysMsgInfo implements Serializable {
	private static final long serialVersionUID = 3351925954716060686L;
	
	    //ID
    @Id
    private String id;
	
	    //来源ID
    @Column(name = "source_id")
    private String sourceId;
	
	    //对象类型（0全部/1项目 /2楼栋 /3个人/4 其他）
    @Column(name = "object_type")
    private String objectType;
	
	    //消息详情ID
    @Column(name = "object_id")
    private String objectId;
	
	    //发送方式（1手机消息推送 2短信发送 3邮件发送）
    @Column(name = "send_type")
    private String sendType;
	
	    //消息类型（1后台 2 APP 3 其他 ）
    @Column(name = "msg_type")
    private String msgType;
	
	    //消息地址
    @Column(name = "msg_addr")
    private String msgAddr;
	
	    //消息标题
    @Column(name = "msg_title")
    private String msgTitle;
	
	    //消息内容
    @Column(name = "msg_content")
    private String msgContent;
	
	    //消息元数据
    @Column(name = "meta_data")
    private String metaData;
	
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
