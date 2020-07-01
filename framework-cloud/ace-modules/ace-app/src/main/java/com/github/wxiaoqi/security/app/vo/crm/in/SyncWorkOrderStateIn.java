package com.github.wxiaoqi.security.app.vo.crm.in;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 报修/投诉工单同步信息
 * @author huangxl
 * @date 2018-2-12
 */
@Data
public class SyncWorkOrderStateIn implements Serializable{

	private static final long serialVersionUID = 196012222296903171L;

	@ApiModelProperty(value = "工单ID")
	private String woId;
	@ApiModelProperty(value = "工单标题")
	private String woTitle;
	@ApiModelProperty(value = "项目编码")
	private String projectId;
	@ApiModelProperty(value = "房屋编码")
	private String roomId;
	@ApiModelProperty(value = "联系人")
	private String contacts;
	@ApiModelProperty(value = "联系电话")
	private String contactTel;
	@ApiModelProperty(value = "预约时间(格式(yyyy-MM-dd HH:mm))")
	private String planTime;
	@ApiModelProperty(value = "工单类型(1-投诉，2-报修)")
	private String type;
	@ApiModelProperty(value = "一级分类编码")
	private String firstClassifyCode;
	@ApiModelProperty(value = "二级分类编码")
	private String secondClassifyCode;
	@ApiModelProperty(value = "三级分类编码")
	private String thirdClassifyCode;
	@ApiModelProperty(value = "投诉分类编码")
	private String complaintClassifyCode;
	@ApiModelProperty(value = "描述")
	private String description;
	@ApiModelProperty(value = "处理人名字")
	private String dealName;
	@ApiModelProperty(value = "处理人电话")
	private String dealTel;
	@ApiModelProperty(value = "工单状态")
	private String woStatus;
	@ApiModelProperty(value = "来源渠道")
	private String comeFrom;
	@ApiModelProperty(value = "下单时间")
	private String createTime;
	@ApiModelProperty(value = "完成时间")
	private String finishTime;
	@ApiModelProperty(value = "接单时间")
	private String acceptTime;
	@ApiModelProperty(value = "下单图片列表(filebinary:图片 FileBlob，fileId：图片唯一标识性Id)")
	private String fileList;
	@ApiModelProperty(value = "服务人员处理图片列表(filebinary:图片 FileBlob，fileId：图片唯一标识性Id)")
	private String serviceFileList;

	@ApiModelProperty(value = "服务人员完成描述")
	private String processResult;

}