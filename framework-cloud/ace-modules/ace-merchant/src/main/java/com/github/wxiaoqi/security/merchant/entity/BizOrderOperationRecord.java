package com.github.wxiaoqi.security.merchant.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author guohao
 * @Date 2020-04-23 10:28:58
 */
@Data
@Table(name = "biz_order_operation_record")
public class BizOrderOperationRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "order_id")
    private String orderId;
	
	    //
    @Column(name = "parent_id")
    private String parentId;
	
	    //当前订单状态
    @Column(name = "step_Status")
    private Integer stepStatus;

	    //
    @Column(name = "curr_step")
    private String currStep;
	
	    //
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
