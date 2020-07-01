package com.github.wxiaoqi.security.app.axb.constants;

public enum AXBEndStateEnum {
    OVER_1(1,"通话结束"),
    OVER_2(2,"通话结束"),
    OVER_3(3,"通话结束"),
    OVER_18(18,"通话结束"),
    OVER_30(30,"通话结束"),
    NO_ANSWER_4(4,"用户无应答"),
    NO_ANSWER_17(17,"用户无应答"),
    NO_ANSWER_19(19,"用户无应答"),
    USER_BUSY(5,"用户忙"),
    USER_ABSENT_6(6,"用户缺席"),
    USER_ABSENT_20(20,"用户缺席"),
    ROUTE_FAIL_7(7,"路由失败"),
    ROUTE_FAIL_16(16,"路由失败"),
    ROUTE_FAIL_31(31,"路由失败"),
    ROUTE_FAIL_32(32,"路由失败"),
    USER_CLOSEDOWN_11(11,"用户关机"),
    USER_CLOSEDOWN_54(54,"用户关机"),
    USER_SHUTDOWN(12,"用户停机"),
    USER_REFUSE_13(13,"用户拒接"),
    USER_REFUSE_21(21,"用户拒接"),
    USER_NO_NUMBER(14,"用户空号"),
    OTHER(15,"其他");


    public static String getValue(Integer key){
        for (AXBEndStateEnum reasonEnum : AXBEndStateEnum.values()) {
            if(reasonEnum.getKey().equals(key)){
                return reasonEnum.getValue();
            }
        }
        return "未知错误";
    }

    private Integer key;

    private String value;

    AXBEndStateEnum(Integer key, String value){
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
