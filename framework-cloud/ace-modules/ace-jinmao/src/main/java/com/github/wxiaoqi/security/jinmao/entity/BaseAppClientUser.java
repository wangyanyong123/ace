package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * app客户端用户表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Table(name = "base_app_client_user")
@Data
public class BaseAppClientUser implements Serializable {
	private static final long serialVersionUID = -5068338938442105430L;
	
	    //用户id
    @Id
    private String id;
	
	    //手机号
    @Column(name = "mobile_phone")
    private String mobilePhone;
	
	    //密码
    @Column(name = "password")
    private String password;
	
	    //头像
    @Column(name = "profile_photo")
    private String profilePhoto;
	
	    //昵称
    @Column(name = "nickname")
    private String nickname;
	
	    //姓名
    @Column(name = "name")
    private String name;
	
	    //生日
    @Column(name = "birthday")
    private String birthday;
	
	    //邮箱
    @Column(name = "email")
    private String email;
	
	    //性别：0、未知；1、男；2、女
    @Column(name = "sex")
    private String sex;
	
	    //类型：1、业主；2、家属；3、租客
    @Column(name = "type")
    private String type;
	
	    //租户id
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //是否认证：0、未认证；1、已认证
    @Column(name = "is_auth")
    private String isAuth;
	
	    //是否删除：0、删除；1、正常
    @Column(name = "is_deleted")
    private String isDeleted;

    @Column(name = "is_operation")
    private String isOperation;

	    //状态：0、删除；1、正常
    @Column(name = "status")
    private String status;
        //个人积分值
    @Column(name = "credits_value")
    private Integer creditsValue;
	
	    //创建时间
    @Column(name = "crt_time")
    private Date crtTime;
	
	    //更新时间
    @Column(name = "upd_time")
    private Date updTime;

	@Column(name = "crm_user_id")
	private String crmUserId;
	@Column(name = "is_sign_service")
	private String isSignService;
	@Column(name = "regist_os")
	private String registOs;

	@Column(name = "memberId")
	private String memberId;

}
