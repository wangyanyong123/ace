package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 绑定日志
 */
@Data
@Table(name = "biz_pns_call_log")
public class BizPnsCallLog implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String telA; //A号码
	private String telB; //B号码
	private String telX; //X号码
	private String subId; //订单号
	private String areaCode; //"需要X号码所属区号
	private Integer record; //是否录音，1：录音；0：不录音"
	private Integer expiration; //绑定失效时间（秒）
    private String customer; // 业务侧随传数据，可以是json和任意字符串
	private String bindId; // 绑定ID
	private Integer bindingType; // 绑定或解绑类型：0-手动；1-自动；
	private Integer bindingFlag; // 是否绑定：0-解绑；1-绑定；
	private String bindingCode; // 绑定接口异常码
	private String bindingMsg; // 绑定接口异常码描述信息
	private Date createTime; // 创建时间
}
