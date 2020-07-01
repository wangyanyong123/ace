package com.github.wxiaoqi.store;

public class StoreException extends Exception {

    private String message;

    public StoreException(String message){
        this.message = message;
    }
}
