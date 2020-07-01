package com.github.wxiaoqi.pay.wechat.biz;

import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.type.TradeType;
import com.github.wxiaoqi.pay.wechat.config.WechatPayConfig;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: guohao
 * @create: 2020-04-09 16:41
 **/
public abstract  class BaseWechatPayBiz {
    @Autowired
    private WechatPayConfig wechatPayConfig;
    @Autowired
    private WechatPayProxyFactory wechatPayProxyFactory;


    /**
     * 获取微信支付类型
     *
     * @param appType :  请求来源
     * @return com.foxinmy.weixin4j.type.TradeType
     * @Author guohao
     * @Date 2020/4/9 16:45
     */
    public TradeType getTradeType(String appType) {
        if (RequestHeaderConstants.PLATFORM_H5.equals(appType)
                || RequestHeaderConstants.PLATFORM_WECHAT_SMALL.equals(appType)) {
            return TradeType.JSAPI;
        } else if (RequestHeaderConstants.PLATFORM_IOS.equals(appType)
                || RequestHeaderConstants.PLATFORM_ANDROID.equals(appType)) {
            return TradeType.APP;
        } else {
            throw new BusinessException("微信支付 非法的应用类型，appType:" + appType);
        }
    }
    public TradeType getTradeType(Integer appTypeIntValue) {
        if (AceDictionary.APP_TYPE_H5.equals(appTypeIntValue)
                || AceDictionary.APP_TYPE_MP.equals(appTypeIntValue)) {
            return TradeType.JSAPI;
        } else if (AceDictionary.APP_TYPE_IOS.equals(appTypeIntValue)
                || AceDictionary.APP_TYPE_ANDROID.equals(appTypeIntValue)) {
            return TradeType.APP;
        } else {
            throw new BusinessException("微信支付 非法的应用类型，appType:" + appTypeIntValue);
        }
    }

    public String getNotifyUrl(String appType) {
        return wechatPayConfig.getNotifyUrl();
//        if (AppTypeConstants.H5 == appType) {
//            return wechatPayConfig.getNotifyUrl();
//        } else if (AppTypeConstants.SMALL_WECHAT == appType) {
//            return wechatPayConfig.getNotifyUrl();
//        } else if (AppTypeConstants.IOS == appType
//                || AppTypeConstants.ANDROID == appType) {
//            return wechatPayConfig.getNotifyUrl();
//        } else {
//            throw new BusinessException("微信是支付 非法的应用类型，appType:" + appType);
//        }
    }
    public String getNotifyUrl(Integer appType) {
        return wechatPayConfig.getNotifyUrl();
    }

    public WeixinPayProxy getWeixinPayProxyByAppType(String appType) {
        return wechatPayProxyFactory.getWeixinPayProxyByAppType(appType);
    }

    public WeixinPayProxy getWeixinPayProxyByAppType(Integer appType) {
        return wechatPayProxyFactory.getWeixinPayProxyByAppType(appType);
    }

    public WeixinPayProxy getWeixinPayProxyByAppId(String appId) {
        return wechatPayProxyFactory.getWeixinPayProxyByAppId(appId);
    }

}
