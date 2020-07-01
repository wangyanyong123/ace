package com.github.wxiaoqi.pay.wechat.biz;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.github.wxiaoqi.pay.wechat.config.WechatPayConfig;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.wechat.config.WechatAppConfig;
import com.github.wxiaoqi.wechat.config.WechatH5Config;
import com.github.wxiaoqi.wechat.config.WechatSmallConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: guohao
 * @create: 2020-04-14 13:42
 **/
@Component
public class WechatPayProxyFactory {

    @Autowired
    private WechatPayConfig wechatPayConfig;
    @Autowired
    private WechatH5Config wechatH5Config;
    @Autowired
    private WechatSmallConfig wechatSmallConfig;
    @Autowired
    private WechatAppConfig wechatAppConfig;

    private WeixinPayProxy weixinH5PayProxy;
    private WeixinPayProxy weixinAppPayProxy;
    private WeixinPayProxy weixinSmallPayProxy;

    @PostConstruct
    private void init() {
        weixinH5PayProxy = initPayProxy(wechatH5Config.getAppId(), wechatH5Config.getSecret());
        weixinAppPayProxy = initPayProxy(wechatAppConfig.getAppId(), wechatAppConfig.getSecret());
        weixinSmallPayProxy = initPayProxy(wechatSmallConfig.getAppId(), wechatSmallConfig.getSecret());
    }
    private WeixinPayProxy initPayProxy(String appId, String secret) {
//            ResourceUtils.getFile(certificateFile).getAbsolutePath();
        WeixinPayAccount weixinPayAccount = new WeixinPayAccount(
                appId, secret,
                wechatPayConfig.getPaySignKey(), wechatPayConfig.getMchId(),
                wechatPayConfig.getCertificateKey(), wechatPayConfig.getCertificateFile(),
                null, null,
                null, null);
        return new WeixinPayProxy(weixinPayAccount);
    }

    public WeixinPayProxy getWeixinPayProxyByAppType(String appType) {
        if (RequestHeaderConstants.PLATFORM_H5.equals(appType)) {
            return weixinH5PayProxy;
        } else if (RequestHeaderConstants.PLATFORM_WECHAT_SMALL.equals(appType)) {
            return weixinSmallPayProxy;
        } else if (RequestHeaderConstants.PLATFORM_IOS.equals(appType)
                || RequestHeaderConstants.PLATFORM_ANDROID.equals(appType)) {
            return weixinAppPayProxy;
        } else {
            throw new BusinessException("微信支付 非法的应用类型，appType:" + appType);
        }
    }
    public WeixinPayProxy getWeixinPayProxyByAppType(Integer appTypeIntValue) {
        if (AceDictionary.APP_TYPE_H5.equals(appTypeIntValue)) {
            return weixinH5PayProxy;
        } else if (AceDictionary.APP_TYPE_MP.equals(appTypeIntValue)) {
            return weixinSmallPayProxy;
        } else if (AceDictionary.APP_TYPE_IOS.equals(appTypeIntValue)
                || AceDictionary.APP_TYPE_ANDROID.equals(appTypeIntValue)) {
            return weixinAppPayProxy;
        } else {
            throw new BusinessException("微信支付 非法的应用类型，appType:" + appTypeIntValue);
        }
    }
    public WeixinPayProxy getWeixinPayProxyByAppId(String appId) {
        if (weixinH5PayProxy.getWeixinPayAccount().getId().equals(appId)) {
            return weixinH5PayProxy;
        } else if (weixinSmallPayProxy.getWeixinPayAccount().getId().equals(appId)) {
            return weixinSmallPayProxy;
        } else if (weixinAppPayProxy.getWeixinPayAccount().getId().equals(appId)) {
            return weixinAppPayProxy;
        } else {
            throw new BusinessException("未知的支付应用id，appId=" + appId);
        }
    }

}
