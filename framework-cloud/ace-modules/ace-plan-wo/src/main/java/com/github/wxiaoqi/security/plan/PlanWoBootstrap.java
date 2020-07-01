package com.github.wxiaoqi.security.plan;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.github.wxiaoqi.security.plan.aop.DynamicDataSourceAnnotationAdvisor;
import com.github.wxiaoqi.security.plan.aop.DynamicDataSourceAnnotationInterceptor;
import com.github.wxiaoqi.security.plan.register.DynamicDataSourceRegister;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lzx
 * @version 2019/02/26.
 */
@Import(DynamicDataSourceRegister.class)
@EnableEurekaClient
@SpringBootApplication
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign","com.github.wxiaoqi.security.plan.feign"})
@MapperScan("com.github.wxiaoqi.security.plan.mapper")
@EnableAceAuthClient
@EnableSwagger2Doc
@EnableTransactionManagement
public class PlanWoBootstrap {
    public static void main(String[] args) {
		SpringApplication.run(PlanWoBootstrap.class, args);
	}
	@Bean
	public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
		return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
	}
}
