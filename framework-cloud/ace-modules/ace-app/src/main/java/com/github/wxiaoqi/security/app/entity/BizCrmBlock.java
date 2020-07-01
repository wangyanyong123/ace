package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 地块表
 * 
 * @author zxl
 * @Date 2018-11-28 15:14:12
 */
@Table(name = "biz_crm_block")
@Data
public class BizCrmBlock implements Serializable {
	private static final long serialVersionUID = 3745492110984218946L;
	
	    //主键
    @Id
    private String blockId;
	
	    //地块编码
    @Column(name = "block_code")
    private String blockCode;
	
	    //地块名称
    @Column(name = "name")
    private String name;
	
	    //地块号
    @Column(name = "block_num")
    private String blockNum;
	
	    //关联项目ID
    @Column(name = "project_id")
    private String projectId;
	
	    //关联项目编码
    @Column(name = "project_code")
    private String projectCode;
	
	    //ID
    @Column(name = "property_type")
    private String propertyType;
	
	    //状态(0-删除，1-正常)
    @Column(name = "status")
    private String status;
	
	    //crm修改时间
    @Column(name = "modified_on")
    private Date modifiedOn;
	
	    //创建日期
    @Column(name = "create_time")
    private Date createTime;
	
	    //修改日期
    @Column(name = "modify_time")
    private Date modifyTime;

}
