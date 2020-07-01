package com.github.wxiaoqi.security.app.vo.userproject.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:32 2018/11/23
 * @Modified By:
 */
@Data
public class ProjectInfoVo implements Serializable {
	private static final long serialVersionUID = 4227312701321678512L;
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("社区id")
	private String projectId;
	@ApiModelProperty("社区名")
	private String projectName;
}
