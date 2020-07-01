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
public class BrainpowerFunctionVo implements Serializable {


	private static final long serialVersionUID = -629021578292419022L;
	@ApiModelProperty("功能点id")
	private String functionId;
	@ApiModelProperty("功能点名称")
	private String functionPoint;
}
