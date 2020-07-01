package com.github.wxiaoqi.security.app.vo.city.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:53 2018/11/26
 * @Modified By:
 */
@Data
public class ProjectInfoVo implements Serializable {
	private static final long serialVersionUID = -6913948813545277104L;
	@ApiModelProperty("社区id")
	private String projectId;
	@ApiModelProperty("社区名")
	private String projectName;
}
