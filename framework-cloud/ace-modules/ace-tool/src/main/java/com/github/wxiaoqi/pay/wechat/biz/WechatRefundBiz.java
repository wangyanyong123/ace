package com.github.wxiaoqi.pay.wechat.biz;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.payment.mch.RefundResult;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.github.wxiaoqi.pay.biz.support.RefundParam;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author: guohao
 * @create: 2020-04-11 13:53
 **/
@Slf4j
@Component
public class WechatRefundBiz extends BaseWechatPayBiz {

    public ObjectRestResponse refund(RefundParam refundParam) throws WeixinException {
        Assert.hasLength(refundParam.getAppId(), "appId can't be null/empty");
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        WeixinPayProxy weixinPayProxy = getWeixinPayProxyByAppId(refundParam.getAppId());
        IdQuery idQuery;
        if (StringUtils.isNotEmpty(refundParam.getTradeNo())) {
            idQuery = new IdQuery(refundParam.getTradeNo(), IdType.TRANSACTIONID);
        } else {
            idQuery = new IdQuery(refundParam.getOutTradeNo(), IdType.TRADENO);
        }
        RefundResult refundResult = weixinPayProxy.applyRefund(idQuery, refundParam.getOutRequestNo(),
                refundParam.getPaidMoney().doubleValue(), refundParam.getRefundMoney().doubleValue(), CurrencyType.CNY,
                weixinPayProxy.getWeixinPayAccount().getMchId(), refundParam.getRefundReason(), null);

        if(log.isInfoEnabled()){
            log.info("微信退款响应结果：param:{}, result:{}", JSON.toJSONString(refundParam), JSON.toJSONString(refundResult));
        }
        return objectRestResponse;
    }
}
