package com.github.wxiaoqi.security.api.vo.face;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:14 2018/12/25
 * @Modified By:
 */
@Data
public class LandInfoVo implements Serializable {
	private static final long serialVersionUID = 6540252447801282737L;
	@ApiModelProperty("地块ID")
	private String landId;
	@ApiModelProperty("地块名称")
	private String landName;

	@ApiModelProperty("单元列表")
	private List<UnitInfoVo> unitList;
}
