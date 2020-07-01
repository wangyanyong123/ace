package com.github.wxiaoqi.security.app.vo.integral;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class RuleInfo implements Serializable {

    @ApiModelProperty(value = "积分规则id")
    private String id;
    @ApiModelProperty(value = "积分规则名称")
    private String ruleName;
    @ApiModelProperty(value = "积分规则说明")
    private String ruleDesc;
    @ApiModelProperty(value = "积分值")
    private int creditsValue;
    @ApiModelProperty(value = "积分日上限(-1:不限制)")
    private int creditsUpperDay;
    @ApiModelProperty(value = "积分月上限(-1:不限制)")
    private int creditsUpperMonth;
    @ApiModelProperty(value = "积分总上限(-1:不限制)")
    private int creditsUpperTotal;
    @ApiModelProperty(value = "适用维度(1-个人，2-小组)")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public int getCreditsValue() {
        return creditsValue;
    }

    public void setCreditsValue(int creditsValue) {
        this.creditsValue = creditsValue;
    }

    public int getCreditsUpperDay() {
        return creditsUpperDay;
    }

    public void setCreditsUpperDay(int creditsUpperDay) {
        this.creditsUpperDay = creditsUpperDay;
    }

    public int getCreditsUpperMonth() {
        return creditsUpperMonth;
    }

    public void setCreditsUpperMonth(int creditsUpperMonth) {
        this.creditsUpperMonth = creditsUpperMonth;
    }

    public int getCreditsUpperTotal() {
        return creditsUpperTotal;
    }

    public void setCreditsUpperTotal(int creditsUpperTotal) {
        this.creditsUpperTotal = creditsUpperTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
