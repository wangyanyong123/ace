package com.github.wxiaoqi.security.im.tio.msg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: huangxl
 * @Description:
 */
@Data
public class BrainMessageIn implements Serializable {
	private static final long serialVersionUID = -6961603516019671267L;

	@ApiModelProperty(value = "类型(1-问题，2-功能，3-仅仅是文字)",required = true)
	private String type;
	@ApiModelProperty(value = "id，问题/功能id",required = false)
	private String id;
	@ApiModelProperty(value = "问题/功能/输入的文字",required = true)
	private String text;

}
