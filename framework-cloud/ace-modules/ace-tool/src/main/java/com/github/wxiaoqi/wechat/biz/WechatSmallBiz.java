package com.github.wxiaoqi.wechat.biz;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.wxa.WeixinAppFacade;
import com.foxinmy.weixin4j.wxa.model.Session;
import com.github.wxiaoqi.security.api.vo.wechat.out.WechatOpenIdResult;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RequestHeaderConstants;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.wechat.config.WechatSmallConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信小程序相关业务
 *
 * @author: guohao
 * @create: 2020-04-12 16:37
 **/
@Slf4j
@Component
public class WechatSmallBiz extends BaseWechatBiz {
    @Autowired
    private WeixinAppFacade weixinAppFacade;
    @Autowired
    private WechatSmallConfig wechatSmallConfig;


    @Override
    public ObjectRestResponse<WechatOpenIdResult> getOpenId(String code) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        try {
            Session session = weixinAppFacade.getLoginApi().jscode2session(code);
            WechatOpenIdResult result = new WechatOpenIdResult();
            result.setAppId(wechatSmallConfig.getAppId());
            result.setAppType(AceDictionary.APP_TYPE_MP);
            result.setOpenId(session.getOpenId());
            result.setSessionKey(session.getSessionKey());
            result.setUnionId(session.getUnionId());
            objectRestResponse.setData(result);
        } catch (WeixinException e) {
            log.error("微信小程序获取openid 异常，code :{}", code, e);
            objectRestResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            objectRestResponse.setMessage("微信小程序获取openid 异常" + e.getMessage());
        }
        return objectRestResponse;
    }

    @Override
    protected String getAppId() {
        return wechatSmallConfig.getAppId();
    }

    @Override
    protected String getSecret() {
        return wechatSmallConfig.getSecret();
    }
}
