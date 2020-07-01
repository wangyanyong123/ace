package com.github.wxiaoqi.security.app.vo.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class WoInVo implements Serializable {


	private static final long serialVersionUID = 612011493524995488L;
	private String id;

	//工单编码
	private String woCode;

	//Crm工单编码
	private String crmWoCode;

	//工单类型(1-即时工单,2-计划性工单)
	private String woType;

	//业务ID
	private String busId;

	//业务名称
	private String busName;

	//流程ID
	private String flowId;

	//标题
	private String title;

	//描述
	private String description;

	//项目ID
	private String projectId;

	//地块ID
	private String landId;

	//楼栋id
	private String buildId;

	//楼栋id
	private String unitId;

	//房屋id
	private String roomId;

	//地址
	private String addr;

	//一级分类编码
	private String oneCategoryCode;

	//一级分类名称
	private String oneCategoryName;

	//二级分类编码
	private String twoCategoryCode;

	//二级分类名称
	private String twoCategoryName;

	//三级分类编码
	private String threeCategoryCode;

	//三级分类名称
	private String threeCategoryName;

	//联系人用户Id
	private String contactUserId;

	//联系人名称
	private String contactName;

	//联系人电话
	private String contactTel;

	//发布人用户Id
	private String publishUserId;

	//发布人名称
	private String publishName;

	//发布人电话
	private String publishTel;

	//工单处理渠道(1-默认服务端APP)
	private String handleChannel;

	//工单来源渠道(1-客户端APP、2-服务端APP、3-CRM系统)
	private String comeFrom;

	//当前处理类型(1-抢单 、2-人工处理)
	private String dealType;

	//工单状态(00-生成工单、01-调度程序已受理,02-已接受,03-处理中,04-暂停,05-已完成,06-用户已取消,07-服务人员已取消)
	private String woStatus;

	//期望服务时间
	private String expectedServiceTimeStr;

	//当前工单工序
	private String processId;

	//指定服务资源公司ID
	private String companyId;

	//当前处理人
	private String handleBy;

	//首次接单人
	private String acceptBy;

	//图片id,多张图片逗号分隔
	private String imgId;

	private String incidentType;

	private String valet;

}
