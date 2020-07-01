package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 管家管理楼栋表
 * 
 * @author zxl
 * @Date 2018-12-10 10:16:12
 */
@Data
@Table(name = "base_app_housekeeper_area")
public class BaseAppHousekeeperArea implements Serializable {
	private static final long serialVersionUID = -5700769627340752668L;
	
	    //id
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

	    //项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //楼栋ID
    @Column(name = "building_id")
    private String buildingId;
	
	    //租户id
    @Column(name = "tenant_id")
    private String tenantId;
	
	    //状态：0、删除；1、正常
    @Column(name = "status")
    private String status;
	
	    //创建人
    @Column(name = "create_By")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;
	
	    //修改人
    @Column(name = "modify_By")
    private String modifyBy;
	
	    //修改日期
    @Column(name = "modify_Time")
    private Date modifyTime;
}
