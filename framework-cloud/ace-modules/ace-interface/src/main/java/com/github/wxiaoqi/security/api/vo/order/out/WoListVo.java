package com.github.wxiaoqi.security.api.vo.order.out;

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
public class WoListVo implements Serializable {

	private static final long serialVersionUID = 6580228265886820303L;

	private String id;

	//标题
	@ApiModelProperty(value = "标题")
	private String title;

	//工单编码
	@ApiModelProperty(value = "工单编码")
	private String woCode;

	@ApiModelProperty(value = "工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单，5-装修监理工单)")
	private String woType;

	//描述
	@ApiModelProperty(value = "工单描述")
	private String description;

	//工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
	@ApiModelProperty(value = "工单状态")
	private String woStatus;

	@ApiModelProperty(value = "工单状态描述")
	private String woStatusStr;

	@ApiModelProperty(value = "工单创建时间")
	private String createTimeStr;

	//图片id,多张图片逗号分隔
	private String imgId;

	//图片路径
	@ApiModelProperty(value = "图片路径")
	private List<String> imgList;

	@ApiModelProperty(value = "商品实际支付金额")
	private BigDecimal actualCost;

	@ApiModelProperty(value = "商品单价(当一个订单有多种商品时数据可能不准确)")
	private BigDecimal price;
	@ApiModelProperty(value = "商品单位(当一个订单有多种商品时数据可能不准确)")
	private String unit;

	@ApiModelProperty(value = "商品总件数")
	private int totalNum;

	@ApiModelProperty(value = "买家联系人")
	private String contactName;

	@ApiModelProperty(value = "公司名称")
	private String companyName;

	@ApiModelProperty(value = "区分报修/投诉/计划性工单/装修监理工单")
	private String incidentType;

	@ApiModelProperty(value = "是否代客，代客-1 非代客-0")
	private String valet;


	@ApiModelProperty(value = "联系人方式")
	private String contactTel;
	@ApiModelProperty(value = "联系地址")
	private String address;
	@ApiModelProperty(value = "装修阶段")
	private String decoreteStage;
	@ApiModelProperty(value = "建筑面积")
	private String coveredArea;
	@ApiModelProperty(value = "支付金额")
	private String cost;

	@ApiModelProperty(value = "是否支持发票(1-是2-否)")
	private String invoiceType;

	@ApiModelProperty(value = "工单是否超时(1-超时2-正常)")
	private String timeOut;

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
