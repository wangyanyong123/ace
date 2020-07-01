package com.github.wxiaoqi.security.app.vo.order;

import com.github.wxiaoqi.security.api.vo.order.in.CompanyProduct;
import com.github.wxiaoqi.security.app.entity.BizSubProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 订购产品信息
 * @author huangxl
 * @date 2018-12-18
 */
@Data
public class BuyProductInfoTwo implements Serializable{


	private static final long serialVersionUID = 3008555143411066030L;

	@ApiModelProperty(value = "订购产品信息")
	private List<BizSubProduct> bizSubProductList;
	@ApiModelProperty(value = "购物车列表")
	private List<String> shoppingCartIdList;
	@ApiModelProperty(value = "商户ID")
	private String companyId;
	@ApiModelProperty(value = "实收金额=商品总金额+运费-优惠金额")
	private BigDecimal actualCost;
	@ApiModelProperty(value = "商品总金额")
	private BigDecimal productCost;
	@ApiModelProperty(value = "运费")
	private BigDecimal expressCost;
	@ApiModelProperty(value = "优惠金额(商品总金额*优惠券ID)")
	private BigDecimal discountCost;
	@ApiModelProperty(value = "订购商品数量")
	private int subNum;
	@ApiModelProperty(value = "描述")
	private String description;
	@ApiModelProperty(value = "备注，留言")
	private String remark;
	@ApiModelProperty(value = "商品图片")
	private String imgId;

	//抵扣优惠券Id
	@ApiModelProperty(value = "抵扣优惠券Id")
	private String couponId;

	//发票类型(0-不开发票,1-个人,2-公司)
	@ApiModelProperty(value = "发票类型(0-不开发票,1-个人,2-公司)")
	private String invoiceType;

	//发票名称
	@ApiModelProperty(value = "发票名称")
	private String invoiceName;

	//税号
	@ApiModelProperty(value = "税号")
	private String dutyNum;



}