package com.github.wxiaoqi.security.merchant.constants;

public enum ResponseCodeEnum {
    ORDER_DELIVERY(513,"订单已发货"),
    ORDER_HANDLE(512,"订单已受理"),
    MOBILE_PHONE_BIND_FAIL(511,"号码绑定失败"),
    USER_INFO_ERROR(510,"未找到用户信息"),
    NEW_OLD_PASSWORD_ERROR(509,"新密码与旧密码不能相同"),
    USER_PHOTO_UPLOAD_ERROR(508,"头像上传失败"),
    OLD_PASSWORD_ERROR(507,"旧密码错误"),
    ORDER_STATUS_ERROR(506,"订单状态有误"),
    NOT_FOUND_ORDER_DETAIL(505,"订单详情信息不存在"),
    NOT_FOUND_ORDER(504,"订单信息不存在"),
    NOT_FOUND_EXPRESS_COMPANY(503,"快递公司不存在"),
    NOT_LOGIN(502,"用户未登陆，请登陆系统"),
    PARAMETER_EXCEPTION(501,"参数异常"),
    UNKNOWN_EXCEPTION(500,"未知异常");

    private Integer key;

    private String value;

    ResponseCodeEnum(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
