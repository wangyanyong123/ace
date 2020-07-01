package com.github.wxiaoqi.security.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;


@Data
@Configuration
@ConfigurationProperties(prefix = "vrobot")
public class VrobotConfig implements Serializable {
    private static final long serialVersionUID = -463970502222241237L;

    private String url;

    private String accessKey;

    private String accessSecret;
    //#true-开放接口，false-关闭接口
    private String woOpenFlag;

    //Apollo配置
    public enum ApolloKey{

        URL("vrobot.url"),
        ACCESS_KEY("vrobot.accessKey"),
        ACCESS_SECRET("vrobot.accessSecret"),
        WO_OPEN_FLAG("vrobot.woOpenFlag");


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

}
