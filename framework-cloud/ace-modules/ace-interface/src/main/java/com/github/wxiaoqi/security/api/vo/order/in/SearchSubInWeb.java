package com.github.wxiaoqi.security.api.vo.order.in;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class SearchSubInWeb implements Serializable {

    private static final long serialVersionUID = 772134890331421995L;

    @ApiModelProperty(value = "起始时间")
    private String startDate;
    @ApiModelProperty(value = "结束时间")
    private String endDate;
    @ApiModelProperty(value = "搜索条件(工单编码/标题/客户名称/客户电话)")
    private String searchVal;
    @ApiModelProperty(value = "项目ID")
    private String projectId;
    @ApiModelProperty(value = "公司/商户ID")
    private String companyId;
    // 页码
    @ApiModelProperty(value = "页码")
    private int page;
    // 每页显示记录数
    @ApiModelProperty(value = "每页显示记录数")
    private int limit = 10;
    @ApiModelProperty(value = "订单状态(0-已下单、1-处理中、2-待支付、3-已取消、4-已完成、5-待确认、6-退款中、7-退款完成）")
    private String subStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预约时间区间 开始时间 (要求精确到天)")
    private Date startExpectedServiceTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预约时间区间 结束时间（要求精确到天）")
    private Date endExpectedServiceTime;

}
