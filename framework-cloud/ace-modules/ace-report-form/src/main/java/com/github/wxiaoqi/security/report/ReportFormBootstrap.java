package com.github.wxiaoqi.security.report;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ace
 * @version 2017/12/26.
 */
@EnableEurekaClient
@SpringBootApplication
// 开启事务
//@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign","com.github.wxiaoqi.security.report.feign"})
@MapperScan("com.github.wxiaoqi.security.report.mapper")
@EnableAceAuthClient
@EnableSwagger2Doc
public class ReportFormBootstrap {
    //1、认证量是用户是否有房间，删除的用户算未认证
    //2、做乐观锁
    //3、昨天数据只更新一遍
    public static void main(String[] args) {
        new SpringApplicationBuilder(ReportFormBootstrap.class).web(true).run(args);    }
}
