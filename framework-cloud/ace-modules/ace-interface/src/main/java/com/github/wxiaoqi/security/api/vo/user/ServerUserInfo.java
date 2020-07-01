package com.github.wxiaoqi.security.api.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * app服务端用户信息
 */
@Data
public class ServerUserInfo implements Serializable {
	private static final long serialVersionUID = 1100283256032963727L;

	@ApiModelProperty(value = "用户id")
	private String userId;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "用户头像")
	private String profilePhoto;

}
