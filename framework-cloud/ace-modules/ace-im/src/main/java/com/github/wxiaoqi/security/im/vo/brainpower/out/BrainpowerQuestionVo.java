package com.github.wxiaoqi.security.im.vo.brainpower.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Author: huangxl
 * @Description:
 */
@Data
public class BrainpowerQuestionVo implements Serializable {

	private static final long serialVersionUID = -9045124784927003617L;

	@ApiModelProperty("问题ID")
	private String questionId;
	@ApiModelProperty("问题描述")
	private String question;
}
