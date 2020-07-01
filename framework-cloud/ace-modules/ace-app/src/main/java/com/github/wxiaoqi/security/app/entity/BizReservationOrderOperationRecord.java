package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-25 21:30:35
 */
@Data
@Table(name = "biz_reservation_order_operation_record")
public class BizReservationOrderOperationRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "order_id")
    private String orderId;
	
	    //当前订单状态
    @Column(name = "step_status")
    private Integer stepStatus;
	
	    //当前步骤
    @Column(name = "curr_step")
    private String currStep;
	
	    //步骤描述
    @Column(name = "description")
    private String description;
	
	    //数据状态
    @Column(name = "status")
    private String status;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "modify_by")
    private String modifyBy;
	
	    //
    @Column(name = "modify_time")
    private Date modifyTime;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;

}
