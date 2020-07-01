package com.github.wxiaoqi.security.app.vo.plan.in;

import com.github.wxiaoqi.security.api.vo.order.out.PlanWoOptDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 14:35 2019/3/1
 * @Modified By:
 */
@Data
public class PlanWoParam implements Serializable {
	private static final long serialVersionUID = -5666506772485398448L;
	@ApiModelProperty(value = "设备/房屋id")
	private String id;
	@ApiModelProperty(value = "处理结果")
	private List<PlanWoOptDetail> planWoOptDetails;
}
