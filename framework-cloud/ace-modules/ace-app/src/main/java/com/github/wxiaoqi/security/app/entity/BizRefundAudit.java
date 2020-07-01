package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 退款审核表
 * 
 * @author zxl
 * @Date 2019-03-28 14:08:45
 */
@Table(name = "biz_refund_audit")
@Data
public class BizRefundAudit implements Serializable {
	private static final long serialVersionUID = -538994770549824679L;
	
	    //ID
    @Id
    private String id;
	
	    //订单工单id
    @Column(name = "sub_id")
    private String subId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "sub_code")
    private String subCode;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "sub_create_time")
    private Date subCreateTime;

    //单据所属用户id
    @Column(name = "user_id")
    private String userId;

    //申请金额
    @Column(name = "apply_price")
    private BigDecimal applyPrice;

    @Column(name = "actual_id")
    private String actualId;

	    //业务类型 0：旧业务 ，1：商品订单，2：服务订单
    @Column(name = "bus_type")
    private Integer busType;

	    //申请人id
    @Column(name = "apply_id")
    private String applyId;
	
	    //商户id
    @Column(name = "company_Id")
    private String companyId;
	
	    //审核状态：0、退款审核中；1、已退款；2、退款失败；3、退款失败
    @Column(name = "audit_status")
    private String auditStatus;
	
	    //状态(0-删除，1-正常)
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

    @Column(name = "apply_type")
	private String applyType;

    // 退款完成时间
    @Column(name = "refund_success_time")
    private Date refundSuccessTime;
}
