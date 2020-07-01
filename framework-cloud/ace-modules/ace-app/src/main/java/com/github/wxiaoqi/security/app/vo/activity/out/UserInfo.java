package com.github.wxiaoqi.security.app.vo.activity.out;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UserInfo implements Serializable {

    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "用户电话")
    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
