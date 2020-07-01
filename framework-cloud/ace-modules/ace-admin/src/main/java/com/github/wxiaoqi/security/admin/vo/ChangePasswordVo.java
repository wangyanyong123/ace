package com.github.wxiaoqi.security.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:48 2018/11/26
 * @Modified By:
 */
@Data
public class ChangePasswordVo implements Serializable {
	private static final long serialVersionUID = 7732362532464848447L;
	@ApiModelProperty(value = "新密码" ,required = true ,example = "adewf23")
	private String newPassword;
	@ApiModelProperty(value = "旧密码" ,required = true ,example = "qwadewf23")
	private String oldPassword;
}
