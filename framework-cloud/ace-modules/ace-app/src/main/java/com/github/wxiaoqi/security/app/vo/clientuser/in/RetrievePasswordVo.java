package com.github.wxiaoqi.security.app.vo.clientuser.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:42 2018/12/12
 * @Modified By:
 */
@Data
public class RetrievePasswordVo implements Serializable {
	private static final long serialVersionUID = -8011313895286251120L;
	@ApiModelProperty(value = "新密码" ,required = true ,example = "adewf23")
	private String newPassword;
	@ApiModelProperty(value = "手机号" ,required = true ,example = "13589467842")
	private String mobilePhone;
	@ApiModelProperty(value = "验证码" ,required = true ,example = "1234")
	private String volidCode;
}
