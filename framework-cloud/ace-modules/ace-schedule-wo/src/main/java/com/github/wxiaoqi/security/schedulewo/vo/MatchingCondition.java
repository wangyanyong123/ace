package com.github.wxiaoqi.security.schedulewo.vo;

import lombok.Data;

import java.util.List;

/**
 * 
* @author xufeng 
* @Description: 匹配条件
* @date 2015-6-8 上午10:47:42 
* @version V1.0  
*
 */
@Data
public class MatchingCondition {

	private String woId;//工单ID
	private String flowId;//流程ID
	private String dealType;//处理类型（1：专人处理   2：抢单）
	private List<String> skillList;//技能id列表
	private String projectId;//项目id
	private String landId;//土地id
	private String buildId;//楼栋id
	private String woTitle;//工单标题
	private String woDesc;//工单描述

}
