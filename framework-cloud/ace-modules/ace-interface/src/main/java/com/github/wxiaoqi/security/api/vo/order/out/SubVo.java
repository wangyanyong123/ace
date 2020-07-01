package com.github.wxiaoqi.security.api.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class SubVo implements Serializable {
	private static final long serialVersionUID = 1510964283970430974L;

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
	private String createTimeStr;
	@ApiModelProperty(value = "确认收货时间")
	private String reviewWoTimeStr;
    @ApiModelProperty(value = "评论状态(0-不可评论、1-待评论、2-已评论、3-关闭)")
    private String commentStatus;

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
	private List<SubProductInfo> subProductInfoList;
	@ApiModelProperty(value = "实收金额")
	private BigDecimal actualCost;
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

	//期望服务时间
	@ApiModelProperty(value = "期望服务时间字符串")
	private String expectedServiceTimeStr;

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
	private BigDecimal couponCost;
	private BigDecimal postageCost;

	public String getPostageCostStr() {
		String postageCostStr = "";
		if (postageCost!=null) {
			if (postageCost.compareTo(BigDecimal.ZERO) == 0){
				postageCostStr = "0";
				return postageCostStr;
			}else {
				return (postageCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
			}
		}else {
			return "";
		}
	}
	public String getCouponCostStr(){
		if(couponCost!=null){
			if (couponCost.compareTo(BigDecimal.ZERO) == 0){
				return "";
			}
			return (couponCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
		}
		return "";
	}

	public String getActualCostStr(){
		if(actualCost!=null){
			return (actualCost.setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
		}else{
			return (new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
		}

	}

	public String getSubStatusStr(){
		String subStatusStr = "";
		if("0".equals(subStatus)){
			subStatusStr = "已下单";
		}else if("1".equals(subStatus)){
			subStatusStr = "处理中";
		}else if("2".equals(subStatus)){
			subStatusStr = "待支付";
		}else if("3".equals(subStatus)){
			subStatusStr = "已取消";
		}else if("4".equals(subStatus)){
			subStatusStr = "已完成";
		}else if("5".equals(subStatus)){
			subStatusStr = "待确认";
		}else if("6".equals(subStatus)){
			subStatusStr = "退款中";
		}else if("7".equals(subStatus)){
			subStatusStr = "退款完成";
		}
		return subStatusStr;
	}

	public String getWoStatusStr(){
		String woStatusStr = "";
		if("00".equals(woStatus)){
			woStatusStr = "待系统受理";
		}else if("01".equals(woStatus)){
			woStatusStr = "待接受";
		}else if("02".equals(woStatus)){
			woStatusStr = "已接受";
		}else if("03".equals(woStatus)){
			woStatusStr = "处理中";
		}else if("04".equals(woStatus)){
			woStatusStr = "暂停";
		}else if("05".equals(woStatus)){
			woStatusStr = "已完成";
		}else if("06".equals(woStatus)){
			woStatusStr = "已取消";
		}else if("07".equals(woStatus)){
			woStatusStr = "已取消";
		}
		return woStatusStr;
	}
}
