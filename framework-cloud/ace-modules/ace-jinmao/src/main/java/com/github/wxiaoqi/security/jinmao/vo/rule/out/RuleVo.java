package com.github.wxiaoqi.security.jinmao.vo.rule.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class RuleVo implements Serializable {

    @ApiModelProperty(value = "积分规则id")
    private String id;
    @ApiModelProperty(value = "积分规则编码(累积以A开始，消费以C开始，如C001）")
    private String ruleCode;
    @ApiModelProperty(value = "积分规则名称")
    private String ruleName;
    @ApiModelProperty(value = "积分规则说明")
    private String ruleDesc;
    @ApiModelProperty(value = "积分值")
    private int creditsValue;
    @ApiModelProperty(value = "积分日上限(-1:不限制)")
    private String creditsUpperDay;
    @ApiModelProperty(value = "积分月上限(-1:不限制)")
    private String creditsUpperMonth;
    @ApiModelProperty(value = "积分总上限(-1:不限制)")
    private String creditsUpperTotal;
    @ApiModelProperty(value = "启用状态(1-草稿，2-已启用，3-已停用)")
    private String ruleStatus;
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

    public int getCreditsValue() {
        return creditsValue;
    }

    public void setCreditsValue(int creditsValue) {
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

    public String getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
