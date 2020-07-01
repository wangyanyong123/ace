package com.github.wxiaoqi.security.auth.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:02 2018/12/25
 * @Modified By:
 */
@Data
public class ApiHeadBean implements Serializable {
	private static final long serialVersionUID = -2024538067982647382L;
	private String appId;
	private String sign;
	private String timestamp;
}
