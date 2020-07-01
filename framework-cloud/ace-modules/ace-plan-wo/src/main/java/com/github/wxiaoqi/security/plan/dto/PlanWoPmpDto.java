package com.github.wxiaoqi.security.plan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:03 2019/2/27
 * @Modified By:
 */
@Data
public class PlanWoPmpDto implements Serializable {
	private static final long serialVersionUID = 6958625597078650365L;
	//程序说明
	private String description;

	//程序类型
	private String pmpType;

	//程序编码
	private String pmpId;

	//数据状态
	private Integer dataStatus;

	//程序名称
	private String pmpName;

	//性质类型
	private String extendType;

	//技能
	private String trList;
}
