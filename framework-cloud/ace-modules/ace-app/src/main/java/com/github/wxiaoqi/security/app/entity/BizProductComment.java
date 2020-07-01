package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author guohao
 * @Date 2020-04-24 10:43:17
 */
@Data
@Table(name = "biz_product_comment")
public class BizProductComment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "order_id")
    private String orderId;
	
	    //
    @Column(name = "order_detail_id")
    private String orderDetailId;
	
	    //
    @Column(name = "product_id")
    private String productId;
	
	    //评论分值 0~5
    @Column(name = "comment_score")
    private Integer commentScore;
	
	    //评论内容
    @Column(name = "content")
    private String content;
	
	    //
    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "user_id")
    private String userId;

   @Column(name = "nick_name")
    private String nickName;

   @Column(name = "user_head_url")
    private String userHeadUrl;

	    //审核状态 0：未审核，1：审核通过，2：审核不通过
    @Column(name = "check_status")
    private Integer checkStatus;
	
	    //
    @Column(name = "check_by")
    private String checkBy;
	
	    //
    @Column(name = "check_time")
    private Date checkTime;
	
	    //
    @Column(name = "check_content")
    private String checkContent;
	
	    //
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
