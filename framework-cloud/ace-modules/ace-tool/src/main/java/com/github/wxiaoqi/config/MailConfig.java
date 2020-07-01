package com.github.wxiaoqi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 9:38 2018/11/21
 * @Modified By:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig implements Serializable {
	private static final long serialVersionUID = -4639705037788841237L;

	private String fromEmailAddr;

	private String smtp;

	private String port;

	private String username;

	private String password;

	private String fromEmailAddrNickName;
}
