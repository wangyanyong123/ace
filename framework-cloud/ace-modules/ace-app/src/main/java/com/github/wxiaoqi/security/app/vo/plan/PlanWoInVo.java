package com.github.wxiaoqi.security.app.vo.plan;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanWoInVo implements Serializable {

	private static final long serialVersionUID = 612011493524995488L;

	//Crm工单编码
	private String crmWoCode;

	//工单类型(1-即时工单,2-计划性工单)
	private String woType;

	//描述
	private String description;

	//项目ID
	private String projectId;

	//工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
	private String comeFrom;

	//当前处理类型(1-抢单 、2-人工处理)
	private String dealType;

	//工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
	private String woStatus;

	//期望服务时间
	private String expectedServiceTimeStr;

	private List<PlanWoTrDto> planWoTrDtos;

	private List<PlanWoOptDetailDto> planWoOptDetailDtos;

}
