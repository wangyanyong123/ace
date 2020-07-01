package com.github.wxiaoqi.security.app.vo.integral;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class IntegralPersonalVo implements Serializable {

    @ApiModelProperty(value = "规则名称")
    private String ruleName;
    @ApiModelProperty(value = "积分值")
    private String creditsValue;
    @ApiModelProperty(value = "积分获取时间")
    private String createTime;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getCreditsValue() {
        return creditsValue;
    }

    public void setCreditsValue(String creditsValue) {
        this.creditsValue = creditsValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
