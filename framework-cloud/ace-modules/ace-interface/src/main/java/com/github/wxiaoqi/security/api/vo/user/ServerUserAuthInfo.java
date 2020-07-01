package com.github.wxiaoqi.security.api.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * app服务端用户带权限信息
 */
@Data
public class ServerUserAuthInfo extends ServerUserInfo implements Serializable {
	private static final long serialVersionUID = 1100283256032963727L;

	@ApiModelProperty(value = "是否是物业服务人员(0-否，1-是)")
	private String is_service;
	@ApiModelProperty(value = "是否是管家(0-否，1-是)")
	private String is_housekeeper;
	@ApiModelProperty(value = "是否是客服人员(0-否，1-是)")
	private String is_customer;
	@ApiModelProperty(value = "是否是商业人员(0-否，1-是)")
	private String is_business;

}
