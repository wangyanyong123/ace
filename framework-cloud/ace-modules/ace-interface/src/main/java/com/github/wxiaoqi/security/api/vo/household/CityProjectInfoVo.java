package com.github.wxiaoqi.security.api.vo.household;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:05 2018/12/5
 * @Modified By:
 */
@Data
public class CityProjectInfoVo extends CityInfoVo implements Serializable {
	private static final long serialVersionUID = -9074934895670382431L;
	@ApiModelProperty("项目信息")
	private List<ProjectInfoVo> projectInfoVos;
}
