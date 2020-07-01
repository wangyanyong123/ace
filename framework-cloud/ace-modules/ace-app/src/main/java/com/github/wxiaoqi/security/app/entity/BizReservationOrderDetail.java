package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 预约服务订单详情表
 * 
 * @author huangxl
 * @Date 2020-04-19 17:01:24
 */
@Data
@Table(name = "biz_reservation_order_detail")
public class BizReservationOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //id
    @Id
    private String id;
	
	    //订单id
    @Column(name = "order_id")
    private String orderId;

	    //产品id
    @Column(name = "product_id")
    private String productId;
	
	    //产品名称
    @Column(name = "product_name")
    private String productName;
	
	    //规格ID
    @Column(name = "spec_id")
    private String specId;
	
	    //规格
    @Column(name = "spec_name")
    private String specName;
	
	    //图片id,多张图片逗号分隔
    @Column(name = "spec_img")
    private String specImg;
	
	    //数量
    @Column(name = "quantity")
    private Integer quantity;
	    //单价
    @Column(name = "sales_price")
    private BigDecimal salesPrice;
	
	    //总价
    @Column(name = "total_price")
    private BigDecimal totalPrice;
	
	    //单位
    @Column(name = "unit")
    private String unit;
	
	    //状态：0、删除；1、正常
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
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;

}
