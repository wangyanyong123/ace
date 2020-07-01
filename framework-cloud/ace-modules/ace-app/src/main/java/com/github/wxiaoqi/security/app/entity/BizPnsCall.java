package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 号码管理
 */
@Data
@Table(name = "biz_pns_call")
public class BizPnsCall implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String telA; //A号码
	private String telB; //B号码
	private String telX; //X号码
	private String areaCode; //"需要X号码所属区号
	private Integer record; //是否录音，1：录音；0：不录音"
	private Integer expiration; //绑定失效时间（秒）
    private String customer; // 业务侧随传数据，可以是json和任意字符串
	private String bindId; // 绑定ID
	private Integer bindingFlag; // 绑定ID
	private Date overTime; // 超时时间
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private Date deleteTime; // 删除时间
}
