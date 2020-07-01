package com.github.wxiaoqi.pay.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: guohao
 * @create: 2020-04-11 14:54
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.pay")
public class WechatPayConfig {
    private String paySignKey;
    private String mchId;
    private String certificateKey;
    private String certificateFile;
    private String notifyUrl;
}
