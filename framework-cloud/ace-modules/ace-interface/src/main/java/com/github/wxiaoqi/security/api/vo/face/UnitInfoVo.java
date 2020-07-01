package com.github.wxiaoqi.security.api.vo.face;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:14 2018/12/25
 * @Modified By:
 */
@Data
public class UnitInfoVo implements Serializable {
	private static final long serialVersionUID = 6540252127801282737L;
	@ApiModelProperty("单元ID")
	private String unitId;
	@ApiModelProperty("单元名称")
	private String unitName;
}
