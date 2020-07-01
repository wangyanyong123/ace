package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SearchSubIncidentInWeb extends  SearchSubIncident implements Serializable {

	private static final long serialVersionUID = 772134890331421995L;

	@ApiModelProperty(value = "起始时间")
	private String startDate;
	@ApiModelProperty(value = "结束时间")
	private String endDate;
	@ApiModelProperty(value = "搜索条件(工单编码/标题/客户名称/客户电话)")
	private String searchVal;
	@ApiModelProperty(value = "项目ID")
	private String projectId;
	@ApiModelProperty(value = "公司ID（前端不需要传值）")
	private String companyId;

	@ApiModelProperty(value = "是否代客 0、非代客，1、代客")
	private String guests;

	@ApiModelProperty(value = "同步CRM 0-未同步1-已同步2-同步失败")
	private String syncStatus;

	@ApiModelProperty(value = "工单来源")
	private String workCome;

	@ApiModelProperty(value = "工单状态")
	private String woStatus;



}
