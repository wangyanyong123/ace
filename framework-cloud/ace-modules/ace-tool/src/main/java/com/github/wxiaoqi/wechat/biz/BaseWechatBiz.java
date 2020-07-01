package com.github.wxiaoqi.wechat.biz;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.type.TicketType;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.wechat.jssdk.jssdk.JSSDKAPI;
import com.github.wxiaoqi.wechat.jssdk.jssdk.JSSDKConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author: guohao
 * @create: 2020-04-17 19:20
 **/
@Slf4j
public abstract class BaseWechatBiz implements WechatBiz {

    @Autowired
    private RedisTokenBiz redisTokenBiz;

    protected WeixinProxy weixinProxy;

    @PostConstruct
    private void init() {
        weixinProxy = new WeixinProxy(new WeixinAccount(getAppId(), getSecret()), redisTokenBiz);
    }

    @Override
    public ObjectRestResponse getJsSdkConfig(String url, JSSDKAPI[] jssdkapis, boolean debug) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        try {
            JSSDKConfigurator jssdkConfigurator = new JSSDKConfigurator(weixinProxy.getTicketManager(TicketType.jsapi));
            jssdkConfigurator.apis(jssdkapis);
            if (debug) {
                jssdkConfigurator.debugMode();
            }
            JSONObject jsonObject = jssdkConfigurator.toJSONConfig(url);
            restResponse.setData(jsonObject);
            return restResponse;
        } catch (WeixinException e) {
            log.error("获取jssdkconfig异常", e);
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("获取jssdkconfig异常");
        }
        return restResponse;
    }

    @Override
    public String getUserAuthorizationURL(String notifyUrl, String scope, String state) {

        return weixinProxy.getOauthApi().getUserAuthorizationURL(notifyUrl, state, scope);
    }

    protected abstract String getAppId();

    protected abstract String getSecret();
}
