package com.github.wxiaoqi.security.app.oauth.config;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 尚未使用
 * 原因：加入后会导致feign 远程调用被oauth2拦截，
 *      feign 远程调用token 没有设置成功，见ace-gate-server AdminAccessFilter
 */
//@Component
@Data
public class AuthIgnoreConfig {

    @Autowired
    private WebApplicationContext applicationContext;

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final String ASTERISK = "*";

    private List<String> ignoreUrls = new ArrayList<>();

//    @PostConstruct
    public void init() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        ignoreUrls.add("/oauth/**");
        map.keySet().forEach(mappingInfo -> {
            HandlerMethod handlerMethod = map.get(mappingInfo);
            IgnoreUserToken method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), IgnoreUserToken.class);
            Optional.ofNullable(method)
                    .ifPresent(authIgnore -> mappingInfo
                            .getPatternsCondition()
                            .getPatterns()
//                            .forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK))));
                            .forEach(url -> {
                                System.out.println(url);
                                ignoreUrls.add(url);
                            }));
            IgnoreUserToken controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), IgnoreUserToken.class);
            Optional.ofNullable(controller)
                    .ifPresent(authIgnore -> mappingInfo
                            .getPatternsCondition()
                            .getPatterns()
                            .forEach(url -> {
                                System.out.println(url);
                                ignoreUrls.add(url);
                            }));
        });
    }

}
