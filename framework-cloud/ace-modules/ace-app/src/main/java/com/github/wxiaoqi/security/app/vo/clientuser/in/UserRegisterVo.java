package com.github.wxiaoqi.security.app.vo.clientuser.in;

import io.swagger.annotations.ApiImplicitParam;
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
public class UserRegisterVo implements Serializable {
	private static final long serialVersionUID = 8345554252829655576L;
	@ApiModelProperty(value = "手机号" ,required = true ,example = "138****1234")
	private String mobile;
	@ApiModelProperty(value = "密码" ,required = true ,example = "qaz123wsx")
	private String password;
	@ApiModelProperty(value = "验证码" ,required = true ,example = "qaz123wsx")
	private String volidCode;
	@ApiModelProperty(value = "昵称" ,required = false ,example = "张三")
	private String nickname;
	@ApiModelProperty(value = "社区id" ,required = false ,example = "132153gsafdgfg543")
	private String projectId;
	@ApiModelProperty(value = "操作系统 1、android；2、ios" ,required = false ,example = "1")
	private String registOs;
}
