package com.github.wxiaoqi.security.app.vo.group.in;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SaveIntegralInfo implements Serializable {

    @ApiModelProperty(value = "规则编码")
    private String ruleCode;
    @ApiModelProperty(value = "小组id(若是小组相关成员获取,则传)")
    private String groupId;
    @ApiModelProperty(value = "对象id(若是跟业务相关,则传)")
    private String objectId;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
