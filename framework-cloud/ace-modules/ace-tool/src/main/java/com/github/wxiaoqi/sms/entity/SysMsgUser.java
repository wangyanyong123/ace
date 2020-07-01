package com.github.wxiaoqi.sms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2018-11-21 11:06:38
 */
@Data
@Table(name = "sys_msg_user")
public class SysMsgUser implements Serializable {
	private static final long serialVersionUID = 6375708883116608257L;
	
	    //ID
    @Id
    private String id;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //消息推送ID
    @Column(name = "msg_id")
    private String msgId;
	
	    //消息状态（1未读 2已读 3删除）
    @Column(name = "msg_status")
    private String msgStatus;
	
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
