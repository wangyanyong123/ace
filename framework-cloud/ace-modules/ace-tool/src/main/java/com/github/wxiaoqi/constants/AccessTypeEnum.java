package com.github.wxiaoqi.constants;

/**
 * 出入库类型
 */
public enum AccessTypeEnum {
    OUT_SALE(1,"销售出库"),
    IN_CANCEL(2,"订单取消入库"),
    IN_SECKILL(3,"秒杀结束入库"),
    IN_BACK(4,"退单入库"),
    JOIN_SECKILL(5,"参与秒杀出库"),
    ADD_NEW_STORE(6,"新商品入库");



    public static String getValue(Integer key){
        for (AccessTypeEnum reasonEnum : AccessTypeEnum.values()) {
            if(reasonEnum.getKey().equals(key)){
                return reasonEnum.getValue();
            }
        }
        return null;
    }

    private Integer key;

    private String value;

    AccessTypeEnum(Integer key, String value){
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
