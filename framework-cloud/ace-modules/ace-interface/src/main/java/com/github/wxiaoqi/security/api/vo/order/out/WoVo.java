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
public class WoVo implements Serializable {
	private static final long serialVersionUID = -3657044437536606655L;

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

	//工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
	@ApiModelProperty(value = "工单状态")
	private String woStatus;

	@ApiModelProperty(value = "工单状态描述")
	private String woStatusStr;

    @ApiModelProperty(value = "当前工单处理人")
    private String handleBy;

	//期望服务时间
	@ApiModelProperty(value = "期望服务时间字符串")
	private String expectedServiceTimeStr;

	@ApiModelProperty(value = "订单创建时间")
	private String createTimeStr;

	//当前工单工序
	@ApiModelProperty(value = "当前工单工序")
	private String processId;

	//图片id,多张图片逗号分隔
	@ApiModelProperty(value = "图片id,多张图片逗号分隔")
	private String imgId;

	//图片路径
	@ApiModelProperty(value = "图片路径")
	private List<ImgInfo> imgList;
	@ApiModelProperty(value = "是否可以转单(0-不可以，1-可以)")
	private String isTurn;
	@ApiModelProperty(value = "工单类型(报修-repair 投诉-cmplain)")
	private String incidentType;
	@ApiModelProperty(value = "是否是计划工单(1-是 0-不是)")
	private String isPlanWO;
	@ApiModelProperty(value = "计划工单信息")
	private List<PlanWoVo> planWoVos;

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
	@ApiModelProperty(value = "开发票0-不开发票")
	private String arriveWoTime;

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
			woStatusStr = "暂停中";
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
