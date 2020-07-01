package com.github.wxiaoqi.security.app.vo.in;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SubInVo implements Serializable {


	private static final long serialVersionUID = 3111836463594004851L;
	//标题
	private String title;

	//描述
	private String description;

	//用户ID
	private String userId;

	//业务ID
	private String busId;

	//团购状态（0-非团购，1-团购中，2-已成团)
	private String groupStatus;

	//收货联系人
	private String contactName;

	//收货联系电话
	private String contactTel;

	//收货地址
	private String deliveryAddr;

	//来源(1-android,2-ios)
	private String source;

	//下单时所属项目ID
	private String projectId;

	//商户ID
	private String companyId;

	//应收金额=商品总金额+运费
	private BigDecimal receivableCost;

	//商品总金额
	private BigDecimal productCost;

	//运费
	private BigDecimal expressCost;

	//实收金额=应收金额-优惠金额
	private BigDecimal actualCost;

	//优惠金额
	private BigDecimal discountCost;

	//买家留言
	private String remark;
	//总件数
	private int totalNum;
	//图片路径
	private String imgId;

	//单价(当订单只有一个时才有效)
	private BigDecimal price;

	//单位(当订单只有一个时才有效)
	private String unit;

	//抵扣优惠券Id
	private String couponId;

	//发票类型(0-不开发票,1-个人,2-公司)
	private String invoiceType;

	//发票名称
	private String invoiceName;

	//税号
	private String dutyNum;

	//预约时间
	private String reservationTime;

}
