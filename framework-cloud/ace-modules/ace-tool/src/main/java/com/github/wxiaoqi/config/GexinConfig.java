package com.github.wxiaoqi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description: gexin配置
 * @Date: Created in 17:42 2018/11/20
 * @Modified By:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gexin")
public class GexinConfig implements Serializable {

	private static final long serialVersionUID = 5236684726067310629L;

	private String host;
	//client
	private String appIdClient;
	private String appkeyClient;
	private String masterClient;
	//service
	private String appIdService;
	private String appkeyService;
	private String masterService;


}
