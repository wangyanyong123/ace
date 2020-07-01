package com.github.wxiaoqi.security.jinmao.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 决策表
 * 
 * @author guohao
 * @Date 2020-06-04 13:33:19
 */
@Data
@Table(name = "biz_decision")
public class BizDecision implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //项目id
    @Column(name = "project_id")
    private String projectId;
	
	    //事件类型 1：一般事件 2：特殊事件
    @Column(name = "event_type")
    private Integer eventType;
	
	    //标题
    @Column(name = "title")
    private String title;
	
	    //内容
    @Column(name = "content")
    private String content;
	
	    //
    @Column(name = "start_time")
    private Date startTime;
	
	    //
    @Column(name = "end_time")
    private Date endTime;
	
	    //发布状态 0：未发布， 1：已发布
    @Column(name = "publish_status")
    private Integer publishStatus;
	
	    //进度比例
    @Column(name = "progress_rate")
    private BigDecimal progressRate;

	    //进度比例
    @Column(name = "decision_status")
    private Integer decisionStatus;

	    //
    @Column(name = "remark")
    private String remark;
	
	    //项目房屋数量
    @Column(name = "house_count")
    private Integer houseCount;
	
	    //房屋总面积
    @Column(name = "house_area")
    private BigDecimal houseArea;
	
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
	
	    //数据状态
    @Column(name = "status")
    private String status;

}
