package com.github.wxiaoqi.security.app.vo.order;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 生成报修投诉工单
 * @author huangxl
 * @date 2018-2-12
 */
@Data
public class CreateWorkOrderIn implements Serializable{


	private static final long serialVersionUID = 8360499237810839420L;
	@ApiModelProperty(value = "CRM工单编码")
	private String crmWoCode;
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
	@ApiModelProperty(value = "分类编码")
	private String classifyCode;
	@ApiModelProperty(value = "分类名称")
	private String classifyName;
	@ApiModelProperty(value = "描述")
	private String description;
	@ApiModelProperty(value = "图片列表(filebinary 图片 Base64  FileBlob)")
	private java.util.List<Map<String,String>> fileList;

}