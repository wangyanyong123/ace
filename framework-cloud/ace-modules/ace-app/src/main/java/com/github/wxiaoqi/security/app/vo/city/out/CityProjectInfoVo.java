package com.github.wxiaoqi.security.app.vo.city.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:50 2018/11/26
 * @Modified By:
 */
@Data
public class CityProjectInfoVo extends CityInfoVo implements Serializable {
	private static final long serialVersionUID = 6743714576743434138L;
	@ApiModelProperty("项目信息")
	private List<ProjectInfoVo> projectInfoVos;
}
