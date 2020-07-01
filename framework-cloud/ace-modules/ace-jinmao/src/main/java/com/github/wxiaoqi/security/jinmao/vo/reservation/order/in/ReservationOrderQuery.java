package com.github.wxiaoqi.security.jinmao.vo.reservation.order.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * PC服务订单查询
 */
@Data
public class ReservationOrderQuery implements Serializable{

    private static final long serialVersionUID = 8971479821814735943L;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "下单起始时间")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "下单结束时间")
    private Date endDate;

    @ApiModelProperty(value = "搜索条件(订单号/标题/客户名称/客户电话)")
    private String searchVal;

    @ApiModelProperty(value = "项目ID")
    private List<String> projectId;

    @ApiModelProperty(value = "公司/商户ID")
    private String tenantId;

    @ApiModelProperty(value = "订单状态(5-待支付、6-拼团中、30-待评价、40-退款中、45-已关闭、50-已完成、110-待受理、115-待上门）")
    private Integer orderStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预约时间区间 开始时间 (要求精确到天)")
    private Date startReservationTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预约时间区间 结束时间（要求精确到天）")
    private Date endReservationTime;

    // 页码
    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    // 每页显示记录数
    @ApiModelProperty(value = "每页显示记录数")
    private Integer limit = 10;

    @ApiModelProperty(value = "退款状态(0-无退款、10-已退款、15-部分退款、20-退款完成)")
    private Integer refundStatus;

    @ApiModelProperty(value = "评论状态(0-未评论、1-已评论)")
    private Integer commentStatus;
}
