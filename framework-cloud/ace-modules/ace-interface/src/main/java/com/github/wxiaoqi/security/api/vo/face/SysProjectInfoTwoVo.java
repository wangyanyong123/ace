package com.github.wxiaoqi.security.api.vo.face;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:13 2018/12/25
 * @Modified By:
 */
@Data
public class SysProjectInfoTwoVo implements Serializable {
	private static final long serialVersionUID = 1960180675396903171L;
	@ApiModelProperty("社区id")
	private String projectId;
	@ApiModelProperty("社区名")
	private String projectName;
	@ApiModelProperty("地块列表")
	private List<LandInfoVo> landList;
}
