package com.github.wxiaoqi;

import com.github.wxiaoqi.listener.ApplicationStartup;
import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ace
 * @version 2017/12/26.
 */

@EnableAsync
@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
// 开启事务
@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign", "com.github.wxiaoqi.oss.feign","com.github.wxiaoqi.feign"})
@EnableAceAuthClient
@EnableSwagger2Doc
@MapperScan("com.github.wxiaoqi.*.mapper")
public class ToolBootstrap {
    public static void main(String[] args) {
//        new SpringApplicationBuilder(ToolBootstrap.class).web(true).run(args);
        SpringApplication springApplication = new SpringApplication(ToolBootstrap.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
