package com.github.wxiaoqi.security.merchant.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户和房屋关系表
 * 
 * @author zxl
 * @Date 2018-11-15 15:15:13
 */
@Table(name = "biz_user_house")
@Data
public class BizUserHouse implements Serializable {
	private static final long serialVersionUID = -3770110663797849682L;
	
	    //ID
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //房间id
    @Column(name = "house_id")
    private String houseId;
	
	    //身份类型：1、家属；2、租客；3、业主
    @Column(name = "identity_type")
    private String identityType;
	
	    //房间产权人姓名
    @Column(name = "house_owner")
    private String houseOwner;

	//是否最新：0、不是；1、是
	@Column(name = "is_now")
	private String isNow;
	
	    //业主是否删除该用户：0、未删除；1、已删除
    @Column(name = "is_delete")
    private String isDelete;
	
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
