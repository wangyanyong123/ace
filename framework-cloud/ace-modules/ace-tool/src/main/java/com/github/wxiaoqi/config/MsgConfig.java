package com.github.wxiaoqi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 18:58 2018/11/20
 * @Modified By:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "msg")
public class MsgConfig implements Serializable {
	private static final long serialVersionUID = 156527101969232680L;
	private String url;
	private String softwareSerialNo;
	private String key;
}
