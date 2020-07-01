package com.github.wxiaoqi.wechat.biz;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.github.wxiaoqi.security.api.vo.wechat.out.WechatOpenIdResult;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.wechat.config.WechatH5Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信公众号相关业务
 *
 * mp是Media Platform（媒体平台）的缩写
 *
 * @author: guohao
 * @create: 2020-04-12 16:37
 **/
@Slf4j
@Component
public class WechatMpBiz extends BaseWechatBiz {
    @Autowired
    private WechatH5Config wechatH5Config;

    @Override
    public ObjectRestResponse<WechatOpenIdResult> getOpenId(String code) {
        ObjectRestResponse<WechatOpenIdResult> objectRestResponse = new ObjectRestResponse<>();
        try {
            OauthToken authorizationToken = weixinProxy.getOauthApi().getAuthorizationToken(code);
            WechatOpenIdResult result = new WechatOpenIdResult();
            result.setAppId(weixinProxy.getWeixinAccount().getId());
            result.setAppType(AceDictionary.APP_TYPE_H5);
            result.setOpenId(authorizationToken.getOpenId());
            objectRestResponse.setData(result);
        } catch (WeixinException e) {
            log.error("微信公众号获取openid 异常，code :{}", code, e);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("微信公众号获取openid 异常" + e.getMessage());
        }
        return objectRestResponse;
    }


    @Override
    protected String getAppId() {
        return wechatH5Config.getAppId();
    }

    @Override
    protected String getSecret() {
        return wechatH5Config.getSecret();
    }
}
