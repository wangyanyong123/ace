package com.github.wxiaoqi.security.app.axb.constants;

public enum AXBEndTypeEnum {
    PLATFORM(0,"平台"),
    CALLER(1,"主叫"),
    CALLED(2,"被叫");



    public static String getValue(Integer key){
        for (AXBEndTypeEnum reasonEnum : AXBEndTypeEnum.values()) {
            if(reasonEnum.getKey().equals(key)){
                return reasonEnum.getValue();
            }
        }
        return null;
    }

    private Integer key;

    private String value;

    AXBEndTypeEnum(Integer key, String value){
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
