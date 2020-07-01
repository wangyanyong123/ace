package com.github.wxiaoqi.security.app.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 10:28 2018/12/27
 * @Modified By:
 */
@Data
public class UpdateInfo implements Serializable {

	private static final long serialVersionUID = -5191645823667085951L;
	@ApiModelProperty(value = "用户id")
	private String userId;
	//头像
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
