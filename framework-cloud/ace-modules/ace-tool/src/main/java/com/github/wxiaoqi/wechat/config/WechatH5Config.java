package com.github.wxiaoqi.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: guohao
 * @create: 2020-04-09 15:12
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.h5")
public class WechatH5Config {
    private String appId;
    private String secret;

}
