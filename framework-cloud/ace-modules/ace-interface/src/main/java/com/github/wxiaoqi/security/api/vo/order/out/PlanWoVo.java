package com.github.wxiaoqi.security.api.vo.order.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:03 2019/3/11
 * @Modified By:
 */
@Data
public class PlanWoVo implements Serializable {
	private static final long serialVersionUID = 1526483477793352057L;
	@ApiModelProperty("房间信息")
	private String recordHouseName;
	@ApiModelProperty("设备类型")
	private String eqName;
	@ApiModelProperty("程序类型")
	private String pmpType;
	@ApiModelProperty("程序名称")
	private String pmpName;
}
