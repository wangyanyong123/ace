package com.github.wxiaoqi.security.app.vo.city.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 13:49 2018/11/26
 * @Modified By:
 */
@Data
public class BuildInfoVo implements Serializable {
	private static final long serialVersionUID = 261378992110069473L;
	@ApiModelProperty("楼栋id")
	private String buildId;
	@ApiModelProperty("楼栋名称")
	private String buildName;
	@ApiModelProperty("单元信息")
	private List<UnitInfoVo> unitInfoVos;
}
