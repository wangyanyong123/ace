package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SearchSubIncident implements Serializable {

	private static final long serialVersionUID = -496396663325787429L;
	// 工单类型
	@ApiModelProperty(value = "工单类型，报修-repair 投诉-cmplain 计划-plan")
	private String incidentType;

	// 页码
	@ApiModelProperty(value = "页码")
	private int page;

	// 每页显示记录数
	@ApiModelProperty(value = "每页显示记录数")
	private int limit;

}
