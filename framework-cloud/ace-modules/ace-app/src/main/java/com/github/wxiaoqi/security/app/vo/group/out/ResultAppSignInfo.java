package com.github.wxiaoqi.security.app.vo.group.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResultAppSignInfo implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "打卡日期")
    private String signDate;

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
}
