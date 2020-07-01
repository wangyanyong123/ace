package com.github.wxiaoqi.security.merchant.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 密码修改
 */
@Data
public class ChangePasswordVo implements Serializable {
	private static final long serialVersionUID = 7732362532464848447L;
	@NotEmpty(message = "新密码参数空")
	@ApiModelProperty(value = "新密码" ,required = true ,example = "123456")
	private String newPassword;
	@NotEmpty(message = "旧密码参数空")
	@ApiModelProperty(value = "旧密码" ,required = true ,example = "654321")
	private String oldPassword;
}
