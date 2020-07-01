package com.github.wxiaoqi.security.im.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 管家和用户关系表
 * 
 * @author zxl
 * @Date 2018-12-18 15:03:50
 */
@Data
@Table(name = "biz_housekeeper_user")
public class BizHousekeeperUser implements Serializable {
	private static final long serialVersionUID = 296449235271217136L;
	
	    //ID
    @Id
    private String id;
	
	    //用户id
    @Column(name = "user_id")
    private String userId;
	
	    //房间id
    @Column(name = "housekeeper_id")
    private String housekeeperId;
	
	    //是否删除该用户：0、未删除；1、已删除
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
