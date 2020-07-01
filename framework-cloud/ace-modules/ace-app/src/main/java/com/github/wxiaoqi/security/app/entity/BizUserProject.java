package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 用户和项目关系表
 * 
 * @author zxl
 * @Date 2018-11-22 15:22:31
 */
@Data
@Table(name = "biz_user_project")
public class BizUserProject implements Serializable {
	private static final long serialVersionUID = -4965264657056461492L;
	
	    //ID
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //项目id
    @Column(name = "project_id")
    private String projectId;
	
	    //是否最新：0、不是；1、是
    @Column(name = "is_now")
    private String isNow;
	
	    //状态(0-删除，1-正常)
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
