package com.github.wxiaoqi.security.im.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2018-12-17 18:32:27
 */
@Data
@Table(name = "biz_chat_message")
public class BizChatMessage implements Serializable {
	private static final long serialVersionUID = -2274327089302724616L;
	
	    //
    @Id
    private String id;
	
	    //发送者
    @Column(name = "from_user_id")
    private String fromUserId;
	
	    //接收者
    @Column(name = "to_user_id")
    private String toUserId;
	
	    //聊天信息
    @Column(name = "message")
    private String message;
	
	    //聊天信息
    @Column(name = "create_time")
    private Date createTime;
	
	    //o未读 1已读
    @Column(name = "is_read")
    private String isRead;
	
	    //
    @Column(name = "msg_type")
    private Integer msgType;
	
	    //
    @Column(name = "small_img")
    private String smallImg;
	
	    //
    @Column(name = "is_send")
    private String isSend;
	
	    //
    @Column(name = "update_time")
    private Date updateTime;
	
	    //
    @Column(name = "read_time")
    private Date readTime;
    @Column(name = "user_type")
	private String userType;
  	@Column(name = "project_id")
	private String projectId;
  	@Column(name = "house_id")
	private String houseId;
    @Column(name = "is_inteligence")
    private String isInteligence;

}
