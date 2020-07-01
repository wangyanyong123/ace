package com.github.wxiaoqi.security.app.vo.reservation.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.ReservationOderDictionary;
import com.github.wxiaoqi.security.common.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ReservationOrderListVO implements Serializable {

	private static final long serialVersionUID = -5433599710849911540L;

	private String id;
	//标题
	@ApiModelProperty(value = "标题")
	private String title;

	//订单编码
	@ApiModelProperty(value = "订单编码")
	private String code;

	@ApiModelProperty(value = "订单状态")
	private Integer orderStatus;

	@ApiModelProperty(value = "订单状态描述")
	private String orderStatusStr;

	private String commentStatusStr;
	private Integer commentStatus;
	private Integer refundStatus;
	private String refundStatusStr;
	@ApiModelProperty(value = "订单创建时间")
	private String createTime;

	//公司名称
	@ApiModelProperty(value = "公司名称")
	private String companyName;

	//图片id,多张图片逗号分隔
	private String imgId;

	//图片路径
	@ApiModelProperty(value = "图片路径(如果有多张，指显示图片列表)")
	private List<String> imgList;

	@ApiModelProperty(value = "商品实际支付金额")
	private BigDecimal actualCost;

	@ApiModelProperty(value = "商品总件数")
	private int totalNum;

	@ApiModelProperty(value = "单价(当只有一个商品时才有效)")
	private BigDecimal price;
	@ApiModelProperty(value = "单位(当只有一个商品时才有效)")
	private String unit;

	@ApiModelProperty(value = "买家联系人")
	private String contactName;

	private Integer quantity;


	public List<ImgInfo> getImgList(){
		List<ImgInfo> list = new ArrayList<>();
		if(StringUtils.isNotEmpty(imgId)){
			String[] imgArrayIds =new String[] { imgId };
			if(imgId.indexOf(",")!=-1){
				imgArrayIds=imgId.split(",");
			}
			for (String url : imgArrayIds){
				ImgInfo imgInfo = new ImgInfo();
				imgInfo.setUrl(url);
				list.add(imgInfo);
			}
		}
		return list;
	}

	public String getOrderStatusStr(){
		return AceDictionary.ORDER_STATUS.get(orderStatus);

	}


	public String getCommentStatusStr(){
		return AceDictionary.PRODUCT_COMMENT.get(commentStatus);

	}

	public String getRefundStatusStr(){
		return AceDictionary.ORDER_REFUND_STATUS.get(refundStatus);

	}
}
