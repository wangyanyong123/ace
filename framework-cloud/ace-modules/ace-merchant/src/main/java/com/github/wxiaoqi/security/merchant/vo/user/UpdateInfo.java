package com.github.wxiaoqi.security.merchant.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户信息
 */
@Data
public class UpdateInfo implements Serializable {

	private static final long serialVersionUID = -5191645823667085951L;
	@ApiModelProperty(value = "用户id")
	private String userId;
	//头像
	@NotEmpty(message = "缺少头像信息")
	@ApiModelProperty(value = "头像")
	private String profilePhoto;

	@ApiModelProperty(value = "昵称,服务端不用传")
	private String nickname;

	@ApiModelProperty(value = "姓名,服务端不用传")
	private String name;

	@ApiModelProperty(value = "生日")
	private String birthday;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "性别：0、未知；1、男；2、女")
	private String sex;
}
