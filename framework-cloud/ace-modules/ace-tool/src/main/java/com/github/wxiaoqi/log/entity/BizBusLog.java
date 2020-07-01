package com.github.wxiaoqi.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author zxl
 * @Date 2019-04-09 14:18:21
 */
@Table(name = "biz_bus_log")
@Data
public class BizBusLog implements Serializable {
	private static final long serialVersionUID = 7803369830777576083L;
	
	    //ID
    @Id
    private String id;
	
	    //英文名称
    @Column(name = "bus_name")
    private String busName;
	
	    //英文名称
    @Column(name = "ip")
    private String ip;
	
	    //操作
    @Column(name = "opt")
    private String opt;
	
	    //资源路径
    @Column(name = "uri")
    private String uri;
	
	    //资源路径
    @Column(name = "params")
    private String params;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;

}
