package com.github.wxiaoqi.security.jinmao.vo.rule.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveRuleParam implements Serializable {


    @ApiModelProperty(value = "积分规则id")
    private String id;
    private String ruleCode;
    @ApiModelProperty(value = "积分规则名称")
    private String ruleName;
    @ApiModelProperty(value = "积分规则说明")
    private String ruleDesc;
    @ApiModelProperty(value = "积分值")
    private String creditsValue;
    @ApiModelProperty(value = "积分日上限(-1:不限制)")
    private String creditsUpperDay;
    @ApiModelProperty(value = "积分月上限(-1:不限制)")
    private String creditsUpperMonth;
    @ApiModelProperty(value = "积分总上限(-1:不限制)")
    private String creditsUpperTotal;
    @ApiModelProperty(value = "适用维度(1-个人，2-小组)")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
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

    public String getCreditsValue() {
        return creditsValue;
    }

    public void setCreditsValue(String creditsValue) {
        this.creditsValue = creditsValue;
    }

    public String getCreditsUpperDay() {
        return creditsUpperDay;
    }

    public void setCreditsUpperDay(String creditsUpperDay) {
        this.creditsUpperDay = creditsUpperDay;
    }

    public String getCreditsUpperMonth() {
        return creditsUpperMonth;
    }

    public void setCreditsUpperMonth(String creditsUpperMonth) {
        this.creditsUpperMonth = creditsUpperMonth;
    }

    public String getCreditsUpperTotal() {
        return creditsUpperTotal;
    }

    public void setCreditsUpperTotal(String creditsUpperTotal) {
        this.creditsUpperTotal = creditsUpperTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
