package com.github.wxiaoqi.security.app.vo.city.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:52 2018/11/26
 * @Modified By:
 */
@Data
public class UnitInfoVo implements Serializable {
	private static final long serialVersionUID = -425326020167961631L;
	@ApiModelProperty("单元id")
	private String unitId;
	@ApiModelProperty("单元名称")
	private String unitName;
	@ApiModelProperty("楼层信息")
	private List<FloorInfoVo> floorInfoVos;
}
