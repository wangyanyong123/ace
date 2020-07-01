package com.codingapi.tx.datasource.bean;

/**
 * create by lorne on 2017/12/7
 */
public class LCNDataSourceLocal {

    private final static ThreadLocal<LCNDataSourceLocal> currentLocal = new ThreadLocal<LCNDataSourceLocal>();
    private String key;

    public static LCNDataSourceLocal current() {
        return currentLocal.get();
    }

    public static void setCurrent(LCNDataSourceLocal current) {
        currentLocal.set(current);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
