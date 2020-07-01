package com.github.wxiaoqi.security.auth.client.jwt;

import com.github.ag.core.util.jwt.IJWTInfo;
import com.github.ag.core.util.jwt.JWTHelper;
import com.github.wxiaoqi.security.auth.client.config.ServiceAuthConfig;
import com.github.wxiaoqi.security.auth.client.feign.ServiceAuthFeign;
import com.github.wxiaoqi.security.common.exception.auth.ClientTokenException;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
@Slf4j
@EnableScheduling
public class ServiceAuthUtil {
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;
    @Autowired
    private JWTHelper jwtHelper;

    private List<String> allowedClient;
    private String clientToken;

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return getInfoFromToken(token,"");
    }

    public IJWTInfo getInfoFromToken(String token,String url) throws Exception {
        try {
            IJWTInfo infoFromToken = jwtHelper.getInfoFromToken(token, serviceAuthConfig.getPubKeyByte());
            Date current = infoFromToken.getExpireTime();
            if (new Date().after(current)) {
                throw new ClientTokenException("Client token expired!token:"+token+",url:"+url);
            }
            return infoFromToken;
        } catch (ExpiredJwtException ex) {
            throw new ClientTokenException("Client token expired!token:"+token+",url:"+url);
        } catch (SignatureException ex) {
            throw new ClientTokenException("Client token signature error!token:"+token+",url:"+url);
        } catch (IllegalArgumentException ex) {
            throw new ClientTokenException("Client token is null or empty!token:"+token+",url:"+url);
        }
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void refreshAllowedClient() {
        log.debug("refresh allowedClient.....");
        BaseResponse resp = serviceAuthFeign.getAllowedClient(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            ObjectRestResponse<List<String>> allowedClient = (ObjectRestResponse<List<String>>) resp;
            this.allowedClient = allowedClient.getData();
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshClientToken() {
        log.debug("refresh client token.....");
        BaseResponse resp = serviceAuthFeign.getAccessToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            ObjectRestResponse<String> clientToken = (ObjectRestResponse<String>) resp;
            this.clientToken = clientToken.getData();
        }
    }

    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

    public List<String> getAllowedClient() {
        if (this.allowedClient == null) {
            this.refreshAllowedClient();
        }
        return allowedClient;
    }

}
