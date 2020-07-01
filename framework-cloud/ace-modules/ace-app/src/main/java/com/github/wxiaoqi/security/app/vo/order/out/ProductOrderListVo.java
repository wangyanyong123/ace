package com.github.wxiaoqi.security.app.vo.order.out;

import com.github.wxiaoqi.security.common.constant.AceDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProductOrderListVo implements Serializable {

	private static final long serialVersionUID = 3074796397183645395L;
	private String id;

	//标题
	@ApiModelProperty(value = "标题")
	private String title;

	//订单编码
	@ApiModelProperty(value = "订单编码")
	private String orderCode;

	@ApiModelProperty(value = "订单状态")
	private Integer orderStatus;

	@ApiModelProperty(value = "订单退款状态")
	private Integer refundStatus;

	@ApiModelProperty(value = "订单评论状态")
	private Integer commentStatus;

	@ApiModelProperty(value = "订单创建时间")
	private Date createTime;

	//公司名称
	@ApiModelProperty(value = "公司名称")
	private String tenantName;

	//图片路径
	@ApiModelProperty(value = "图片路径(如果有多张，指显示图片列表)")
	private List<String> imgList;

	@ApiModelProperty(value = "商品实际支付金额")
	private BigDecimal actualPrice;

	@ApiModelProperty(value = "商品总件数")
	private int quantity;

	@ApiModelProperty(value = "单价(当只有一个商品时才有效)")
	private BigDecimal price;
	@ApiModelProperty(value = "单位(当只有一个商品时才有效)")
	private String unit;

	@ApiModelProperty(value = "买家联系人")
	private String contactName;

	@ApiModelProperty(value = "发货日期")
	private Date sendTime;



	public String getOrderStatusDesc(){
		return AceDictionary.ORDER_STATUS.get(orderStatus);
	}

	public String getRefundStatusDesc(){
		return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);
	}
	public String getCommentStatusDesc(){
		return AceDictionary.PRODUCT_COMMENT.get(commentStatus);
	}


}
