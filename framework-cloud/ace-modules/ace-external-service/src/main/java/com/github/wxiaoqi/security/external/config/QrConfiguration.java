package com.github.wxiaoqi.security.external.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 15:51 2019/1/25
 * @Modified By:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qr")
public class QrConfiguration implements Serializable {
	private static final long serialVersionUID = -2565094372759521126L;

	private int refreshTime;
}
