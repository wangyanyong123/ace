package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计划工单关系表表
 * 
 * @author zxl
 * @Date 2019-02-27 15:56:15
 */
@Table(name = "biz_plan_wo_relation")
@Data
public class BizPlanWoRelation implements Serializable {
	private static final long serialVersionUID = 3113365742084449857L;
	
	    //ID
    @Id
    private String id;
	
	    //
    @Column(name = "wo_id")
    private String woId;
	
	    //英文名称
    @Column(name = "room_id")
    private String roomId;
	
	    //
    @Column(name = "eq_id")
    private String eqId;
	
	    //英文名称
    @Column(name = "pmp_id")
    private String pmpId;
	
	    //创建人
    @Column(name = "create_By")
    private String createBy;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;

    @Column(name = "is_complete")
    private String isComplete;

	//修改人
	@Column(name = "modify_By")
	private String modifyBy;

	//修改日期
	@Column(name = "modify_Time")
	private Date modifyTime;

}
