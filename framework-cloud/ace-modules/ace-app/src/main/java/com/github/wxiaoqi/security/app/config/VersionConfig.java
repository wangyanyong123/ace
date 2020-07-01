package com.github.wxiaoqi.security.app.config;

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

        AND_HOME_LOW("check.anHomeLowVersion"),
        AND_HOME_LAST("check.anHomeLastVersion"),
        AND_HOME_UPDATE("check.anHomeUpdateContent"),
        AND_JIN_LOW("check.anJinLowVersion"),
        AND_JIN_LAST("check.anJinLastVersion"),
        AND_JIN_UPDATE("check.anJinUpdateContent"),
        IOS_HOME_LOW("check.iosHomeLowVersion"),
        IOS_HOME_LAST("check.iosHomeLastVersion"),
        IOS_HOME_UPDATE("check.iosHomeUpdateContent"),
        IOS_JIN_LOW("check.iosJinLowVersion"),
        IOS_JIN_LAST("check.iosJinLastVersion"),
        IOS_JIN_UPDATE("check.iosJinUpdateContent"),
        DOWNLOAD_URL("check.url");

        private final String va;
        private ApolloKey(String value){ va = value; }

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
