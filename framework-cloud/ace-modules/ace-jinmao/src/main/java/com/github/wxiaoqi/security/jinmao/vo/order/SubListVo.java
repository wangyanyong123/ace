package com.github.wxiaoqi.security.jinmao.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SubListVo implements Serializable {

    @ApiModelProperty(value = "订单Id")
    private String id;

    //订单编码
    @ApiModelProperty(value = "订单编码")
    private String code;

    @ApiModelProperty(value = "工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)")
    private String woType;

    //标题
    @ApiModelProperty(value = "标题")
    private String title;

    //地址
    @ApiModelProperty(value = "收货人地址")
    private String deliveryAddr;

    //联系人名称
    @ApiModelProperty(value = "收货人名称")
    private String contactName;

    //联系人电话
    @ApiModelProperty(value = "收货人电话")
    private String contactTel;

    //买家名字
    @ApiModelProperty(value = "买家名字")
    private String userName;

    //备注留言
    @ApiModelProperty(value = "备注留言")
    private String remark;

    //工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
    @ApiModelProperty(value = "工单状态")
    private String woStatus;

    @ApiModelProperty(value = "工单状态描述")
    private String woStatusStr;

    @ApiModelProperty(value = "订单状态")
    private String subStatus;

    @ApiModelProperty(value = "订单状态描述")
    private String subStatusStr;

    @ApiModelProperty(value = "订单创建时间")
    private String createTime;

    @ApiModelProperty(value = "当前工单处理人")
    private String handleBy;

    @ApiModelProperty(value = "实收金额")
    private BigDecimal actualCost;

    private String actualCostStr;

    @ApiModelProperty(value = "总件数")
    private int totalNum;
    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "单位(当只有一个商品时才有效)")
    private String unit;
}
