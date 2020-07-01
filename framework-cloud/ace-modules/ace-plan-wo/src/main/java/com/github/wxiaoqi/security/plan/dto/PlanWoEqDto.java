package com.github.wxiaoqi.security.plan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:07 2019/2/27
 * @Modified By:
 */
@Data
public class PlanWoEqDto implements Serializable {
	private static final long serialVersionUID = 4846644940524030253L;
	private String eqId;
	private String eqName;
	private String eqCode;
}
