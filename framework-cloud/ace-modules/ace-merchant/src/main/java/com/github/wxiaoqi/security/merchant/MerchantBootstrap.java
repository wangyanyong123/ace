package com.github.wxiaoqi.security.merchant;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ace
 * @version 2017/12/26.
 */
@EnableEurekaClient
@SpringBootApplication
// 开启事务
@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign","com.github.wxiaoqi.security.merchant.fegin","com.github.wxiaoqi.job.feign"})
@MapperScan({"com.github.wxiaoqi.security.merchant.mapper","com.github.wxiaoqi.job.mapper"})
@EnableAceAuthClient
@EnableSwagger2Doc
@EnableResourceServer
@EnableAuthorizationServer

@ComponentScan(basePackages = {"com.github.wxiaoqi.security.merchant","com.github.wxiaoqi.security.merchant.job"})

public class MerchantBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MerchantBootstrap.class).web(true).run(args);
    }
}
