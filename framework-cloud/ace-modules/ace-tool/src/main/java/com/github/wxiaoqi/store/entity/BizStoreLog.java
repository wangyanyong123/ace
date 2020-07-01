package com.github.wxiaoqi.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author huangxl
 * @Date 2020-04-30 17:24:50
 */
@Data
@Table(name = "biz_store_log")
public class BizStoreLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;

    @Column(name = "order_id")
    private String orderId;
	
	    //规格ID
    @Column(name = "spec_id")
    private String specId;
	
	    //操作类型
    @Column(name = "operation_type")
    private Integer operationType;
	
	    //操作类型描述
    @Column(name = "operation_type_desc")
    private String operationTypeDesc;
	
	    //历史库存
    @Column(name = "history_num")
    private Integer historyNum;
	
	    //增加库存
    @Column(name = "access_num")
    private Integer accessNum;
	
	    //当前库存
    @Column(name = "current_num")
    private Integer currentNum;
	
	    //是否限制库存:true-限制；false-不限制；
    @Column(name = "is_limit")
    private Boolean isLimit;
	
	    //时间段：0-不限时间（商品类型）；1-上午（预约类型）；2-下午（预约类型）；
    @Column(name = "time_slot")
    private Integer timeSlot;

    @Column(name = "is_result")
    private Boolean isResult;

    @Column(name = "result_desc")
    private String resultDesc;

	    //
    @Column(name = "create_by")
    private String createBy;
	
	    //
    @Column(name = "create_time")
    private Date createTime;

    // 日志类型：0-参数校验日志；1-操作结果日志
    @Column(name = "log_type")
    private Boolean logType;
}
