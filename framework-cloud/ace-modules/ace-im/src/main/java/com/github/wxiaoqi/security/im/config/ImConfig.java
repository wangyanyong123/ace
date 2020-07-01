package com.github.wxiaoqi.security.im.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:26 2018/12/14
 * @Modified By:
 */
@Component
@ConfigurationProperties(prefix="im")
@Data
public class ImConfig {
	private String tioServerIp;

	private String tioServerPort;

	private Long tioTimeout;
}
