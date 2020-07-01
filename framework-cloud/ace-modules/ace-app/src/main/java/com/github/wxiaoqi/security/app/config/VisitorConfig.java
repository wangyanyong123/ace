package com.github.wxiaoqi.security.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "visitor")
public class VisitorConfig {

    private String effectiveTime;
    private String failureTime;
    private String number;
}
