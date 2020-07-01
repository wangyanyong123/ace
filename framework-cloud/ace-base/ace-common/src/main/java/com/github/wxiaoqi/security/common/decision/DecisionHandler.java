package com.github.wxiaoqi.security.common.decision;

import com.github.wxiaoqi.security.common.constant.AceDictionary;

import java.math.BigDecimal;

/**
 * @author: guohao
 * @date: 2020-06-06 10:22
 **/
public class DecisionHandler {

    private DecisionPassRate decisionPassRate;

    public DecisionHandler(DecisionPassRate decisionPassRate) {
        this.decisionPassRate = decisionPassRate;
    }

    public   BigDecimal getProgressRate(Integer projectHouseCount){
        if(projectHouseCount== null || projectHouseCount <= 0){
            return  BigDecimal.ZERO;
        }
        BigDecimal divide = BigDecimal.ONE.divide(new BigDecimal(projectHouseCount), 4, BigDecimal.ROUND_HALF_UP);
        return divide.compareTo(BigDecimal.ZERO)> 0?divide:new BigDecimal("0.0001");
    }

    public   boolean isPass(Integer eventType,BigDecimal progressRate){
        BigDecimal rate = progressRate.multiply(new BigDecimal("100"));
        if(AceDictionary.DECISION_EVENT_TYPE_ORDINARY.equals(eventType)){
            return rate.compareTo(decisionPassRate.getDecisionOrdinaryPassRate()) >=0;
        }else if(AceDictionary.DECISION_EVENT_TYPE_SPECIAL.equals(eventType)){
            return rate.compareTo(decisionPassRate.getDecisionSpecialPassRate()) >=0;
        }
        return false;

    }
}
