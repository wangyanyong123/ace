package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 订购产品信息
 * @author huangxl
 * @date 2018-12-18
 */
@Data
public class BuyProductInfo implements Serializable{


	private static final long serialVersionUID = -607339946334132172L;

	@ApiModelProperty(value = "订单类型 1：普通订单；2：团购订单。3：疯抢订单。4：秒杀订单")
	private Integer orderType;

	@ApiModelProperty(value = "收货地址id")
	private String addressId;

	//联系人姓名
	@ApiModelProperty(value = "收货联系人姓名")
	private String contactName;

	//联系人手机号码
	@ApiModelProperty(value = "收货联系人手机号码")
	private String contactTel;

	//地址
	@ApiModelProperty(value = "收货地址")
	private String addr;

	@ApiModelProperty(value = "收货地址省编码")
	private String procCode;

	@ApiModelProperty(value = "收货地址城市编码")
	private String cityCode;

	@ApiModelProperty(value = "来源(1-android,2-ios)")
	private String source;

	@ApiModelProperty(value = "项目Id")
	private String projectId;

	@ApiModelProperty(value = "预约时间(生活预约产品下单需要传)")
	private String reservationTime;

	@ApiModelProperty(value = "订购公司产品信息")
	private List<CompanyProduct> companyProductList;

}