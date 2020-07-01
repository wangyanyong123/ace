package com.github.wxiaoqi.security.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 决策投票表
 * 
 * @author guohao
 * @Date 2020-06-04 21:29:07
 */
@Data
@Table(name = "biz_decision_vote")
public class BizDecisionVote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "project_id")
    private String projectId;
	
	    //
    @Column(name = "decision_id")
    private String decisionId;
	
	    //
    @Column(name = "user_id")
    private String userId;
	
	    //
    @Column(name = "house_id")
    private String houseId;
	
	    //
    @Column(name = "house_name")
    private String houseName;
	
	    //房屋认证类型  1、家属；2、租客；3、业主
    @Column(name = "identity_type")
    private String identityType;
	
	    //进度占比
    @Column(name = "progress_rate")
    private BigDecimal progressRate;

    //投票状态 0:不同意 1：同意
	@Column(name = "vote_status")
    private Integer voteStatus;
	    //
    @Column(name = "remark")
    private String remark;
	
	    //
    @Column(name = "create_time")
    private Date createTime;
	
	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "delete_time")
    private Date deleteTime;
	
	    //数据状态
    @Column(name = "status")
    private String status;

}
