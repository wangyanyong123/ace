package com.github.wxiaoqi.security.app.config;

import com.github.wxiaoqi.security.common.decision.DecisionHandler;
import com.github.wxiaoqi.security.common.decision.DecisionPassRate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * 用于装配app项目默认配置
 *
 * @author: guohao
 * @create: 2020-04-16 15:37
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppDefaultConfig {

    private String defaultProjectId;

    private BigDecimal decisionOrdinaryPassRate;

    private BigDecimal decisionSpecialPassRate;


    @Bean
    public DecisionHandler getDecisionHandler(){
        DecisionPassRate decisionPassRate = new DecisionPassRate();
        decisionPassRate.setDecisionOrdinaryPassRate(decisionOrdinaryPassRate);
        decisionPassRate.setDecisionSpecialPassRate(decisionSpecialPassRate);
        return new DecisionHandler(decisionPassRate);
    }

}
