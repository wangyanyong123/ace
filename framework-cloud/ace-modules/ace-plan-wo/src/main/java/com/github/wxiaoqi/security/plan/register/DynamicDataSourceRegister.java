package com.github.wxiaoqi.security.plan.register;

import com.github.wxiaoqi.security.plan.config.DynamicDataSourceContextHolder;
import com.github.wxiaoqi.security.plan.config.DynamicRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 17:57 2019/2/26
 * @Modified By:
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    private Environment evn;

    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    static {
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }

    private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();

    private Binder binder;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map config, defauleDataSourceProperties;
        defauleDataSourceProperties = binder.bind("spring.datasource.master", Map.class).get();
        String typeStr = evn.getProperty("spring.datasource.master.type");
        Class<? extends DataSource> clazz = getDataSourceType(typeStr);
        DataSource consumerDatasource, defaultDatasource = bind(clazz, defauleDataSourceProperties);
        DynamicDataSourceContextHolder.dataSourceIds.add("master");
        List<Map> configs = binder.bind("spring.datasource.cluster", Bindable.listOf(Map.class)).get();
        for (int i = 0; i < configs.size(); i++) {
            config = configs.get(i);
            clazz = getDataSourceType((String) config.get("type"));
            defauleDataSourceProperties = config;
            consumerDatasource = bind(clazz, defauleDataSourceProperties);
            String key = config.get("key").toString();
            customDataSources.put(key, consumerDatasource);
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }
        GenericBeanDefinition define = new GenericBeanDefinition();
        define.setBeanClass(DynamicRoutingDataSource.class);
        MutablePropertyValues mpv = define.getPropertyValues();
        mpv.add("defaultTargetDataSource", defaultDatasource);
        mpv.add("targetDataSources", customDataSources);
        beanDefinitionRegistry.registerBeanDefinition("datasource", define);
    }

    private Class<? extends DataSource> getDataSourceType(String typeStr) {
        Class<? extends DataSource> type;
        try {
            if (StringUtils.hasLength(typeStr)) {
                type = (Class<? extends DataSource>) Class.forName(typeStr);
            } else {
                type = HikariDataSource.class;
            }
            return type;
        } catch (Exception e) {
            throw new IllegalArgumentException("can not resolve class with type: " + typeStr);
        }
    }

    private void bind(DataSource result, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }

    private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
    }

    private <T extends DataSource> T bind(Class<T> clazz, String sourcePath) {
        Map properties = binder.bind(sourcePath, Map.class).get();
        return bind(clazz, properties);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.evn = environment;
        binder = Binder.get(evn);
    }

}
