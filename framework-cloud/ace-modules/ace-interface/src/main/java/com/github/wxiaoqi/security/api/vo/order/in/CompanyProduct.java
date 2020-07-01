package com.github.wxiaoqi.security.api.vo.order.in;

import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 订购公司产品
 * @author huangxl
 * @date 2018-12-18
 */
@Data
public class CompanyProduct implements Serializable{


	private static final long serialVersionUID = -607339946334132172L;

	@ApiModelProperty(value = "产品ID")
	private String companyId;

	@ApiModelProperty(value = "商家下单总金额")
	private BigDecimal totalPrice;

	@ApiModelProperty(value = "店铺备注")
	private String remark;

	@ApiModelProperty(value = "订购产品信息")
	private List<SubProduct> subProductList;

	@ApiModelProperty(value = "抵扣优惠券Id")
	private String couponId;

	@ApiModelProperty(value = "发票类型(0-不开发票,1-个人,2-公司)")
	private String invoiceType;

	@ApiModelProperty(value = "发票名称")
	private String invoiceName;

	@ApiModelProperty(value = "税号")
	private String dutyNum;

	public String getInvoiceType() {
		return StringUtils.isNotEmpty(invoiceType)?invoiceType:"0";
	}


}