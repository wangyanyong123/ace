package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户邀请成为房屋成员表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Table(name = "biz_user_invite")
@Data
public class BizUserInvite implements Serializable {
	private static final long serialVersionUID = -1173280743785429810L;
	
	    //ID
    @Id
    private String id;
	
	    //邀请人id
    @Column(name = "inviter_id")
    private String inviterId;
	
	    //被邀请人手机号
    @Column(name = "invited_phone")
    private String invitedPhone;
	
	    //房间id
    @Column(name = "house_id")
    private String houseId;
	
	    //身份类型：1、家属；2、租客
    @Column(name = "identity_type")
    private String identityType;
	
	    //被邀请状态：0、待注册；1、邀请成功
    @Column(name = "invite_status")
    private String inviteStatus;
	
	    //状态(0-删除，1-正常)
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
