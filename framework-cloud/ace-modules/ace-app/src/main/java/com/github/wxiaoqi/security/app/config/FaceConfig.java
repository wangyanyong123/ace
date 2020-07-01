package com.github.wxiaoqi.security.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "face")
public class FaceConfig {

    private String uploadUrl;

    private String deleteUrl;

    private String getLogUrl;

    private String appId;

    private String appSecret;
}
