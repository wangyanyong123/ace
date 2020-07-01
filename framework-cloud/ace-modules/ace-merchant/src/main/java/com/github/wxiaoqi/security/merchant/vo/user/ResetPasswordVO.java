package com.github.wxiaoqi.security.merchant.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 重置密码参数
 */
@Data
public class ResetPasswordVO implements Serializable {
	private static final long serialVersionUID = -8011313895286251120L;
	@NotEmpty(message="缺少参数newPassword")
	@ApiModelProperty(value = "新密码" ,required = true ,example = "adewf23")
	private String newPassword;
	@NotEmpty(message="缺少参数mobilePhone")
	@ApiModelProperty(value = "手机号" ,required = true ,example = "13589467842")
	private String mobilePhone;
	@NotEmpty(message="缺少参数volidCode")
	@ApiModelProperty(value = "验证码" ,required = true ,example = "1234")
	private String volidCode;
}
