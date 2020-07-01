package com.github.wxiaoqi.sms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2018-11-20 14:56:24
 */
@Data
@Table(name = "sys_msg_templet")
public class SysMsgTemplet implements Serializable {
	private static final long serialVersionUID = 1851414905857871852L;

	//ID
	@Id
	private String id;

	//主题ID
	@Column(name = "theme_id")
	private String themeId;

	//模版类型:1.手机通知，2.手机消息，3.短信，4.邮件
	@Column(name = "templet_type")
	private String templetType;

	//模版类型:1.无跳转，2.跳转HTML5，3.App内页
	@Column(name = "templet_jump_type")
	private String templetJumpType;

	//标题
	@Column(name = "title")
	private String title;

	//模板内容（模板描述字符）
	@Column(name = "templet_content")
	private String templetContent;

	//
	@Column(name = "templet_code")
	private String templetCode;

	//手机通知提示音
	@Column(name = "sound")
	private String sound;

	//点击手机通知跳转页
	@Column(name = "page")
	private String page;

	//html5页面
	@Column(name = "page_html")
	private String pageHtml;

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
