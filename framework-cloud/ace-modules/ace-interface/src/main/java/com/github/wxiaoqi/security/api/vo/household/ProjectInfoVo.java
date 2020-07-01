package com.github.wxiaoqi.security.api.vo.household;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:06 2018/12/5
 * @Modified By:
 */
@Data
public class ProjectInfoVo implements Serializable {
	private static final long serialVersionUID = 3235593161859933104L;
	@ApiModelProperty("社区id")
	private String projectId;
	@ApiModelProperty("社区名")
	private String projectName;
}