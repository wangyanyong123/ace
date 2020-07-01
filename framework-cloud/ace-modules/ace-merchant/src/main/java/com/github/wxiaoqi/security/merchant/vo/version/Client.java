package com.github.wxiaoqi.security.merchant.vo.version;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class Client implements Serializable {
    private static final long serialVersionUID = -8288422953064922102L;

    public enum ClientType{

        IOS_MERCHANT("ios"),
        ANDROID_MERCHANT("android");


        private final String va;

        ClientType(String value){
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

        public static boolean contains(String value){
            for (ClientType clienType:ClientType.values()) {
                if(clienType.va.equals(value)){
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }

    }

    /**
     * 客户端类型
     */
    @ApiModelProperty(value = "ios-IOS商户端；android-android商户端")
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
        return ClientType.contains(client.getcType());

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
        return ClientType.contains(client.getcType());

    }
}
