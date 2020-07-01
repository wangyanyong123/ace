package com.github.wxiaoqi.security.im.vo.brainpower.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: huangxl
 * @Description:
 */
@Data
public class OperateVo implements Serializable {

	private static final long serialVersionUID = -9045124784927003617L;

	@ApiModelProperty("消息ID")
	private String msgId;
	@ApiModelProperty("操作类型(1-已解决，2-未解决)")
	private String operateType;
}
