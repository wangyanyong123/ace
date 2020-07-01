package com.github.wxiaoqi.security.app.vo.plan;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:04 2019/2/27
 * @Modified By:
 */
@Data
public class PlanWoPmpsDto implements Serializable {
	private static final long serialVersionUID = -8938105706043100157L;
	//步骤文档
	private String doc;

	//步骤描述
	private String instructions;

	//程序编码
	private String pmpId;

	//步骤编码
	private Integer pmpsId;

	//操作类型
	private String opType;

	//选项列表
	private String opVal;
}
