package com.github.wxiaoqi.security.app.vo.reservation.out;

import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
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
public class SubReservationListVo implements Serializable {

	private static final long serialVersionUID = -5433599710849911540L;

	private String id;

	//标题
	@ApiModelProperty(value = "标题")
	private String title;

	//订单编码
	@ApiModelProperty(value = "订单编码")
	private String code;

	@ApiModelProperty(value = "订单状态")
	private String subStatus;

	@ApiModelProperty(value = "订单状态描述")
	private String subStatusStr;

	@ApiModelProperty(value = "订单创建时间")
	private String createTimeStr;

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

	public String getSubStatusStr(){
		String subStatusStr = "";
		if("0".equals(subStatus)){
			subStatusStr = "已下单";
		}else if("1".equals(subStatus)){
			subStatusStr = "待接单";
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

}
