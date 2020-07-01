package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * app服务端用户表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Data
@Table(name = "base_app_server_user")
public class  BaseAppServerUser implements Serializable {
	private static final long serialVersionUID = 6401994571715082065L;
	//用户id
	@Id
	private String id;

	//手机号
	@Column(name = "mobile_phone")
	private String mobilePhone;

	//密码
	@Column(name = "password")
	private String password;

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

	//头像
	@Column(name = "profile_photo")
	private String profilePhoto;

	//资质图片
	@Column(name = "seniority_photo")
	private String seniorityPhoto;

	//是否激活(0-否，1-是)
	@Column(name = "is_active")
	private String isActive;

	//物业人员分类id
	@Column(name = "service_group_id")
	private String serviceGroupId;

	//是否是物业服务人员(0-否，1-是)
	@Column(name = "is_service")
	private String isService;

	//是否是管家(0-否，1-是)
	@Column(name = "is_housekeeper")
	private String isHousekeeper;

	//是否是客服人员(0-否，1-是)
	@Column(name = "is_customer")
	private String isCustomer;

	//租户id
	@Column(name = "tenant_id")
	private String tenantId;

	//启用状态(0表示禁用，1表示启用)
	@Column(name = "enable_status")
	private String enableStatus;

	//状态：0、删除；1、正常
	@Column(name = "status")
	private String status;

	//创建人
	@Column(name = "create_By")
	private String createBy;

	//创建日期
	@Column(name = "create_Time")
	private Date createTime;

	//修改人
	@Column(name = "modify_By")
	private String modifyBy;

	//修改日期
	@Column(name = "modify_Time")
	private Date modifyTime;

	@Column(name = "is_business")
	private String isBusiness;

}
