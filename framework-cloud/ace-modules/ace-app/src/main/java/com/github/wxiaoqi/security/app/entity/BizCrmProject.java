package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 项目表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Table(name = "biz_crm_project")
@Data
public class BizCrmProject implements Serializable {
	private static final long serialVersionUID = -841566228229124992L;
	
	    //主键
    @Id
    private String projectId;
	
	    //项目编码
    @Column(name = "project_code")
    private String projectCode;
	
	    //项目名称
    @Column(name = "project_name")
    private String projectName;
	
	    //关联城市id
    @Column(name = "city_id")
    private String cityId;
	
	    //关联城市编码
    @Column(name = "city_code")
    private String cityCode;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //分期
    @Column(name = "project_stage")
    private String projectStage;
	
	    //所属项目公司
    @Column(name = "business_unit_name")
    private String businessUnitName;
	
	    //所属经营单位
    @Column(name = "owning_business_unit")
    private String owningBusinessUnit;
	
	    //开工日期
    @Column(name = "begin_date")
    private Date beginDate;
	
	    //竣工日期
    @Column(name = "end_date")
    private Date endDate;
	
	    //修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;

}
