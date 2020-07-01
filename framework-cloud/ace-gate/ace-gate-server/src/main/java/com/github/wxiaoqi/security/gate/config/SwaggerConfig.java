package com.github.wxiaoqi.security.gate.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ace
 * @create 2018/2/7.
 */
@Component
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("管理服务", "/api/admin/v2/api-docs", "2.0"));
//        resources.add(swaggerResource("字典服务", "/api/dict/v2/api-docs", "2.0"));
//        resources.add(swaggerResource("鉴权服务", "/api/auth/v2/api-docs", "2.0"));
        resources.add(swaggerResource("金茂服务", "/api/jinmao/v2/api-docs", "2.0"));
        resources.add(swaggerResource("工具服务", "/api/tool/v2/api-docs", "2.0"));
        resources.add(swaggerResource("app服务", "/api/app/v2/api-docs", "2.0"));
        resources.add(swaggerResource("即时通信服务", "/api/im/v2/api-docs", "2.0"));
        resources.add(swaggerResource("对外提供接口服务", "/api/external/v2/api-docs", "2.0"));
        resources.add(swaggerResource("报表服务", "/api/report/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
