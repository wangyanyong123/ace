package com.github.wxiaoqi.security.api.vo.order.out;

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
public class PlanWoDetailVo implements Serializable {
	private static final long serialVersionUID = -6360099521344582944L;
	@ApiModelProperty(value = "设备/房屋id")
	private String id;
	@ApiModelProperty(value = "工单Id")
	private String woId;
	@ApiModelProperty(value = "房屋编码")
	private String houseCode;
	@ApiModelProperty(value = "房屋名称")
	private String houseName;
	@ApiModelProperty(value = "是否完成")
	private String isComplete;
	@ApiModelProperty(value = "设备id")
	private String eqId;
	@ApiModelProperty(value = "设备code")
	private String eqCode;
	@ApiModelProperty(value = "工单操作步骤")
	private List<PlanWoOptDetail> planWoOptDetails;
}
