package com.github.wxiaoqi.security.plan.aop;

import com.github.wxiaoqi.security.plan.annotation.DataSource;
import com.github.wxiaoqi.security.plan.config.DynamicDataSourceContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:57 2019/2/26
 * @Modified By:
 */
public class DynamicDataSourceAnnotationInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAnnotationInterceptor.class);


    private static final Map<Method, String> METHOD_CACHE = new HashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            String datasource = determineDatasource(invocation);
            if (! DynamicDataSourceContextHolder.containsDataSource(datasource)) {
                logger.info("数据源[{}]不存在，使用默认数据源 >", datasource);
            }
            DynamicDataSourceContextHolder.setDataSourceRouterKey(datasource);
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.removeDataSourceRouterKey();
        }
    }

    private String determineDatasource(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (METHOD_CACHE.containsKey(method)) {
            return METHOD_CACHE.get(method);
        } else {
            DataSource ds = method.isAnnotationPresent(DataSource.class) ? method.getAnnotation(DataSource.class)
                    : AnnotationUtils.findAnnotation(method.getDeclaringClass(), DataSource.class);
            METHOD_CACHE.put(method, ds.value());
            return ds.value();
        }
    }

}