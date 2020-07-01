package com.github.wxiaoqi.security.common.msg;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Ace on 2017/6/11.
 */
public class ObjectRestResponse<T> extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -1403890896426609519L;
    @ApiModelProperty("数据")
    T data;

    public static ObjectRestResponse ok(Object data) {
        return new ObjectRestResponse<Object>().data(data);
    }

    public static ObjectRestResponse ok() {
        return new ObjectRestResponse<Object>();
    }

    public boolean success() {
        return 200 == getStatus();
    }

    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
