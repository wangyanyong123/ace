package com.github.wxiaoqi.security.api.vo.order.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SearchWoSaloon implements Serializable {


	private static final long serialVersionUID = 6058236180167324942L;
	// 工单状态：00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消
	@ApiModelProperty(value = "工单状态(99-全部，01-待接受,03-处理中，05-已完成，07-已取消,04-已暂停)")
	private String woStatus;

	@ApiModelProperty(value = "工单类型(1-投诉报修工单,2-计划性工单,3-商品工单,4-服务工单)")
	private String woType;
	// 页码
	@ApiModelProperty(value = "页码")
	private int page;

	// 每页显示记录数
	@ApiModelProperty(value = "每页显示记录数")
	private int limit = 10;
	
	@ApiModelProperty(value = "项目id")
	private String projectId;

	@ApiModelProperty(value = "位置(1-工单管理正在处理中,2-我的工单按分类)")
	private String position;
}
