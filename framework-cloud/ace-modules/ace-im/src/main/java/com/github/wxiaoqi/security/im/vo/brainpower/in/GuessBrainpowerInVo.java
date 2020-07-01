package com.github.wxiaoqi.security.im.vo.brainpower.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: huangxl
 * @Description:
 */
@Data
public class GuessBrainpowerInVo implements Serializable {


	private static final long serialVersionUID = -7089630394342112516L;
	@ApiModelProperty("功能ID列表")
	private List<String> functionIdList;
}
