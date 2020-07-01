package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


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

    @Id
    private String id;
	
	    //订单工单id
    @Column(name = "sub_id")
    private String subId;
	
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


}
