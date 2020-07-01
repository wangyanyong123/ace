package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CreateWoInVo implements Serializable {

	//工单ID
	@ApiModelProperty(value = "工单ID,前端不需输入")
	private String id;

	//描述
	@ApiModelProperty(value = "描述")
	private String description;

	//项目id
	@ApiModelProperty(value = "项目id")
	private String projectId;

	//房屋id
	@ApiModelProperty(value = "房屋id")
	private String roomId;

	//地址
	@ApiModelProperty(value = "地址")
	private String addr;


	@ApiModelProperty(value = "设备Id")
	private String eqId;

	//三级分类编码
	@ApiModelProperty(value = "报修才有(分类编码)")
	private String threeCategoryCode;

	//三级分类名称
	@ApiModelProperty(value = "报修才有(分类名称)")
	private String threeCategoryName;

	//联系人用户Id
	@ApiModelProperty(value = "服务端APP才有(联系人用户Id)")
	private String contactUserId;

	//联系人名称
	@ApiModelProperty(value = "服务端APP才有(联系人名称)")
	private String contactName;

	//联系人电话
	@ApiModelProperty(value = "服务端APP才有(联系人电话)")
	private String contactTel;

	//工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
	@ApiModelProperty(value = "工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)")
	private String comeFrom;

	//期望服务时间
	@ApiModelProperty(value = "期望服务时间,格式(yyyy-MM-dd HH:mm:ss)")
	private String expectedServiceTimeStr;

	//图片id,多张图片逗号分隔
	@ApiModelProperty(value = "图片id,多张图片逗号分隔")
	private String imgId;

	//业务ID
	@ApiModelProperty(value = "业务ID,前端不需输入")
	private String busId;

	//投诉或者报修
	@ApiModelProperty(value = "工单类型，报修-repair 投诉-cmplain")
	private String incidentType;

	@ApiModelProperty(value = "是否代客，代客-1 非代客-0")
	private String valet;
}
