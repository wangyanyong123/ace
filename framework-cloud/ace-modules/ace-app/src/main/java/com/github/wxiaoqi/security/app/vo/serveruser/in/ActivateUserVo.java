package com.github.wxiaoqi.security.app.vo.serveruser.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:40 2018/11/21
 * @Modified By:
 */
@Data
public class ActivateUserVo implements Serializable {
	private static final long serialVersionUID = 4137540026273508520L;
	@ApiModelProperty(value = "手机号" ,required = true ,example = "138****1234")
	private String mobile;
	@ApiModelProperty(value = "密码" ,required = true ,example = "qaz123wsx")
	private String password;
	@ApiModelProperty(value = "验证码" ,required = true ,example = "qaz123wsx")
	private String volidCode;
}
