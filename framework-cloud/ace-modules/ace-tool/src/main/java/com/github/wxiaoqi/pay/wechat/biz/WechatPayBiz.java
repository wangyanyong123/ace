package com.github.wxiaoqi.pay.wechat.biz;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.TradeType;
import com.gexin.fastjson.JSON;
import com.github.wxiaoqi.security.api.vo.order.in.GenerateUnifiedOrderIn;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信app支付
 *
 * @author: guohao
 * @create: 2020-04-09 14:25
 **/
@Slf4j
@Component
public class WechatPayBiz extends BaseWechatPayBiz {


    public ObjectRestResponse pay(GenerateUnifiedOrderIn generateUnifiedOrderIn) {
        ObjectRestResponse restResponse = new ObjectRestResponse();
        if (!AceDictionary.PAY_TYPE_WECHAT.equals(generateUnifiedOrderIn.getPayType())) {
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            restResponse.setMessage("非法调用微信 pay/unifiedorder ");
            return restResponse;
        }
        try {
            PayRequest payRequest = doPay(generateUnifiedOrderIn);
            restResponse.setData(payRequest);
        } catch (Exception e) {
            log.error("微信 统一下单异常,param:{}", JSON.toJSONString(generateUnifiedOrderIn), e);
            restResponse.setStatus(RestCodeConstants.EX_BUSINESS_BASE_CODE);
            if (e instanceof BusinessException) {
                restResponse.setMessage(e.getMessage());
            } else {
                restResponse.setMessage("微信 统一下单异常," + e.getMessage());
            }
        }
        return restResponse;

    }


    private PayRequest doPay(GenerateUnifiedOrderIn generateUnifiedOrderIn) throws WeixinException {
        TradeType tradeType = getTradeType(generateUnifiedOrderIn.getAppType());
        MchPayPackage mchPayPackage = buildParam(generateUnifiedOrderIn, tradeType);
        WeixinPayProxy weixinPayProxy = getWeixinPayProxyByAppType(generateUnifiedOrderIn.getAppType());
        if (log.isInfoEnabled()) {
            log.info("微信统一下单请求：{}", JSON.toJSONString(mchPayPackage));
        }
        MchPayRequest mchPayRequest = weixinPayProxy.createPayRequest(mchPayPackage);
        PayRequest payRequest = mchPayRequest.toRequestObject();
        if (log.isInfoEnabled()) {
            log.info("微信统一下单响应：{}", JSON.toJSONString(payRequest));
        }
        return payRequest;

    }

    private MchPayPackage buildParam(GenerateUnifiedOrderIn generateUnifiedOrderIn, TradeType tradeType) {
        String notifyUrl = generateUnifiedOrderIn.getNotifyUrl();
        if (StringUtils.isEmpty(notifyUrl)) {
            notifyUrl = getNotifyUrl(generateUnifiedOrderIn.getAppType());
        }
        return new MchPayPackage(
                generateUnifiedOrderIn.getTitle(), null, generateUnifiedOrderIn.getActualId(),
                generateUnifiedOrderIn.getActualPrice().doubleValue(), CurrencyType.CNY, notifyUrl,
                generateUnifiedOrderIn.getUserIp(), tradeType, generateUnifiedOrderIn.getOpenId(), null, null,
                generateUnifiedOrderIn.getAttach(), null, null, null, null, null
        );
    }
}
