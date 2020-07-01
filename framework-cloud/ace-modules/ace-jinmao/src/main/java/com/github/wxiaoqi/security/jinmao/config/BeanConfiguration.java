package com.github.wxiaoqi.security.jinmao.config;

import com.github.wxiaoqi.security.common.decision.DecisionHandler;
import com.github.wxiaoqi.security.common.decision.DecisionPassRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author: guohao
 * @date: 2020-06-06 11:05
 **/
@Configuration
public class BeanConfiguration {


    @Value("${app.decision-ordinary-pass-rate}")
    private BigDecimal decisionOrdinaryPassRate;
    @Value("${app.decision-special-pass-rate}")
    private BigDecimal decisionSpecialPassRate;
    @Bean
    public DecisionHandler getDecisionHandler(){
        DecisionPassRate decisionPassRate = new DecisionPassRate();
        decisionPassRate.setDecisionOrdinaryPassRate(decisionOrdinaryPassRate);
        decisionPassRate.setDecisionSpecialPassRate(decisionSpecialPassRate);
        return new DecisionHandler(decisionPassRate);
    }
}
