package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户申请成为房屋家属、租客表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Table(name = "biz_user_audit")
@Data
public class BizUserAudit implements Serializable {
	private static final long serialVersionUID = -8373555412818371426L;
	
	    //ID
    @Id
    private String id;
	
	    //房间id
    @Column(name = "house_id")
    private String houseId;
	
	    //申请人id
    @Column(name = "apply_id")
    private String applyId;
	
	    //审核人id
    @Column(name = "audit_id")
    private String auditId;
	
	    //身份类型：1、家属；2、租客
    @Column(name = "identity_type")
    private String identityType;
	
	    //审核状态：0、待审核；1、通过；2、拒绝
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

}
