package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


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
	
	//  已下单：5，支付成功：10 。 支付成功：15， 团购成功：16， 已发货：20，
	//  确认收货：25， 评价完成：30， 取消订单：40， 退款审核：45， 退款驳回：46，
	//  申请售后：50，售后驳回：55，已退款：60
    @Column(name = "step_status")
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
