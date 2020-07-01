package com.github.wxiaoqi.security.common.decision;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: guohao
 * @date: 2020-06-06 10:48
 **/
@Data
public class DecisionPassRate {

    private BigDecimal decisionOrdinaryPassRate;
    private BigDecimal decisionSpecialPassRate;
}
