package com.github.wxiaoqi.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2019-04-09 16:40:10
 */
@Table(name = "biz_sys_log")
@Data
public class BizSysLog implements Serializable {
	private static final long serialVersionUID = 8918348139937617724L;
	
	    //ID
    @Id
    private String id;
	
	    //英文名称
    @Column(name = "log_name")
    private String logName;
	
	    //英文名称
    @Column(name = "ip")
    private String ip;
	
	    //操作
    @Column(name = "type")
    private String type;
	
	    //资源路径
    @Column(name = "message")
    private String message;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;


}
