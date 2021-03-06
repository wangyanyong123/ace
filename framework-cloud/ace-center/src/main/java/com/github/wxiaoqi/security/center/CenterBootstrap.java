package com.github.wxiaoqi.security.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-05-25 12:44
 */
//启动一个服务注册中心提供给其他应用进行对话
@EnableEurekaServer
@SpringBootApplication
public class CenterBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(CenterBootstrap.class, args);
    }
}
