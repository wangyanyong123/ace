package com.github.wxiaoqi.wechat.config;

import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.wxa.WeixinAppFacade;
import com.github.wxiaoqi.wechat.biz.RedisTokenBiz;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: guohao
 * @create: 2020-04-09 15:12
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.small")
public class WechatSmallConfig {
    private String appId;
    private String secret;

    @Bean
    public WeixinAppFacade weixinAppFacade(RedisTokenBiz redisTokenBiz) {
        WeixinAccount weixinAccount = new WeixinAccount(appId, secret);
        return new WeixinAppFacade(weixinAccount, redisTokenBiz);
    }


}
