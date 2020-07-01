package com.github.wxiaoqi.security.api.vo.order.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WoToRobotVo implements Serializable {

	@ApiModelProperty(value = "工单Id")
	private String id;

	//工单编码
	@ApiModelProperty(value = "工单编码")
	private String woCode;

	@ApiModelProperty(value = "工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)")
	private String woType;

	//标题
	@ApiModelProperty(value = "标题")
	private String title;

	//描述
	@ApiModelProperty(value = "描述")
	private String description;

	//项目ID
	@ApiModelProperty(value = "项目ID")
	private String projectId;

	//房屋id
	@ApiModelProperty(value = "房屋id")
	private String roomId;

	//地址
	@ApiModelProperty(value = "地址")
	private String addr;

	//三级分类名称
	@ApiModelProperty(value = "三级分类名称")
	private String threeCategoryName;

	//联系人用户Id
	@ApiModelProperty(value = "联系人用户Id")
	private String contactUserId;

	//联系人名称
	@ApiModelProperty(value = "联系人名称")
	private String contactName;

	//联系人电话
	@ApiModelProperty(value = "联系人电话")
	private String contactTel;

    @ApiModelProperty(value = "当前工单处理人")
    private String handleBy;

	//期望服务时间
	@ApiModelProperty(value = "期望服务时间字符串")
	private String expectedServiceTimeStr;

	@ApiModelProperty(value = "订单创建时间")
	private String createTimeStr;

	//图片id,多张图片逗号分隔
	@ApiModelProperty(value = "图片id,多张图片逗号分隔")
	private String imgId;

	@ApiModelProperty(value = "是否可以转单(0-不可以，1-可以)")
	private String isTurn;
	@ApiModelProperty(value = "工单类型(报修-repair 投诉-cmplain)")
	private String incidentType;

	@ApiModelProperty(value = "预约服务名称")
	private String name;

	@ApiModelProperty(value = "联系地址")
	private String address;
	@ApiModelProperty(value = "装修阶段")
	private String decoreteStage;
	@ApiModelProperty(value = "建筑面积")
	private String coveredArea;
	@ApiModelProperty(value = "支付金额")
	private String cost;
	@ApiModelProperty(value = "是否支持打印小票，0-不打印，1、打印")
	private String isPrint;
	@ApiModelProperty(value = "开发票0-不开发票")
	private String invoiceType;

	@ApiModelProperty(value = "公司ID")
	private String companyId;


	//订单编码
	@ApiModelProperty(value = "订单编码")
	private String code;

	//地址
	@ApiModelProperty(value = "收货人地址")
	private String deliveryAddr;


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

	//当前工单工序
	@ApiModelProperty(value = "当前工单工序")
	private String processId;

	@ApiModelProperty(value = "当前工单处理人用户ID")
	private String handleByUserId;
	@ApiModelProperty(value = "实际支付ID")
	private String actualId;

	@ApiModelProperty(value = "订单产品列表")
	private List<SubToRobotProductInfo> subProductInfoList;
	@ApiModelProperty(value = "实收金额字符串")
	private String actualCostStr;
	@ApiModelProperty(value = "总件数")
	private int totalNum;
	//公司名称
	@ApiModelProperty(value = "公司名称")
	private String companyName;

	@ApiModelProperty(value = "支付类型(1-支付宝,2-微信)")
	private String payType;

	@ApiModelProperty(value = "发票抬头")
	private String invoiceName;
	@ApiModelProperty(value = "税号")
	private String dutyNum;

	@ApiModelProperty(value = "优惠券ID")
	private String couponId;
	@ApiModelProperty(value = "优惠券金额")
	private String couponCostStr;
	@ApiModelProperty(value = "邮费金额")
	private String postageCostStr;

}
