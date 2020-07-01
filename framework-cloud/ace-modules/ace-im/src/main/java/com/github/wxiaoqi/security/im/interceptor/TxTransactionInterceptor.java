//package com.github.wxiaoqi.security.im.interceptor;
//
//import com.codingapi.tx.springcloud.interceptor.TxManagerInterceptor;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
///**
// * Created by lorne on 2017/6/28.
// */
//@Aspect
//@Component
//public class TxTransactionInterceptor implements Ordered {
//
//
//    @Override
//    public int getOrder() {
//        return 1;
//    }
//
//    @Autowired
//    private TxManagerInterceptor txManagerInterceptor;
//
//    @Around("execution(* com.github.wxiaoqi.security.im.biz.*Biz.*(..))")
//    public Object around(ProceedingJoinPoint point)throws Throwable{
//        return txManagerInterceptor.around(point);
//    }
//}
