package com.github.wxiaoqi.security.common.vo.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:57 2019/4/8
 * @Modified By:
 */
@Data
public class LogInfoVo implements Serializable {

	private static final long serialVersionUID = -5094317762870995272L;

	// 1、登录2、系统3、业务
	private String logType;

	private String ip;

	private String createBy;

	private Date createTime;

	//***************************登录日志*******************************
	private String logName;
	//1、w，2、a，3、i
	private String os;
	//1、web，2、c，3、s
	private String userType;
	//----------------------------业务日志------------------------------
	private String busName;

	private String opt;

	private String uri;

	private String params;

	//-------------------------------系统日志---------------------------
	//1、内部，2、第三方
	private String type;

	private String message;
	//--------------------------------返回给页面的字段---------------------------
	private String userId;
	private String userName;
	private String account;
	private String id;


	private String projectId;
}
