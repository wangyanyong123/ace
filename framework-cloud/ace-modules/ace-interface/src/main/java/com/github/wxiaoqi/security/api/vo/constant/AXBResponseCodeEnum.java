package com.github.wxiaoqi.security.api.vo.constant;

public enum AXBResponseCodeEnum {
    // 百度云平台
    SUCCESS(0,"success"),
    EXPIRED_OR_NOT_EXIST(9009,"绑定关系过期或不存在"),
    NUMBER_BOUND(10003,"号码已有相关绑定关系"),
    // 自定义异常
    COMPANY_MOBILE_BUSY(502,"服务占线，请稍后再试"),
    PARAMETER_EXCEPTION(501,"参数异常"),
    UNKNOWN_EXCEPTION(500,"未知异常");

    private Integer key;

    private String value;

    AXBResponseCodeEnum(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
