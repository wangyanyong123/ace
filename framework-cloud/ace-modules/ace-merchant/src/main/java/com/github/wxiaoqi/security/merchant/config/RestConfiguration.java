package com.github.wxiaoqi.security.merchant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:33 2018/12/17
 * @Modified By:
 */
@Configuration
public class RestConfiguration {
	@Autowired
	RestTemplateBuilder builder;

	@Bean
	public RestTemplate restTemplate(){
		return builder.build();
	}
}