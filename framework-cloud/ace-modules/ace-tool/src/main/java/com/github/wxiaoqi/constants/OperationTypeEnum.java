package com.github.wxiaoqi.constants;

/**
 * Redis的key命名前缀
 */
public enum  OperationTypeEnum {
    PC_ADD(1,"后台添加"),
    PC_UPDATE(2,"后台更新"),
    SALE_REDIS_OUT(3,"redis下单出库"),
    CONCEL_REDIS_IN(4,"redis取消归还库存"),
    PAY_SUCCESS_DB_OUT(5,"支付成功DB出库");

    private Integer key;

    private String value;

    OperationTypeEnum(Integer key, String value){
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
