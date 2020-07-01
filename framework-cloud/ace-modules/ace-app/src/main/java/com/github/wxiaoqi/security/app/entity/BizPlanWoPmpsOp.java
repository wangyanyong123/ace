package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 计划工单程序表
 * 
 * @author zxl
 * @Date 2019-03-01 15:20:02
 */
@Table(name = "biz_plan_wo_pmps_op")
@Data
public class BizPlanWoPmpsOp implements Serializable {
	private static final long serialVersionUID = 5338783363698518433L;
	
	    //ID
    @Id
    private String id;
	
	    //步骤文档
    @Column(name = "pwr_id")
    private String pwrId;
	
	    //英文名称
    @Column(name = "pwps_id")
    private String pwpsId;
	
	    //英文名称
    @Column(name = "op_val")
    private String opVal;
	
	    //创建日期
    @Column(name = "create_Time")
    private Date createTime;
}
