package com.github.wxiaoqi.security.schedulewo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
@ConfigurationProperties(prefix = "wo")
@RefreshScope
public class WOTimeConfig implements Serializable  {
    //Apollo配置
    public enum ApolloKey{
        OUT_TIME("wo.outtime"),
        MISSED_ORDER_BUSID("wo.missedorderbusid"),
        MISSED_ORDER("wo.missedorder"),
        UN_CONFIRMED_BUSID("wo.unconfirmedbusid"),
        UN_CONFIRMED("wo.unconfirmed");

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


    private int outtime;

    private String missedorderbusid;

    private int missedorder;

    private String unconfirmedbusid;

    private int unconfirmed;

}
