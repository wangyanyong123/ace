package com.github.wxiaoqi.security.auth.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:27 2018/12/26
 * @Modified By:
 */
@Data
public class ExternalUserVo implements Serializable {

	private static final long serialVersionUID = 3337184248732861536L;

	private String id;

	private String name;

	private String appId;

	private String appSecret;

	private String callbackUrl;

	private String remark;

	private String externalUserType;
}
