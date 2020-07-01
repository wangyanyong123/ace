package com.github.wxiaoqi.security.app.vo.plan.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:50 2019/3/1
 * @Modified By:
 */
@Data
public class PlanWoParamVo implements Serializable {
	private static final long serialVersionUID = -5909323662760658173L;
	@ApiModelProperty(value = "工单id")
	private String woId;
	@ApiModelProperty(value = "工单处理参数")
	private List<PlanWoParam> planWoParams;
}
