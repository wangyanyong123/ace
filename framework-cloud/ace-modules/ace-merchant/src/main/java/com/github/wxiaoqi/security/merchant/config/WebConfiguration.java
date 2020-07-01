package com.github.wxiaoqi.security.merchant.config;

import com.github.wxiaoqi.security.merchant.filter.ChannelFilter;
import com.github.wxiaoqi.security.auth.client.interceptor.ExternalServiceAuthRestInterceptor;
import com.github.wxiaoqi.security.auth.client.interceptor.ServiceAuthRestInterceptor;
import com.github.wxiaoqi.security.auth.client.interceptor.UserAuthRestInterceptor;
import com.github.wxiaoqi.security.common.handler.GlobalExceptionHandler;
import com.github.wxiaoqi.security.common.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器和全局配置
 *
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
        /*
            增加对外api权限拦截器
         */
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
     * 配置服务权限拦截
     *
     * @return
     */
    @Bean
    ServiceAuthRestInterceptor getServiceAuthRestInterceptor() {
        return new ServiceAuthRestInterceptor();
    }

    /**
     * 配置用户用户token拦截
     *
     * @return
     */
    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

    /**
     * 配置对外api token拦截
     *
     * @return
     */
    @Bean
    ExternalServiceAuthRestInterceptor getExternalServiceAuthRestInterceptor() {
        return new ExternalServiceAuthRestInterceptor();
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
}
