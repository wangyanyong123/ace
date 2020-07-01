package com.github.wxiaoqi.sms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 手机信息
 * 
 * @author zxl
 * @Date 2018-11-20 16:04:25
 */
@Data
@Table(name = "sys_mobile_info")
public class SysMobileInfo implements Serializable {
	private static final long serialVersionUID = -6868983124766184272L;
	
	    //ID
    @Id
    private String id;
	
	    //CID
    @Column(name = "cid")
    private String cid;
	
	    //用户ID
    @Column(name = "user_id")
    private String userId;
	
	    //客户端类型：1、ios客户端APP；2、android客户端APP；3、ios服务端APP；4、android服务端APP
    @Column(name = "client_type")
    private String clientType;
	
	    //手机类型1.Android  2. IOS
    @Column(name = "os")
    private String os;
	
	    //手机操作系统版本
    @Column(name = "os_version")
    private String osVersion;
	
	    //应用当前版本
    @Column(name = "version")
    private String version;
	
	    //客户端唯一标识
    @Column(name = "mac_id")
    private String macId;
	
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
