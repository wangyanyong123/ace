package com.github.wxiaoqi.security.external.config;

import com.github.wxiaoqi.security.auth.client.interceptor.ExternalServiceAuthRestInterceptor;
import com.github.wxiaoqi.security.auth.client.interceptor.ServiceAuthRestInterceptor;
import com.github.wxiaoqi.security.auth.client.interceptor.UserAuthRestInterceptor;
import com.github.wxiaoqi.security.common.handler.GlobalExceptionHandler;
import com.github.wxiaoqi.security.common.xss.XssFilter;
import com.github.wxiaoqi.security.external.filter.ChannelFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 拦截器和全局配置
 * @author ace
 * @version 2017/9/8
 */
@Configuration("securityWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
            增加服务权限烂机器
         */
        registry.addInterceptor(getServiceAuthRestInterceptor()).addPathPatterns("/**");
        /*
            增加用户权限拦截器
         */
        registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/**");

        registry.addInterceptor(getExternalServiceAuthRestInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean signValidateFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        ChannelFilter signValidateFilter = new ChannelFilter();
        registration.setFilter(signValidateFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        XssFilter xssFilter = new XssFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(xssFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    /**
     * 配置服务权限拦截
     * @return
     */
    @Bean
    ServiceAuthRestInterceptor getServiceAuthRestInterceptor() {
        return new ServiceAuthRestInterceptor();
    }

    /**
     * 配置用户用户token拦截
     * @return
     */
    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

    /**
     * 配置用户用户token拦截
     * @return
     */
    @Bean
    ExternalServiceAuthRestInterceptor getExternalServiceAuthRestInterceptor() {
        return new ExternalServiceAuthRestInterceptor();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
