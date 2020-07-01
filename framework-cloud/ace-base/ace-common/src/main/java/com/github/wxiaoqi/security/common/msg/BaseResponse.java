package com.github.wxiaoqi.security.common.msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ace on 2017/8/23.
 */
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = -1582401620562643146L;

    @ApiModelProperty("状态 200为正常，其他均为异常")
    private int status = 200;
    @ApiModelProperty("描述")
    private String message = "SUCCEED";

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
