package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author wangyanyong
 * @Date 2020-04-25 18:23:26
 */
@Data
@Table(name = "biz_reservation_order_comment")
public class BizReservationOrderComment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_id")
    private String productId;
	
	    //
    @Column(name = "img_ids")
    private String imgIds;
	
	    //
    @Column(name = "description")
    private String description;
	
	    //
    @Column(name = "appraisal_val")
    private Integer appraisalVal;
	
	    //
    @Column(name = "is_arrive_ontime")
    private String isArriveOntime;
	
	    //
    @Column(name = "status")
    private Boolean status;
	
	    //
    @Column(name = "create_name")
    private String createName;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
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
