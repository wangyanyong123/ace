package com.github.wxiaoqi.security.jinmao;

import com.ace.cache.EnableAceCache;
import com.github.wxiaoqi.merge.EnableAceMerge;
import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zxl
 * @version 2018/11/14.
 */
@EnableEurekaClient
@SpringBootApplication
// 开启事务
//@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign"
        ,"com.github.wxiaoqi.security.jinmao.feign","com.github.wxiaoqi.job.feign"})
@MapperScan("com.github.wxiaoqi.security.jinmao.mapper")
@EnableAceAuthClient
@EnableSwagger2Doc
@EnableAceCache
@EnableAceMerge
@EnableScheduling
public class JinMaoBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(JinMaoBootstrap.class).web(true).run(args);    }
}
