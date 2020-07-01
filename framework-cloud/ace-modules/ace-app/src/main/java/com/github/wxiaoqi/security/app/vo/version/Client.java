package com.github.wxiaoqi.security.app.vo.version;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class Client implements Serializable {
    private static final long serialVersionUID = -8288422953064922102L;

    public enum ClientType{

        IOS_HOME("C1"),
        IOS_JINXIAOMAO("C2"),
        ANDROID_HOME("C3"),
        ANDROID_JINXIAOMAO("C4");

        private final String va;

        private ClientType(String value){
            va = value;
        }
        public boolean equals(String value){
            if(value == null){
                return false;
            }
            return value.equals(this.va);
        }
        @Override
        public String toString() {
            return va;
        }

    }

    /**
     * 客户端类型
     */
    @ApiModelProperty(value = "C1-IOS回家C2-IOS金小茂C3-安卓回家C4-安卓金小茂")
    private String cType;

    /**
     * 客户端版本
     */
    private String cVersion;

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getcVersion() {
        return cVersion;
    }

    public void setcVersion(String cVersion) {
        this.cVersion = cVersion;
    }

    /**
     * 客户端是否合法
     * @param client
     * @return
     */
    public static boolean check( Client client ){

        if(client == null ){
            return false;
        }

        if(String.valueOf(client.getcType()).length() != 2){
            return false;
        }
        return "C1,C2,C3,C4".contains(client.getcType());

    }

    /**
     * 服务端是否合法
     * @param client
     * @return
     */
    public static boolean isApp( Client client ){

        if(client == null ){
            return false;
        }

        if(String.valueOf(client.getcType()).length() != 2){
            return false;
        }
        return "C1,C2,C3,C4".contains(client.getcType());

    }
}
