package com.github.wxiaoqi.security.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;


@Data
@Configuration
@ConfigurationProperties(prefix = "crm")
public class CrmConfig implements Serializable {
    private static final long serialVersionUID = -463970502222241237L;

    private String url;

    private String accessKey;

    private String accessSecret;
    //#true-开放接口，false-关闭接口
    private String woOpenFlag;
    //开放项目id列表,多个用逗号分隔
    private String projectIds;

    private String zhongtaiUrl;
    private String zhongtaiAccessKey;
    private String zhongtaiAccessSecret;
    //#(1-old crm ,2-crm zhongtai)
    private String serverType;

    //Apollo配置
    public enum ApolloKey{

        URL("crm.url"),
        ACCESS_KEY("crm.accessKey"),
        ACCESS_SECRET("crm.accessSecret"),
        WO_OPEN_FLAG("crm.woOpenFlag"),
        PROJECTIDS("crm.projectIds"),
        ZHONGTAI_URL("crm.zhongtaiUrl"),
        ZHONGTAI_ACCESS_KEY("crm.zhongtaiAccessKey"),
        ZHONGTAI_ACCESS_SECRET("crm.zhongtaiAccessSecret"),
        SERVER_TYPE("crm.serverType");


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
