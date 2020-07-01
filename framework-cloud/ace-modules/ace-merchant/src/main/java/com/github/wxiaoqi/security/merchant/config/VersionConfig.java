package com.github.wxiaoqi.security.merchant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
@ConfigurationProperties(prefix = "check")
@RefreshScope
public class VersionConfig implements Serializable  {
    //Apollo配置
    public enum ApolloKey{

        AND_MERCHANT_LOW("check.anMerchantLowVersion"),
        AND_MERCHANT_LAST("check.anMerchantLastVersion"),
        AND_MERCHANT_UPDATE("check.anMerchantUpdateContent"),

        IOS_MERCHANT_LOW("check.iosMerchantLowVersion"),
        IOS_MERCHANT_LAST("check.iosMerchantLastVersion"),
        IOS_MERCHANT_UPDATE("check.iosMerchantUpdateContent"),

        DOWNLOAD_URL("check.url");



        private final String va;
        ApolloKey(String value){ va = value; }

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


    private String anHomeLowVersion;

    private String anHomeLastVersion;

    private String anJinLowVersion;

    private String anJinLastVersion;

    private String iosHomeLowVersion;

    private String iosHomeLastVersion;

    private String iosJinLowVersion;

    private String iosJinLastVersion;

    private String checkUrl;
    private String[] anHomeUpdateContent;
    private String[] anJinUpdateContent;
    private String[] iosHomeUpdateContent;
    private String[] iosJinUpdateContent;


}
