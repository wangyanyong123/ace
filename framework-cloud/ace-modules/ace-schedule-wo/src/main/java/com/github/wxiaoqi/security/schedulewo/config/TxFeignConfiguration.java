//package com.github.wxiaoqi.security.schedulewo.config;
//
//import com.codingapi.tx.springcloud.feign.TransactionRestTemplateInterceptor;
//import com.github.wxiaoqi.security.auth.client.interceptor.ServiceFeignInterceptor;
//import feign.RequestInterceptor;
//import org.springframework.context.annotation.Bean;
//
///**
// * @author ace
// * @version 2018/1/18.
// */
//public class TxFeignConfiguration {
//    @Bean
//    public RequestInterceptor requestInterceptor(){
//        return new TransactionRestTemplateInterceptor();
//    }
//
//    @Bean
//    public ServiceFeignInterceptor serviceFeignInterceptor(){
//        return new ServiceFeignInterceptor();
//    }
//}
