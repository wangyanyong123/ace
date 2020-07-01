package com.github.wxiaoqi.security.api.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class SubToRobotVo implements Serializable {

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

    @ApiModelProperty(value = "订单状态")
    private String subStatus;

    @ApiModelProperty(value = "订单创建时间")
    private String createTimeStr;

    //当前工单工序
    @ApiModelProperty(value = "当前工单工序")
    private String processId;
    @ApiModelProperty(value = "当前工单处理人")
    private String handleBy;
    @ApiModelProperty(value = "当前工单处理人用户ID")
    private String handleByUserId;
    @ApiModelProperty(value = "实际支付ID")
    private String actualId;

    @ApiModelProperty(value = "是否可以转单(0-不可以，1-可以)")
    private String isTurn;
    @ApiModelProperty(value = "订单产品列表")
    private List<SubToRobotProductInfo> subProductInfoList;
    @ApiModelProperty(value = "实收金额字符串")
    private String actualCostStr;
    @ApiModelProperty(value = "总件数")
    private int totalNum;
    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "是否支持打印小票，0-不打印，1、打印")
    private String isPrint;
    @ApiModelProperty(value = "支付类型(1-支付宝,2-微信)")
    private String payType;

    @ApiModelProperty(value = "发票抬头")
    private String invoiceName;
    @ApiModelProperty(value = "税号")
    private String dutyNum;
    @ApiModelProperty(value = "发票类型")
    private String invoiceType;
    @ApiModelProperty(value = "优惠券ID")
    private String couponId;
    @ApiModelProperty(value = "优惠券金额")
    private String couponCostStr;
    @ApiModelProperty(value = "邮费金额")
    private String postageCostStr;


	@ApiModelProperty(value = "描述")
	private String description;
	//项目ID
	@ApiModelProperty(value = "项目ID")
	private String projectId;
	@ApiModelProperty(value = "公司ID")
	private String companyId;

}
