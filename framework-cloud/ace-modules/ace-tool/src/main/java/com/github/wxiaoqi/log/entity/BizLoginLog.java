package com.github.wxiaoqi.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2019-04-08 15:50:07
 */
@Table(name = "biz_login_log")
@Data
public class BizLoginLog implements Serializable {
	private static final long serialVersionUID = 1477782195925857934L;
	
	    //ID
    @Id
    private String id;
	
	    //英文名称
    @Column(name = "log_name")
    private String logName;

	@Column(name = "user_Type")
	private String userType;

	    //英文名称
    @Column(name = "ip")
    private String ip;
	
	    //英文名称
    @Column(name = "os")
    private String os;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;

    @Column(name = "project_id")
    private String projectId;

}
