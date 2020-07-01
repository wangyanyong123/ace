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
@Table(name = "sys_msg_theme")
public class SysMsgTheme implements Serializable {
	private static final long serialVersionUID = 6781722503449066868L;

	//ID
	@Id
	private String id;

	//对应接口
	@Column(name = "theme_code")
	private String themeCode;

	//模板描述字符串，用于解释模板
	@Column(name = "theme_desc")
	private String themeDesc;

	//是否开启当前类别推送（1打开 2 关闭）
	@Column(name = "is_open")
	private String isOpen;

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
