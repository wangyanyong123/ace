package com.github.wxiaoqi.security.api.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:55 2019/3/1
 * @Modified By:
 */
@Data
public class PlanWoOptDetail implements Serializable {
	private static final long serialVersionUID = -5510299730320447622L;
	@ApiModelProperty(value = "步骤id",required = true)
	private String id;
	@ApiModelProperty(value = "说明")
	private String instructions;
	@ApiModelProperty(value = "操作类型")
	private String opType;
	@ApiModelProperty(value = "步骤顺序")
	private String pmpsId;
	@ApiModelProperty(value = "操作结果", required = true)
	private String opVal;
}
