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
public class ChangeBrainpowerInVo implements Serializable {


	private static final long serialVersionUID = -7089630394342112516L;
	@ApiModelProperty("问题ID，当为猜你所想换一批时可为空值")
	private String questionId;
	@ApiModelProperty("问题ID列表")
	private List<String> questionIdList;
}
