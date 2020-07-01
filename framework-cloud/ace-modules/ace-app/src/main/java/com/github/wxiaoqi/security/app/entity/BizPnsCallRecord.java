package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 号码通话记录
 */
@Data
@Table(name = "biz_pns_call_record")
public class BizPnsCallRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String callId; //	通话Id
	private String bindId; // 绑定Id，和绑定接口返回的bindId一致
	private String ani; // 主叫号码 格式：手机或固话座机
	private String dnis; // 被叫号码 格式：手机或固话座机
	private String telX; // X号码
	private String modeType; // AXB、AX等
	private Integer talkingTimeLen; // 通话时长，单位：秒
	private Date startTime; // 	拨打时间 格式： yyyy-MM-dd hh:mm:ss
	private Date talkingTime; // 通话时间 格式： yyyy-MM-dd hh:mm:ss
	private Date endTime; // 挂断时间 格式： yyyy-MM-dd hh:mm:ss
	private Integer endType; // 挂机结束方 (0表示平台释放,1表示主叫，2表示被叫)
	private String endTypeDesc; // 挂机结束方描述
	private Integer endState; //	挂机状态原因
	private String endStateDesc; //	挂机状态原因描述
	private String recUrl; //	通话录音地址
	private String customer; // 	业务侧随传数据，可以是json和任意字符串
	private Date createTime; // 创建时间
}