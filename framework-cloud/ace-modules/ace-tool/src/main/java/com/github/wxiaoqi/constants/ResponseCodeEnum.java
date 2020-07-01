package com.github.wxiaoqi.constants;

/**
 * 异常
 */
public enum ResponseCodeEnum {
    // 自定义异常
    MYSQL_STORE_UPDATE_FAIL(508,"MySQL库存更新失败"),
    REDIS_STORE_UPDATE_FAIL(508,"Redis库存更新失败"),
    REDIS_STORE_NOT_EXISTS(508,"Redis库存信息不存在"),
    STORE_NOT_EXISTS(508,"数据库存信息不存在"),
    STORE_ALREADY_EXISTS(508,"库存信息已经存在"),
    SECKILL_PRODUCT_REPEAT(507,"秒杀商品重复添加"),
    SECKILL_FAIL(506,"秒杀失败"),
    SECKILL_OVER(505,"秒杀活动结束"),
    TIME_FORMAT_ERROR(504,"结束时间必须>开始时间"),
    NOT_ENOUGH_STOCK(503,"库存不足"),
    PARAMETER_EXCEPTION(502,"参数异常"),
    REdiS_LOCK_SET_FAIL(501,"Redis锁设置失败"),
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
