package com.github.wxiaoqi.security.external.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:36 2019/1/2
 * @Modified By:
 */
@Data
public class BizQrVo implements Serializable {

	private static final long serialVersionUID = 9052748677205922134L;
	private String  id;//            varchar(36)         二维码ID
	private String qrType;//       varchar(36)      二维码类型
	private String qrNum;//       varchar(64)     输入值
	private String qrVal;//       varchar(64)      值
	private Date effTime;//      datetime          生效时间
	private Date loseTime;//     datetime          失效时间
	private int validTims;//    int(11)           有效次数
	private int useTimes;//     int(11)             已使用次数
	private int surTimes;//     int(11)             剩余次数
	private Date preUseTime;//  datetime             上次使用时间
	private String goUrl;//        varchar(512)     跳转地址
	private String goType;//       varchar(6)        跳转类型
	private String goPara;//       varchar(2048)     跳转参数
	private String status;//        char(1)            状态
	private Date timeStamp;//    datetime           时间戳
	private String createBy;//     varchar(36)     创建人
	private Date createTime;//   datetime          创建日期
	private String modifyBy;//     varchar(36)       修改人
	private Date modifyTime;//   datetime           修改日期
	private String qrStatus;//     char(1)            推送状态（0：未发送  1：已发送）[已废弃]
	private String privateNum;//私码值
	private String privateVal;//私码值
	private String enclosedId;//围合id
	private Date privateIssuedTime;//私码下发时间

	private String userId;
	private String userName;
	private String tel;
	private String enterpriseId;

	private List<String> ids;
	private String equipmentCode;
	private String auditDesc;

}